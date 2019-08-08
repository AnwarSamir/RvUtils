package com.asitrack.rvitemclicks;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RvItemsClicks {
    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                // ask the RecyclerView for the viewHolder of this view.
                // then use it to get the position for the adapter
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };
    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            // every time a new child view is attached add click listeners to it
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private RvItemsClicks(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        // the ID must be declared in XML, used to avoid
        // replacing the RvItemsClicks without removing
        // the old one from the RecyclerView
        mRecyclerView.setTag(R.id.rv_item_clicks, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static RvItemsClicks addTo(RecyclerView view) {
        // if there's already an RvItemsClicks attached
        // to this RecyclerView do not replace it, use it
        RvItemsClicks support = (RvItemsClicks) view.getTag(R.id.rv_item_clicks);
        if (support == null) {
            support = new RvItemsClicks(view);
        }
        return support;
    }

    public static RvItemsClicks removeFrom(RecyclerView view) {
        RvItemsClicks support = (RvItemsClicks) view.getTag(R.id.rv_item_clicks);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public RvItemsClicks setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public RvItemsClicks setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.rv_item_clicks, null);
    }

    public interface OnItemClickListener {

        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {

        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}