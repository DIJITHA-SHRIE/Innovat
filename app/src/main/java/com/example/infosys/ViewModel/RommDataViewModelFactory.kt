package com.example.infosys.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.infosys.Repository.RoomDataRepository

class RommDataViewModelFactory(private val roomDataRepository: RoomDataRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RoomDataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            RoomDataViewModel(this.roomDataRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}