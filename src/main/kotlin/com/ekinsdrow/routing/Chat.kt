package com.ekinsdrow.routing

import com.ekinsdrow.domain.RoomsController
import com.ekinsdrow.data.models.Message
import com.ekinsdrow.data.models.Room
import com.ekinsdrow.data.models.RoomRequestBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerializationException
import java.util.*
import kotlin.collections.LinkedHashSet

fun Route.chatRoute(roomsController: RoomsController) {

    route("/rooms") {
        post("add") {
            try {
                val name = Json.decodeFromString<RoomRequestBody>(call.receiveText()).name
                val room = roomsController.createRoom(name)
                this@route.startSocket(room = room, roomsController)
                call.respond(room)
            } catch (e: SerializationException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }

        get {
            call.respond(roomsController.getAllRooms())
        }

        get("{id}/messages") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val idInt = id.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val messages = roomsController.getAllMessages(idInt) ?: return@get call.respond(listOf<Message>())
            call.respond(messages)
        }
    }


}


fun Route.startSocket(room: Room, roomsController: RoomsController) {
    val connections = Collections.synchronizedSet<DefaultWebSocketServerSession?>(LinkedHashSet())
    webSocket("${room.id}") {
        for (frame in incoming) {
            connections.add(this)
            when (frame) {
                is Frame.Text -> {
                    try {
                        val messageFrame = frame.readText()
                        val message = Json.decodeFromString(Message.serializer(), messageFrame)
                        roomsController.addMessage(room.id, message)

                        connections.forEach {
                            it.send(messageFrame)
                        }
                    } catch (e: SerializationException) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                }
                else -> call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
