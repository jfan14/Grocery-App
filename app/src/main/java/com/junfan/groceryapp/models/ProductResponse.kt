package com.junfan.groceryapp.models

import java.io.Serializable

data class ProductResponse(
    val count: Int,
    val data: ArrayList<Product>,
    val error: Boolean
): Serializable

data class Product(
    val __v: Int?,
    val _id: String?,
    val catId: Int?,
    val created: String?,
    val description: String?,
    val image: String?,
    val mrp: Float?,
    val position: Int?,
    val price: Float?,
    val productName: String?,
    var quantity: Int,
    val status: Boolean?,
    val subId: Int?,
    val unit: String?
    //var count: Int

): Serializable {
    companion object {
        const val PRODUCT_KEY = "product"
    }
}