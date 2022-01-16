package com.ekinsdrow.routing

import com.ekinsdrow.data.models.UserRequestBody
import com.ekinsdrow.domain.UsersController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


fun Route.userRoute(usersController: UsersController) {

    route("/users") {
        post("add") {
            try {
                val name = Json.decodeFromString<UserRequestBody>(call.receiveText()).name
                val user = usersController.createUser(name)
                call.respond(user)
            } catch (e: SerializationException) {
                call.respond(HttpStatusCode.BadRequest)
            }

        }

        get {
            call.respond(usersController.getAllUsers())
        }

        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val idInt = id.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
            val user = usersController.getUser(idInt)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(user)
            }
        }
    }
}