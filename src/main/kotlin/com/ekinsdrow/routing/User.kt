package com.ekinsdrow.routing

import com.ekinsdrow.data.models.User
import com.ekinsdrow.data.models.UserRequestBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Route.userRoute() {
    val users = mutableListOf<User>()

    route("/users") {
        post("add") {
            val userBody = Json.decodeFromString<UserRequestBody>(call.receiveText())
            val id = if (users.isEmpty()) {
                0
            } else {
                users.last().id + 1
            }


            val user = User(userBody.name, id)
            users.add(user)

            call.respond(user)

        }

        get {
            call.respond(users)
        }

        get("{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                val idInt = id.toIntOrNull()
                if (idInt == null) {
                    call.respond(HttpStatusCode.NotFound)
                } else {
                    val user = users.findLast { it.id == idInt }
                    if (user == null) {
                        call.respond(HttpStatusCode.NotFound)
                    }else{
                        call.respond(user)
                    }

                }
            }
        }
    }
}