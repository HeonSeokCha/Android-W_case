package com.chs.bookcase.recycler_view_apater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chs.bookcase.R
import com.chs.bookcase.data_binding_class.Dummy
import com.chs.bookcase.databinding.ItemNobooksBinding
import com.chs.bookcase.databinding.ItemNoshelfsBinding

class NoshelfAdapter(val items:List<Dummy>) : RecyclerView.Adapter<NoshelfAdapter.NoshelfViewHolder>(){
    class NoshelfViewHolder(val binding: ItemNoshelfsBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoshelfViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_noshelfs,parent,false)
        val viewHolder = NoshelfViewHolder(ItemNoshelfsBinding.bind(view))
        return viewHolder
    }
    override fun getItemCount()=items.size
    override fun onBindViewHolder(holder: NoshelfAdapter.NoshelfViewHolder, position: Int) {
        holder.binding.dummyNoShelfs = items[position]
    }
}