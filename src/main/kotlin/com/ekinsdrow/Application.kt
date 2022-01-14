package com.ekinsdrow

import com.ekinsdrow.domain.RoomsController
import com.ekinsdrow.data.repositories.IRoomsRepository
import com.ekinsdrow.data.repositories.IUsersRepository
import com.ekinsdrow.data.repositories.RoomsRepositoryLocalImpl
import com.ekinsdrow.data.repositories.UserRepositoryLocalImpl
import com.ekinsdrow.domain.UsersController
import com.ekinsdrow.routing.registerCustomRouting
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
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

    val userRepository: IUsersRepository = UserRepositoryLocalImpl()
    val usersController = UsersController(userRepository)

    registerCustomRouting(roomsController, usersController)
}
