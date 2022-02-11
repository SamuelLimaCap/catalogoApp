package com.example.catalogoapp.repository

import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.data.db.ProductEntity

class CatalogoRepository(
    val db: AppDatabase
) {

    suspend fun insertProduct(productEntity: ProductEntity) =
        db.getProductDao().insertProduct(productEntity)

    suspend fun getAllProducts() = db.getProductDao().getAll()


}