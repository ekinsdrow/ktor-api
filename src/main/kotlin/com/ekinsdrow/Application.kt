package com.ekinsdrow

import com.ekinsdrow.routing.registerCustomRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.websocket.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {
    install(ContentNegotiation) {
        json()
    }

    install(WebSockets)

    install(StatusPages) {
        statusFile(HttpStatusCode.NotFound, HttpStatusCode.Unauthorized, filePattern = "errors/error#.json")
    }

    registerCustomRouting()
}
