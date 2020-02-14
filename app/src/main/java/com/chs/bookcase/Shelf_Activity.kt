package com.chs.bookcase

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chs.bookcase.data_binding_class.Books
import com.chs.bookcase.data_binding_class.Dummy
import com.chs.bookcase.network_request.*
import com.chs.bookcase.recycler_view_apater.BookAdapter
import com.chs.bookcase.recycler_view_apater.NobookAdapter
import com.chs.bookcase.recycler_view_apater.SearchAdapter
import kotlinx.android.synthetic.main.activity_book_shelf.*
import kotlinx.android.synthetic.main.book_dialog.view.*
import kotlinx.android.synthetic.main.content_book_shelf.*
import kotlinx.android.synthetic.main.fragment_find_book.*

class book_shelf : AppCompatActivity() {

    var category_name = "애욱"
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_shelf)
        setSupportActionBar(toolbar)
        if(intent.hasExtra("category_name")){
            category_name= intent.getStringExtra("category_name")
        }
        title = category_name

        get_book_sync(category_name).execute()


    }

    inner class get_book_sync internal constructor(
        private val book_category: String) : AsyncTask<Void, Void,ArrayList<Books>>() {
        private val progressDialog = ProgressDialog(this@book_shelf)
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.setMessage("책장에 접속 중..")
            progressDialog.show()
        }
        override fun doInBackground(vararg params: Void):ArrayList<Books> {
            send_signal_pi(book_category)
            val abook = arrayListOf<Books>()
            if(category_name=="소설/문학")
                abook.add(Books("철학은 어떻게 삶의 무기가 되는가","야마구치 슈" , "소설/문학","http://image.yes24.com/goods/68749139/800x0",0))
            else if(category_name=="역사"){
                abook.add(Books("덩케르크","에드워드 키블 채터턴" , "역사","http://image.yes24.com/goods/43948843/800x0",0))
                abook.add(Books("던전에서 만남을 추구하면 안 되는 걸까","오모리 후지노" , "라이트 노벨","http://image.yes24.com/goods/77276172/800x0",1))
            }

            return abook
        }

        override fun onPostExecute(abook: ArrayList<Books>) { //UI처리
            book_recycler_view.apply {
                layoutManager = GridLayoutManager(this@book_shelf, 2)
                adapter = BookAdapter(this@book_shelf,abook) { books ->
                    val builder = AlertDialog.Builder(this@book_shelf)
                    val diaglogView = layoutInflater.inflate(R.layout.book_dialog, null)
                    diaglogView.book_dlg_book_name.text = books.title
                    diaglogView.book_dlg_book_author.text = books.author
                    diaglogView.book_dlg_book_position.text = books.postion
                    if(books.img_url!="No_img") Glide.with(this@book_shelf).load(books.img_url).into(diaglogView.book_dlg_book_img)
                    else Glide.with(this@book_shelf).load(R.drawable.ico_error_img).into(diaglogView.book_dlg_book_img)
                    builder.setView(diaglogView).setNegativeButton("닫기") { _, i ->
                    }.show()
                }
            }
            progressDialog.dismiss()
        }
    }
}
