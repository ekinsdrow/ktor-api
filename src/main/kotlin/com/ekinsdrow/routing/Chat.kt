package com.ekinsdrow.routing

import com.ekinsdrow.data.models.Message
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Route.chatRoute() {
    webSocket("/chat") {
        for (frame in incoming) {
            when (frame) {
                is Frame.Text -> {
                    val message = Json.decodeFromString<Message>(frame.readText())
                    send(Json.encodeToString(Message.serializer(), message))
                }
                else -> send("You send bad message")
            }
        }

    }
}