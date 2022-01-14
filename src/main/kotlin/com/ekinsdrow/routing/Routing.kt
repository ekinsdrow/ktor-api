package com.ekinsdrow.routing
import com.ekinsdrow.domain.RoomsController
import com.ekinsdrow.domain.UsersController
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.registerCustomRouting(roomsController: RoomsController, usersController: UsersController) {
    routing {
        userRoute(usersController)
        chatRoute(roomsController)
    }
}




