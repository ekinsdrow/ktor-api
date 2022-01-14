package com.ekinsdrow.data.repositories

import com.ekinsdrow.data.models.Message
import com.ekinsdrow.data.models.Room
import com.ekinsdrow.data.models.User

interface IRoomsRepository {
    fun getRooms(): List<Room>
    fun saveRoom(room: Room)
    fun addMessageToRoom(roomId: Int, message: Message)
}