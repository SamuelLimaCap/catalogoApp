package com.example.catalogoapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.catalogoapp.data.db.ProductEntity

@Dao
interface ProductDao {

    @Insert
    fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM product")
    fun getAll() : List<ProductEntity>
}