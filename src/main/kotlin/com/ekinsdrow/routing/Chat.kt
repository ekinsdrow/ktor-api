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
import com.ekinsdrow.routing.startSocket

fun Route.chatRoute() {

    val rooms = mutableListOf<Room>()

    route("/rooms") {
        post("add") {
            val id = if(rooms.isEmpty()){
                0
            }else{
                rooms.last().id + 1
            }
            val name = Json.decodeFromString<RoomRequestBody>(call.receiveText()).name
            val room = Room(id, name)
            rooms.add(room)

            this@route.startSocket(id)

            call.respond(room)
        }

        get {
            call.respond(rooms)
        }

    }


}


fun Route.startSocket(index: Int) {
    webSocket("$index") {
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