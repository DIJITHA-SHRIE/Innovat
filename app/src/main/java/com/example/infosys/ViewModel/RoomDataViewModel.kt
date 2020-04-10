package com.example.infosys.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infosys.Model.DataResponse
import com.example.infosys.Model.RowResponse
import com.example.infosys.Repository.RoomDataRepository
import kotlinx.coroutines.launch

class RoomDataViewModel (private val roomDataRepository: RoomDataRepository) : ViewModel(){

    fun updateDataToRoomVM(title: String, rows: ArrayList<RowResponse>, id: Int){
        viewModelScope.launch{
            roomDataRepository.updateDataToRoomRepo(title,rows,id)
        }
    }

    fun insertAllDataToRoomVM( dataResponse: DataResponse){
        viewModelScope.launch {
            roomDataRepository.insertAllDataToRoomRepo(dataResponse)
        }
    }

    val getAllDataFromRoomVM: LiveData<DataResponse> =
        roomDataRepository.getAllDataFromRoomRepo()

    val getDataCountFromRoomVM:Int = roomDataRepository.getDataCountFromRoomRepo()




}