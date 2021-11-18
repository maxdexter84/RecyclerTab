package ru.maxdexter.recyclertab.ui.adapters

import RoomEvent

interface BaseHolder {
    fun bind(item: RoomEvent)
}