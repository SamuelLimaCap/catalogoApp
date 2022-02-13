package com.example.catalogoapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.catalogoapp.data.db.CategoryEntity
import com.example.catalogoapp.data.db.ProductEntity

@Dao
interface CatalogoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Query("SELECT * FROM product")
    suspend fun getAllProducts() : List<ProductEntity>

    @Query("SELECT * FROM product WHERE id =:productId")
    suspend fun getProductById(productId: Long) : ProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("SELECT * FROM category")
    suspend fun getAllCategories() : List<CategoryEntity>

}