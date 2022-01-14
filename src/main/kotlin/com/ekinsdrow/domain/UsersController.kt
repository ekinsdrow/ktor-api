package com.ekinsdrow.domain

import com.ekinsdrow.data.models.User
import com.ekinsdrow.data.repositories.IUsersRepository

class UsersController(private val userRepository: IUsersRepository) {
    fun createUser(name: String): User {
        val users = userRepository.getUsers()
        val id = if (users.isEmpty()) {
            0
        } else {
            users.last().id + 1
        }

        val user = User(name, id)
        userRepository.saveUser(user)
        return user
    }

    fun getAllUsers(): List<User> {
        return userRepository.getUsers()
    }

    fun getUser(id: Int): User? {
        return userRepository.getUsers().findLast { it.id == id }
    }

}