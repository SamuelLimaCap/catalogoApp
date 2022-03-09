package com.example.catalogoapp.data.db.dao

import androidx.room.*
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity

@Dao
interface CatalogoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("DELETE from product WHERE id= :productId ")
    suspend fun deleteProductById(productId: Long)

    @Query("SELECT * FROM product")
    suspend fun getAllProducts() : List<ProductEntity>

    @Query("SELECT * FROM product ORDER BY categoryName")
    suspend fun getAllProductsGroupByCategory() : List<ProductEntity>

    @Query("SELECT * FROM product WHERE id =:productId")
    suspend fun getProductById(productId: Long) : ProductEntity

    @Query("SELECT * FROM product WHERE categoryName = :categoryName")
    suspend fun getAllProductsByCategory(categoryName: String) : List<ProductEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Query("UPDATE category SET category = :newCategoryName WHERE category = :oldCategoryName")
    suspend fun updateCategory(oldCategoryName: String, newCategoryName: String)

    @Query("DELETE FROM category WHERE category.category = :categoryName")
    suspend fun deleteCategory(categoryName: String)

    @Query("SELECT * FROM category")
    suspend fun getAllCategories() : List<CategoryEntity>

    @Query("SELECT * FROM category WHERE category.category = :categoryName")
    suspend fun getCategoryByName(categoryName: String) : CategoryEntity

    @MapInfo(keyColumn = "categoryName", valueColumn = "count")
    @Query("SELECT categoryName, count(categoryName) as `count` FROM product WHERE categoryName IN (:categoryList) group by categoryName")
    suspend fun getCountByCategoryList(categoryList: List<String>) : Map<String, Int>

}