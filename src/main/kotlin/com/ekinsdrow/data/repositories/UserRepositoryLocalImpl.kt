package com.ekinsdrow.data.repositories

import com.ekinsdrow.data.models.User

class UserRepositoryLocalImpl : IUsersRepository {
    private val users = mutableListOf<User>()


    override fun getUsers(): List<User> {
        return users
    }

    override fun saveUser(user: User) {
        users.add(user)
    }
}