package com.asitrack.rvitemclicks

import android.support.v7.widget.RecyclerView
import android.view.View


class KotlinRvItemsClicks private constructor(private val mRecyclerView: RecyclerView) {
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private val mOnClickListener = View.OnClickListener { v ->
        if (mOnItemClickListener != null) {
            // ask the RecyclerView for the viewHolder of this view.
            // then use it to get the position for the adapter
            val holder = mRecyclerView.getChildViewHolder(v)
            mOnItemClickListener!!.onItemClicked(mRecyclerView, holder.adapterPosition, v)
        }
    }
    private val mOnLongClickListener = View.OnLongClickListener { v ->
        if (mOnItemLongClickListener != null) {
            val holder = mRecyclerView.getChildViewHolder(v)
            return@OnLongClickListener mOnItemLongClickListener!!.onItemLongClicked(
                mRecyclerView,
                holder.adapterPosition,
                v
            )
        }
        false
    }
    private val mAttachListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            // every time a new child view is attached add click listeners to it
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener)
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener)
            }
        }

        override fun onChildViewDetachedFromWindow(view: View) {

        }
    }

    init {
        // the ID must be declared in XML, used to avoid
        // replacing the KotlinRvItemsClicks without removing
        // the old one from the RecyclerView
        mRecyclerView.setTag(R.id.rv_item_clicks, this)
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener)
    }

    fun setOnItemClickListener(listener: OnItemClickListener): KotlinRvItemsClicks {
        mOnItemClickListener = listener
        return this
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener): KotlinRvItemsClicks {
        mOnItemLongClickListener = listener
        return this
    }

    private fun detach(view: RecyclerView) {
        view.removeOnChildAttachStateChangeListener(mAttachListener)
        view.setTag(R.id.rv_item_clicks, null)
    }

    interface OnItemClickListener {

        fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View)
    }

    interface OnItemLongClickListener {

        fun onItemLongClicked(recyclerView: RecyclerView, position: Int, v: View): Boolean
    }

    companion object {

        fun addTo(view: RecyclerView): KotlinRvItemsClicks {
            // if there's already an KotlinRvItemsClicks attached
            // to this RecyclerView do not replace it, use it
            var support: KotlinRvItemsClicks? = view.getTag(R.id.rv_item_clicks) as? KotlinRvItemsClicks
            if (support == null) {
                support = KotlinRvItemsClicks(view)
            }
            return support
        }

        fun removeFrom(view: RecyclerView): KotlinRvItemsClicks? {
            val support = view.getTag(R.id.rv_item_clicks) as KotlinRvItemsClicks
            support?.detach(view)
            return support
        }
    }
}