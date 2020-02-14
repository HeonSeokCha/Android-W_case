package com.chs.bookcase

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import kotlin.system.exitProcess


class Main2Activity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headeriew:View = navView.getHeaderView(0)
        val head_user_name:TextView = headeriew.findViewById(R.id.nav_user_name)
        val user_position:TextView = headeriew.findViewById(R.id.nav_user_position)
        val navController = findNavController(R.id.nav_host_fragment)
        val user_img:ImageView = headeriew.findViewById(R.id.user_img)

        if(intent.hasExtra("list")){//로그인한 아이디 사용자명 설정
            val intent_val:ArrayList<String> = intent.getSerializableExtra("list") as ArrayList<String>
            head_user_name.text = intent_val[1]
            user_position.text = intent_val[0]

        }

        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home,R.id.nav_find_book,R.id.nav_log_out),drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)//아이디로 구분
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_log_out -> {
                    val dialogBuilder = AlertDialog.Builder(this)

                    dialogBuilder.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("확인"){ _,_ ->
                        val nextIntent = Intent(this,LoginActivity::class.java)
                        startActivity(nextIntent)
                        finish()
                    }
                        .setNegativeButton("취소") {dialog,_ -> dialog.cancel() }
                    val alert = dialogBuilder.create()
                    alert.setTitle("W Case")
                    alert.setIcon(R.drawable.ico_title)
                    alert.show()
                }
                R.id.nav_home -> {
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.nav_home)
                }
                R.id.nav_find_book -> {
                    Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.nav_find_book)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed()
    {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("종료 하시겠습니까?").setCancelable(false).
                setPositiveButton("확인") {_,_ ->
                    ActivityCompat.finishAffinity(this)
                    exitProcess(0)
                }
                .setNegativeButton("취소") {dialog,_ -> dialog.cancel()
                }

            val alert = dialogBuilder.create()
            alert.setTitle("W Case")
            alert.setIcon(R.drawable.ico_title)
            alert.show()
        }
    }
}
