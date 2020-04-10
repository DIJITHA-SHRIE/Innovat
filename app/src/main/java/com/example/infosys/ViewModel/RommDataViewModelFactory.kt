package com.example.infosys.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.infosys.Repository.RoomDataRepository

class RommDataViewModelFactory(private val roomDataRepository: RoomDataRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RoomDataViewModel::class.java!!)) {
            RoomDataViewModel(this.roomDataRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}