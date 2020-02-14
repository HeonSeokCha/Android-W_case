package com.chs.bookcase.recycler_view_apater

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.chs.bookcase.R
import com.chs.bookcase.book_shelf
import com.chs.bookcase.data_binding_class.Shelfs
import com.chs.bookcase.databinding.ItemShelfsBinding
import kotlinx.android.synthetic.main.item_shelfs.view.*

class Book_Shelf_Adapter(val mcontext: FragmentActivity, val items:List<Shelfs>, private val clickListener:(shelfs: Shelfs)->Unit) : RecyclerView.Adapter<Book_Shelf_Adapter.Book_Shelf_ViewHolder>(){
    class Book_Shelf_ViewHolder(val binding: ItemShelfsBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Book_Shelf_ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shelfs,parent,false)
        val viewHolder =
            Book_Shelf_ViewHolder(
                ItemShelfsBinding.bind(view)
            )

        view.setOnClickListener {
            if (viewHolder.adapterPosition != RecyclerView.NO_POSITION)
                clickListener.invoke(items[viewHolder.adapterPosition])
            var nextIntent = Intent(mcontext, book_shelf::class.java)
            nextIntent.putExtra("category_name", items[viewHolder.adapterPosition].shlef_name)
            mcontext.startActivity(nextIntent)
        }

        return viewHolder
    }

    override fun getItemCount()=items.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Book_Shelf_ViewHolder, position: Int) {
        holder.binding.shelfdata = items[position]
        holder.itemView.item_shelf_img.setImageResource(items[position].shelf_img)
        if(items[position].shelf_msg==0) holder.itemView.item_shelf_img.tooltipText="현재 발견된 문제가 없습니다."
        else holder.itemView.item_shelf_img.tooltipText="책장에 잘못된 책이 있습니다."
    }
}