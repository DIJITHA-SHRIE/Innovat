package com.example.infosys.Repository

import com.example.infosys.Database.RoomDao
import com.example.infosys.Model.DataResponse
import com.example.infosys.Model.RowResponse

class RoomDataRepository private constructor(
    private val roomDao: RoomDao
) {


    fun updateDataToRoomRepo(title: String, rows: ArrayList<RowResponse>, id: Int){
        roomDao.updateTodo(title,rows,id)
    }
    fun insertAllDataToRoomRepo(dataResponse: DataResponse){
        roomDao.insertAll(dataResponse)
    }
    fun getAllDataFromRoomRepo() = roomDao.getAll()

    fun getDataCountFromRoomRepo() = roomDao.getCount()


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: RoomDataRepository? = null

        fun getInstance(roomDao: RoomDao) =
            instance ?: synchronized(this) {
                instance ?: RoomDataRepository(roomDao).also { instance = it }
            }
    }
}