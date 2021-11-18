package ru.maxdexter.recyclertab.ui.adapters

import RoomEvent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ru.maxdexter.recyclertab.databinding.ItemViewBinding
import ru.maxdexter.recyclertab.databinding.ItemViewEventBinding

class MyViewHolderEvent(private var binding: ItemViewEventBinding) : RecyclerView.ViewHolder(binding.root), BaseHolder {
    lateinit var currentEvent: RoomEvent

    override fun bind(item: RoomEvent) {
        currentEvent = item
        binding.cvEvent.apply {
            minimumHeight = item.minuteToDp()
            setBackgroundColor(item.colorRoom)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MyViewHolderEvent {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemViewEventBinding.inflate(inflater, parent, false)
            return MyViewHolderEvent(binding)
        }

    }
}