package com.ekinsdrow.routing

import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay

fun Route.chatRoute() {
    webSocket("/chat") {
        while (true){
            send("Test")
            delay(1000)
        }
    }
}