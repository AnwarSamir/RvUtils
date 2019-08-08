# RvUtils
This Project will hold a magical things for recycler view to help developers to make things more and more better  
# How it work 
if you have a recyclerview and want to make clcik on item at your view [Activity or Fragment ] you will never use callback and inerfaces to do that 

# Setup
The lib is available on JitPack.io. Add the following to your build.gradle:
```
dependencies {
	         implementation 'com.github.AnwarSamir:RvUtils:1.0.07'
	}
```
then 
Add support JitPack repository in root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
# Implemntation 
- **For Java**
```
JavaRvItemsClicks.addTo(rvName).setOnItemClickListener(new JavaRvItemsClicks.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // Write your code here 
                // the postion hold the postion of item  you clcik on it 
            }
        });
```
- **For Kotlin**

```

        KotlinRvItemsClicks.addTo(rvName).setOnItemClickListener(object : KotlinRvItemsClicks.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView, position: Int, v: View) {

              
                replaceFragment(ViewCatItemsFragment())
            }
        })
```
