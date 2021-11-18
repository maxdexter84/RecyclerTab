package ru.maxdexter.recyclertab.ui.adapters

import RoomEvent
import androidx.recyclerview.widget.DiffUtil

class EventDiff : DiffUtil.ItemCallback<RoomEvent>() {
    override fun areItemsTheSame(oldItem: RoomEvent, newItem: RoomEvent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RoomEvent, newItem: RoomEvent): Boolean {
        return oldItem.startTime == newItem.startTime
    }
}