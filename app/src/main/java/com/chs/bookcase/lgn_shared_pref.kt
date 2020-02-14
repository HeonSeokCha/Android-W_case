package com.chs.bookcase

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class shared_pref(context:Context){
    var user_id:String=""
    var chk:Boolean = false
    val settings: SharedPreferences = context.getSharedPreferences("id", AppCompatActivity.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = settings.edit()

    fun lgn_get(user_id:String,chk:Boolean){
        this.user_id = user_id
        this.chk = chk
        editor.putString("user_id",user_id)
        editor.putBoolean("checked",chk)
        editor.commit()
    }

    fun lgn_set():Array<Any>{
        var output_id:String = ""
        var output_chk:Boolean = false
        return arrayOf(settings.getString("user_id",output_id),settings.getBoolean("checked",output_chk))
    }
    fun lgn_clear(){
        editor.clear()
    }
    fun lgn_commit(){
        editor.commit()
    }
}