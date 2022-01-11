package com.ekinsdrow.routing

import com.ekinsdrow.data.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute() {
    val users = listOf(User( "Ivan"), User( "Oleg"))

    route("/user") {
        get("{name}") {
            val name = call.parameters["name"]!!
            val user = users.find { it.name == name } ?: return@get call.respondText(
                "User Not Found",
                status = HttpStatusCode.NotFound,
            )
            call.respond(user)
        }
    }
}