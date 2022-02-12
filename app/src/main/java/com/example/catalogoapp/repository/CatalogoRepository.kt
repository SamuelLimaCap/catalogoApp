package com.example.catalogoapp.repository

import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.data.db.CategoryEntity
import com.example.catalogoapp.data.db.ProductEntity

class CatalogoRepository(
    val db: AppDatabase
) {

    suspend fun insertProduct(productEntity: ProductEntity) =
        db.getCatalogoDao().insertProduct(productEntity)

    suspend fun getAllProducts() =
        db.getCatalogoDao().getAllProducts()

    suspend fun insertCategory(category: CategoryEntity) =
        db.getCatalogoDao().insertCategory(category)

    suspend fun getAllCategories() =
        db.getCatalogoDao().getAllCategories()

    suspend fun clearAllTables() = db.clearAllTables()
}