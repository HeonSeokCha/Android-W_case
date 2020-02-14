package com.chs.bookcase.data_binding_class

import android.widget.ImageView
import androidx.databinding.BindingAdapter

data class Books(val title:String,val author:String,val postion:String,val img_url:String,val book_state_img:Int)

data class Shelfs(val shlef_name:String,val shelf_img:Int,val shelf_msg:Int)

data class Dummy(val dummys:String)

