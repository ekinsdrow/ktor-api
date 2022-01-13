package com.ekinsdrow.data.repositories

import com.ekinsdrow.data.models.Message
import com.ekinsdrow.data.models.Room

class RoomsRepositoryLocalImpl : IRoomsRepository {
    private val rooms = mutableListOf<Room>()

    override fun getRooms(): List<Room> {
        return rooms
    }

    override fun saveRoom(room: Room) {
        rooms.add(room)
    }

    override fun addMessageToRoom(roomId: Int, message: Message) {
        val oldRoom = rooms.findLast { it.id == roomId }
        if (oldRoom != null) {
            val oldRoomIndex = rooms.indexOf(oldRoom)
            val newMessages = oldRoom.messages.toMutableList()
            newMessages.add(message)
            val newRoom = Room(id = oldRoom.id, name = oldRoom.name, messages = newMessages)
            rooms[oldRoomIndex] = newRoom
        }

    }
}