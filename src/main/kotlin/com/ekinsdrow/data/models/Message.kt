package com.ekinsdrow.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Message(val text: String, val user: User)
