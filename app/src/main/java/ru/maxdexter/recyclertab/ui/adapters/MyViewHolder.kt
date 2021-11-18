package ru.maxdexter.recyclertab.ui.adapters

import RoomEvent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.maxdexter.recyclertab.databinding.ItemViewBinding

class MyViewHolder(private var binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root), BaseHolder {
    lateinit var currentEvent: RoomEvent

    override fun bind(item: RoomEvent) {
        currentEvent = item
        binding.event.apply {
            minimumHeight = item.minuteToDp()
            setBackgroundColor(item.colorRoom)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MyViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemViewBinding.inflate(inflater, parent, false)
            return MyViewHolder(binding)
        }

    }
}