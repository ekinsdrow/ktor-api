package com.ekinsdrow.routing

import com.ekinsdrow.data.models.Message
import com.ekinsdrow.data.models.Room
import com.ekinsdrow.data.models.RoomRequestBody
import io.ktor.http.*
import io.ktor.http.websocket.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Route.chatRoute() {

    route("/rooms") {
        val rooms = mutableListOf<Room>()

        for (i in 0..2) {
            rooms.add(Room(id = i, name = "Room $i"))
        }


        post("add") {
            val id = rooms.last().id
            val name = Json.decodeFromString<RoomRequestBody>(call.receiveText()).name
            val room = Room(id + 1, name)
            rooms.add(room)
            call.respond(room)
        }

        get {
            call.respond(rooms)
        }

    }

    webSocket("/chat") {
        for (frame in incoming) {
            when (frame) {
                is Frame.Text -> {
                    val message = Json.decodeFromString(Message.serializer(), frame.readText())
                    send(Frame.Text(Json.encodeToString(Message.serializer(), message)))
                }
                else -> send("shit")
            }
        }
    }
}


