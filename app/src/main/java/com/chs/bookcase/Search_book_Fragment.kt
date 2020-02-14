package com.chs.bookcase

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.chs.bookcase.data_binding_class.Books
import com.chs.bookcase.data_binding_class.Dummy
import com.chs.bookcase.network_request.get_book_detail
import com.chs.bookcase.network_request.search_book
import com.chs.bookcase.recycler_view_apater.BookAdapter
import com.chs.bookcase.recycler_view_apater.SearchAdapter
import com.chs.bookcase.recycler_view_apater.Search_book_apater
import kotlinx.android.synthetic.main.book_dialog.view.*
import kotlinx.android.synthetic.main.fragment_find_book.*


class Search_book_Fragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root:View = inflater.inflate(R.layout.fragment_find_book, container, false)
        setHasOptionsMenu(true)
        search_book_sync("").execute()
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.queryHint="검색"
        searchView.setOnQueryTextListener(ActionListener())
        super.onCreateOptionsMenu(menu, inflater)

    }
    companion object {
        fun newInstance(): Search_book_Fragment {
            return Search_book_Fragment()
        }
    }

    inner class ActionListener : SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {

            search_book_sync(query!!.trim()).execute()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if(newText?.length==0){
                this.onQueryTextSubmit("")
            }
            return true
        }
    }

    inner class search_book_sync internal constructor(
        private val book_title: String) : AsyncTask<Void, Void,ArrayList<Books>>() {
        private val progressDialog = ProgressDialog(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.setMessage("책 자료를 검색하는 중..")
            progressDialog.show()
        }

        override fun doInBackground(vararg params: Void):ArrayList<Books> {
            val book_result:Array<Array<String>> = search_book(book_title)
            val abook = arrayListOf<Books>()
            abook.add(Books("철학은 어떻게 삶의 무기가 되는가","야마구치 슈" , "소설/문학","http://image.yes24.com/goods/68749139/800x0",0))
            abook.add(Books("덩케르크","에드워드 키블 채터턴" , "역사","http://image.yes24.com/goods/43948843/800x0",0))
            abook.add(Books("던전에서 만남을 추구하면 안 되는 걸까","오모리 후지노" , "라이트 노벨","http://image.yes24.com/goods/77276172/800x0",1))
            return abook
        }

        override fun onPostExecute(abook: ArrayList<Books>) { //UI처리
            if(abook.isNotEmpty()){
                find_book_recyclerView.setHasFixedSize(true)
                find_book_recyclerView.apply {
                    layoutManager = GridLayoutManager(activity, 2)
                    adapter = Search_book_apater(activity!!,abook) { books ->
                        val builder = AlertDialog.Builder(activity)
                        val diaglogView = layoutInflater.inflate(R.layout.book_dialog, null)
                        diaglogView.book_dlg_book_name.text = books.title
                        diaglogView.book_dlg_book_author.text = books.author
                        diaglogView.book_dlg_book_position.text = books.postion
                        if(books.img_url!="No_img") Glide.with(this@Search_book_Fragment).load(books.img_url).into(diaglogView.book_dlg_book_img)
                        else diaglogView.book_dlg_book_img.setImageResource(R.drawable.ico_error_img)
                        builder.setView(diaglogView).setNegativeButton("닫기") { _, i ->
                        }.show()
                    }
                }
                progressDialog.dismiss()
            }
            else{
                var dumm_arr= arrayListOf<Dummy>()
                dumm_arr.add(Dummy(""))
                find_book_recyclerView.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = SearchAdapter(dumm_arr)
                }
                Toast.makeText(activity,"검색결과가 없습니다.",Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
    }
}
