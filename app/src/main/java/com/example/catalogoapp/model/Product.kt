package com.example.catalogoapp.model

data class Product(
    val imageLocation: String?,
    val name: String,
    val price: Float,
    val category: String,
    val unit: String
)