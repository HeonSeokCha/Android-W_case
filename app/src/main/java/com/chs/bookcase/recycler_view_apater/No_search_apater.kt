package com.chs.bookcase.recycler_view_apater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.bookcase.R
import com.chs.bookcase.data_binding_class.Dummy
import com.chs.bookcase.databinding.ItemEmptyBinding

class SearchAdapter(val items:List<Dummy>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){
    class SearchViewHolder(val binding:ItemEmptyBinding):RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_empty,parent,false)
        val viewHolder = SearchViewHolder(ItemEmptyBinding.bind(view))
        return viewHolder
    }
    override fun getItemCount()=items.size
    override fun onBindViewHolder(holder: SearchAdapter.SearchViewHolder, position: Int) {
        holder.binding.dummySearchBook = items[position]
    }
}