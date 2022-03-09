package com.example.catalogoapp.repository

import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory

class CatalogoRepository(
    val db: AppDatabase
) {

    suspend fun insertProduct(productEntity: ProductEntity) = db.getCatalogoDao().insertProduct(productEntity)

    suspend fun updateProduct(productEntity: ProductEntity) = db.getCatalogoDao().updateProduct(productEntity)

    suspend fun deleteProductById(productId: Long) = db.getCatalogoDao().deleteProductById(productId)

    suspend fun getAllProducts() = db.getCatalogoDao().getAllProducts()

    suspend fun getProductsGroupByCategory(): List<ProductsGroupByCategory> {
        val products = db.getCatalogoDao().getAllProductsGroupByCategory()
        val map = mutableMapOf<String, List<ProductEntity>>()
        val stringList = mutableListOf<String>()
        val listProducts = mutableListOf<ProductEntity>()
        val listGrouped = mutableListOf<ProductsGroupByCategory>()
        products.forEach {
            if (!map.contains(it.categoryName)) {
                val list = listProducts.toList()
                val last = if (stringList.isNotEmpty()) stringList.last() else it.categoryName
                map.put(last, list)
                stringList.add(it.categoryName)
                listProducts.clear()
            }
            listProducts.add(it)
        }
        if (stringList.isNotEmpty()) map.put(stringList.last(), listProducts.toList())
        listProducts.clear()

        for ((categoryName, list) in map) {
            val category = db.getCatalogoDao().getCategoryByName(categoryName)
            listGrouped.add(ProductsGroupByCategory(category, list))
        }

        return listGrouped
    }

    suspend fun getAllProductsByCategory(categoryName: String) =
        db.getCatalogoDao().getAllProductsByCategory(categoryName)

    suspend fun getProductById(productId: Long) = db.getCatalogoDao().getProductById(productId)

    suspend fun insertCategory(category: CategoryEntity) = db.getCatalogoDao().insertCategory(category)

    suspend fun updateCategory(oldCategoryName: String, newCategoryName: String) =
        db.getCatalogoDao().updateCategory(oldCategoryName, newCategoryName)

    suspend fun deleteCategory(category: CategoryEntity) = db.getCatalogoDao().deleteCategory(category.category)

    suspend fun getAllCategories() = db.getCatalogoDao().getAllCategories()

    suspend fun getCategoryByName(categoryName: String) = db.getCatalogoDao().getCategoryByName(categoryName)

    suspend fun clearAllTables() = db.clearAllTables()

    suspend fun getCountByListCategory(categoryList: List<String>) =
        db.getCatalogoDao().getCountByCategoryList(categoryList)
}