package com.chs.bookcase.recycler_view_apater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.bookcase.R
import com.chs.bookcase.data_binding_class.Dummy
import com.chs.bookcase.databinding.ItemNobooksBinding

class NobookAdapter(val items:List<Dummy>) : RecyclerView.Adapter<NobookAdapter.NobookViewHolder>(){
    class NobookViewHolder(val binding: ItemNobooksBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NobookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_nobooks,parent,false)
        val viewHolder = NobookViewHolder(ItemNobooksBinding.bind(view))
        return viewHolder
    }
    override fun getItemCount()=items.size
    override fun onBindViewHolder(holder: NobookAdapter.NobookViewHolder, position: Int) {
        holder.binding.dummyNoBook = items[position]
    }
}