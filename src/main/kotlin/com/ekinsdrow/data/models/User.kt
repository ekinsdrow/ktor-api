package com.ekinsdrow.data.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String, val id: Int)
