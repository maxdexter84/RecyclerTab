package ru.maxdexter.recyclertab.ui.adapters

import RoomEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ViewColorAdapter(private val click:(Boolean)->Unit):   ListAdapter<RoomEvent, RecyclerView.ViewHolder>(EventDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var vh = when(viewType){
            0 -> MyViewHolderEvent.create(parent)
            else -> MyViewHolder.create(parent)
        }
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as BaseHolder
        holder.bind(currentList[holder.adapterPosition])
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position].haveEvent){
            true -> 0
            false -> 1
        }
    }

}