package com.example.infosys.Utilities

import android.content.Context
import com.example.infosys.Database.AppDatabase
import com.example.infosys.Repository.RoomDataRepository
import com.example.infosys.ViewModel.RommDataViewModelFactory

object InjectorUtils {

    private fun getRoomDataRepository(context: Context): RoomDataRepository {
        return RoomDataRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).userDao())
    }

    fun provideRoomDataViewModelFactory(
        context: Context
    ): RommDataViewModelFactory {
        return RommDataViewModelFactory(getRoomDataRepository(context))
    }

}