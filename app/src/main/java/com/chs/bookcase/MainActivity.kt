package com.chs.bookcase

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.ActionBar
import android.util.Pair
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionbar: ActionBar? = supportActionBar
        actionbar!!.hide()


        //animate
        val from_btm = AnimationUtils.loadAnimation(this,R.anim.anim_bottom)
        val from_top = AnimationUtils.loadAnimation(this,R.anim.anim_top)
        btn_do_login.startAnimation(from_btm)
        btn_do_create_user.startAnimation(from_btm)
        img_main_title.startAnimation(from_top)
        main_title.startAnimation(from_top)
        main_sub_title.startAnimation(from_top)

        btn_do_login.setOnClickListener {
            val nextIntent = Intent(this,LoginActivity::class.java)
            val pairs = ArrayList<Pair<View,String>>()
            pairs.add(Pair.create(btn_do_login,btn_do_login.transitionName))
            pairs.add(Pair.create(main_title,main_title.transitionName))
            pairs.add(Pair.create(img_main_title,img_main_title.transitionName))
            val pairArr:Array<Pair<View,String>> = pairs.toTypedArray()
            //Shared_Activity
            val actoption:ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this,*pairArr)
            startActivity(nextIntent,actoption.toBundle())
        }

        btn_do_create_user.setOnClickListener {
            val nextIntent = Intent(this,Create_user_Activity::class.java)
            val pairs = ArrayList<Pair<View,String>>()
            pairs.add(Pair.create(btn_do_create_user,btn_do_create_user.transitionName))
            pairs.add(Pair.create(main_title,main_title.transitionName))
            pairs.add(Pair.create(img_main_title,img_main_title.transitionName))
            val pairArr:Array<Pair<View,String>> = pairs.toTypedArray()
            //Shared_Activity
            val actoption:ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this,*pairArr)
            startActivity(nextIntent,actoption.toBundle())
        }
    }
    override fun onBackPressed()//뒤로가기 이벤트
    {
        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("종료 하시겠습니까?").setCancelable(false)
            .setPositiveButton("확인") { _, _ ->
                ActivityCompat.finishAffinity(this)
                exitProcess(0)
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.cancel()
            }

        val alert = dialogBuilder.create()
        alert.setTitle("W Case")
        alert.setIcon(R.drawable.ico_title)
        alert.show()
    }
}

