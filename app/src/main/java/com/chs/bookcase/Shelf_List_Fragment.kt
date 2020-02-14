package com.chs.bookcase

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chs.bookcase.data_binding_class.Dummy
import com.chs.bookcase.data_binding_class.Shelfs
import com.chs.bookcase.recycler_view_apater.Book_Shelf_Adapter
import com.chs.bookcase.network_request.get_Shelf_state
import com.chs.bookcase.network_request.get_category
import com.chs.bookcase.recycler_view_apater.NoshelfAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class main_fr : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setHasOptionsMenu(true)
        get_shelf_sync().execute()
        return root
    }

    companion object{
        fun newInstance():main_fr{
            return main_fr()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId){
        R.id.action_refresh ->{
            get_shelf_sync().execute()
            true
        }
        else -> false
    }
    inner class get_shelf_sync internal constructor() : AsyncTask<Void, Void, Array<Array<String>>>() {
        private val progressDialog = ProgressDialog(activity)
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog.setMessage("책장 리스트 가져오는 중..")
            progressDialog.setCancelable(false)
            progressDialog.show()
        }

        override fun doInBackground(vararg params: Void):Array<Array<String>> {
            val category_result:Array<Array<String>> =get_category()
            return category_result
        }
        override fun onPostExecute(category_result: Array<Array<String>>) { //UI처리
            val acategory = arrayListOf<Shelfs>()
            acategory.add(Shelfs("소설/문학",R.drawable.ico_ok,0))
            acategory.add(Shelfs("역사",R.drawable.ico_waning,1))
            shelf_recycler_view.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = activity?.let {
                    Book_Shelf_Adapter(it, acategory) { }
                }
            }
            progressDialog.dismiss()
        }
    }
}
