package com.ekinsdrow.domain

import com.ekinsdrow.data.models.Message
import com.ekinsdrow.data.models.Room
import com.ekinsdrow.data.repositories.IRoomsRepository

class RoomsController(private val roomsRepo: IRoomsRepository) {

    fun createRoom(name: String): Room {
        val rooms = roomsRepo.getRooms()
        val id = if (rooms.isEmpty()) {
            0
        } else {
            rooms.last().id + 1
        }
        val room = Room(id = id, name = name, messages = listOf())
        roomsRepo.saveRoom(room)
        return room
    }

    fun getAllRooms(): List<Room> {
        return roomsRepo.getRooms()
    }

    fun getAllMessages(roomId: Int): List<Message>? {
        val room = getRoomById(roomId) ?: return null
        return room.messages
    }

    fun addMessage(roomId: Int, message: Message) {
        roomsRepo.addMessageToRoom(roomId, message)
    }

    private fun getRoomById(roomId: Int): Room? {
        val rooms = getAllRooms()
        return rooms.findLast { it.id == roomId }
    }
}