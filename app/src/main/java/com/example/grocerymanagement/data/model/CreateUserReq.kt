package com.example.grocerymanagement.data.model


import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

data class CreateUserReq(val name: String, val email: String, val password: String) {
    fun toJson(): String {
        return Json.encodeToString(this)
    }
}
