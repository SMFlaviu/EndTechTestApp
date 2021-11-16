package com.flaviu.endtechtestapp.util

sealed class DataStateEvent {
    object GetClothesEvent: DataStateEvent()
}