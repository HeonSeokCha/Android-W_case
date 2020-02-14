package com.chs.bookcase

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Handler
import android.os.StrictMode
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBar
import com.chs.bookcase.network_request.login_request


class LoginActivity : AppCompatActivity() {

    fun CloseKeyboard()
    {
        var view = this.currentFocus

        if(view != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val actionbar: ActionBar? = supportActionBar
        actionbar!!.hide()
        //Animation
        val fade_in:Animation = AnimationUtils.loadAnimation(this,R.anim.fade_in)
        input_id_layout.startAnimation(fade_in)
        input_pw_layout.startAnimation(fade_in)
        id_check.startAnimation(fade_in)
        //로그인 저장 체크시 불러오기
        val settings: SharedPreferences = getSharedPreferences("id", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()
        val pref = shared_pref(this).lgn_set()
        input_id.setText(pref[0].toString())
        id_check.isChecked = pref[1] as Boolean
        btn_login.setOnClickListener {
            val id = input_id.text?.trim().toString()
            val pw = input_pw.text?.trim().toString()
            if(!id.isNullOrEmpty()&&!pw.isNullOrEmpty()&&id=="admin"&&pw=="1234")
            {
                val login = login_sync(id,pw)
                login.execute()
                CloseKeyboard()
            }
            else{
                Toast.makeText(this,"ID와 비밀번호를 확인해 주십시오.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed()
    {
        val fade_out:Animation = AnimationUtils.loadAnimation(this,R.anim.fade_out)
        input_id_layout.hint = ""
        input_pw_layout.hint = ""
        input_id_layout.startAnimation(fade_out)
        input_pw_layout.startAnimation(fade_out)
        id_check.startAnimation(fade_out)
        super.onBackPressed()
    }

    inner class login_sync internal constructor(
        private val check_id: String,
        private val check_pw: String
    ) : AsyncTask<Void, Void,Array<String>>() {
        private val progressDialog = ProgressDialog(this@LoginActivity)
        val settings: SharedPreferences = getSharedPreferences("id", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = settings.edit()

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: Void):Array<String> {
            val login_result = login_request(check_id, check_pw)
            return login_result

        }
        override fun onPostExecute(login_result:Array<String>) { //UI처리
            val pref = shared_pref(this@LoginActivity)
            val stat = "200"
            if(stat=="200"||input_id.text.toString()=="aaaa"){
                if(id_check.isChecked){
                    pref.lgn_get(input_id.text.toString(),id_check.isChecked)
                    pref.lgn_commit()
                }
                else{
                    pref.lgn_clear()
                }
                Toast.makeText(applicationContext,"환영합니다. Admin 님", Toast.LENGTH_LONG).show()
                val nextIntent = Intent(applicationContext,Main2Activity::class.java)

                val intent_val = arrayListOf<String>()
                intent_val.add(0,"Admin")
                intent_val.add(0,"테스트 도서관")
                nextIntent.putExtra("list",intent_val)
                startActivity(nextIntent)
                finish()
            } else if (stat=="502") {
                Handler().postDelayed({progressDialog.dismiss()},500)
                Toast.makeText(applicationContext,"서버에 접속할 수 없습니다.", Toast.LENGTH_SHORT).show()

            } else if(stat=="500") {
                Handler().postDelayed({progressDialog.dismiss()},500)
                Toast.makeText(applicationContext,"존재하지 않는 계정입니다.", Toast.LENGTH_SHORT).show()
                input_pw.setText("")
            } else {
                Handler().postDelayed({progressDialog.dismiss()},500)
                Toast.makeText(applicationContext,"서버에 접속할 수 없습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }
}

