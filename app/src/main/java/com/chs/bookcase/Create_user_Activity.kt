package com.chs.bookcase

import android.app.ProgressDialog
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.chs.bookcase.network_request.createUser_request
import kotlinx.android.synthetic.main.activity_create_user.*

class Create_user_Activity : AppCompatActivity() {

    fun theradpolicy()
    {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()//Thread 분리
        StrictMode.setThreadPolicy(policy)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        val actionbar: ActionBar? = supportActionBar
        actionbar!!.hide()
        input_create_id.requestFocus()

        btn_create_user.setOnClickListener {
            var id = input_create_id.text.toString()
            var name = input_name.text.toString()
            var pw = input_create_pw.text.toString()
            var pw_check = input_create_pw_chk.text.toString()
            theradpolicy()//Thread

            if(!name.isNullOrEmpty()||!id.isNullOrEmpty() || !pw.isNullOrEmpty()||!pw_check.isNullOrEmpty())//빈칸체크
            {
                if (pw == pw_check)//비번체크
                {
                    val dialogBuilder = AlertDialog.Builder(this)
                    dialogBuilder.setMessage("생성 하시겠습니까?").setCancelable(false)
                        .setPositiveButton("확인"){_, _ ->
                            val createUser_type = createUser_sync(name,id,pw)
                            createUser_type.execute()
                        }
                        .setNegativeButton("취소"){ dialog, _ ->
                            dialog.cancel()
                    }
                    val alert = dialogBuilder.create()
                    alert.setTitle("W Case")
                    alert.setIcon(R.drawable.ico_title)
                    alert.show()
                }
                else{
                    input_create_pw_chk.error = "비밀번호가 일치 하지 않습니다."
                    Toast.makeText(this,"비밀번호가 일치 하지 않습니다.",Toast.LENGTH_SHORT).show()
                }
            }
            else Toast.makeText(this,"사용자 정보를 입력해주세요",Toast.LENGTH_SHORT).show()
        }
    }

    inner class createUser_sync internal constructor(
        private val check_name: String,
        private val check_id: String,
        private val check_pw: String
    ) : AsyncTask<Void, Void, String>() {
        private val progressDialog = ProgressDialog(this@Create_user_Activity)

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.setMessage("회원정보 생성 중..")
            progressDialog.show()
        }

        override fun doInBackground(vararg params: Void):String {
            val create_result:String = createUser_request(check_name,check_id,check_pw)
            return create_result

        }
        override fun onPostExecute(create_result: String) { //UI처리
            val pref = shared_pref(this@Create_user_Activity)
            if(create_result=="200"){
                Handler().postDelayed({progressDialog.dismiss()},500)
                Toast.makeText(applicationContext,"회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
                val nextintent = Intent(this@Create_user_Activity,LoginActivity::class.java)
                startActivity(nextintent)
                finish()
            }
            else if(create_result=="502"){
                Handler().postDelayed({progressDialog.dismiss()},500)
                Toast.makeText(applicationContext,"서버에 접속할 수 없습니다.", Toast.LENGTH_SHORT).show()

            }
            else if(create_result=="500"){
                Handler().postDelayed({progressDialog.dismiss()},500)
//                Toast.makeText(applicationContext,"이미 가입된 계정입니다.", Toast.LENGTH_SHORT).show()
                input_name.error = "이미 가입된 계정입니다."
                input_create_id.error = "이미 가입된 계정입니다."
            }
            else{
                Handler().postDelayed({progressDialog.dismiss()},500)
                Toast.makeText(applicationContext,"서버에 접속할 수 없습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
