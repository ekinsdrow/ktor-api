package com.ekinsdrow

import com.ekinsdrow.controllers.RoomsController
import com.ekinsdrow.data.repositories.IRoomsRepository
import com.ekinsdrow.data.repositories.RoomsRepositoryLocalImpl
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
        statusFile(
            HttpStatusCode.NotFound,
            HttpStatusCode.BadRequest,
            filePattern = "errors/error#.json"
        )
    }

    val roomsRepository: IRoomsRepository = RoomsRepositoryLocalImpl()
    val roomsController = RoomsController(roomsRepository)

    registerCustomRouting(roomsController)
}
