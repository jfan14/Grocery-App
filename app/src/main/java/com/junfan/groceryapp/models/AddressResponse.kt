package com.junfan.groceryapp.models

import java.io.Serializable

data class AddressResponse(
    val data: ArrayList<Address>,
    val error: Boolean,
    val message: String
): Serializable

data class Address(
    val __v: Int,
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
): Serializable