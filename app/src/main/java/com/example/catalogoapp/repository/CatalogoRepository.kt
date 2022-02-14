package com.example.catalogoapp.repository

import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.data.db.CategoryEntity
import com.example.catalogoapp.data.db.ProductEntity

class CatalogoRepository(
    val db: AppDatabase
) {

    suspend fun insertProduct(productEntity: ProductEntity) =
        db.getCatalogoDao().insertProduct(productEntity)

    suspend fun updateProduct(productEntity: ProductEntity) =
        db.getCatalogoDao().updateProduct(productEntity)

    suspend fun deleteProductById(productId: Long) =
        db.getCatalogoDao().deleteProductById(productId)
    suspend fun getAllProducts() =
        db.getCatalogoDao().getAllProducts()

    suspend fun getProductById(productId: Long) = db.getCatalogoDao().getProductById(productId)

    suspend fun insertCategory(category: CategoryEntity) =
        db.getCatalogoDao().insertCategory(category)

    suspend fun updateCategory(category: CategoryEntity) =
        db.getCatalogoDao().updateCategory(category)

    suspend fun deleteCategory(category: CategoryEntity) =
        db.getCatalogoDao().deleteCategory(category)
    suspend fun getAllCategories() =
        db.getCatalogoDao().getAllCategories()

    suspend fun clearAllTables() = db.clearAllTables()
}