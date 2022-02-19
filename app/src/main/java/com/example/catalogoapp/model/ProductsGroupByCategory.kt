package com.example.catalogoapp.model

data class ProductsGroupByCategory(
    val category: String,
    val productList: List<ProductEntity>
)
