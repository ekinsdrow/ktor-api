package com.ekinsdrow

import com.ekinsdrow.routing.registerCustomRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        main()
    }.start(wait = true)
}


fun Application.main() {
    install(ContentNegotiation) {
        json()
    }

    install(StatusPages) {
        statusFile(HttpStatusCode.NotFound, HttpStatusCode.Unauthorized, filePattern = "errors/error#.json")
    }

    registerCustomRouting()
}
