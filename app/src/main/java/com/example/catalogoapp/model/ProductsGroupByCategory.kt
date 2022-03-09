package com.example.catalogoapp.model

data class ProductsGroupByCategory(
    val category: CategoryEntity,
    val productList: List<ProductEntity>
)
