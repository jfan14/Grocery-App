package com.junfan.groceryapp.models

import java.io.Serializable

data class UserResponse(
    val token: String,
    val user: User
): Serializable

data class User(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val email: String,
    val firstName: String,
    val mobile: String,
    val password: String
): Serializable