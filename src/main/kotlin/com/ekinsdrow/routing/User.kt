package com.ekinsdrow.routing

import com.ekinsdrow.data.models.User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoute() {
    val users = listOf(User(1, "Ivan"), User(2, "Oleg"))

    route("/user") {
        get("{id}") {
            val idString = call.parameters["id"]!!
            val id = idString.toIntOrNull()
            val user = users.find { it.id == id } ?: return@get call.respondText(
                "User Not Found",
                status = HttpStatusCode.NotFound,
            )
            call.respond(user)
        }
    }
}