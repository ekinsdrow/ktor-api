package com.ekinsdrow.data.repositories

import com.ekinsdrow.data.models.User

interface IUsersRepository {
    fun getUsers(): List<User>
    fun saveUser(user: User)
}