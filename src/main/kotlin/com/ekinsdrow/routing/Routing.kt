package com.ekinsdrow.routing
import com.ekinsdrow.controllers.RoomsController
import com.ekinsdrow.data.models.Message
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json


fun Application.registerCustomRouting(roomsController: RoomsController) {
    routing {
        userRoute()
        chatRoute(roomsController)
    }
}




