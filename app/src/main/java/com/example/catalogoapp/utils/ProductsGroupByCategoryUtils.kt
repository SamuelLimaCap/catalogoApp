package com.example.catalogoapp.utils

import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory

object ProductsGroupByCategoryUtils {

    fun transformListIntoExportList(productsGroupCategory: ProductsGroupByCategory): List<ProductsGroupByCategory> {
        val newList = mutableListOf<ProductsGroupByCategory>()
            val temporaryList: MutableList<ProductEntity> = mutableListOf()
            var count = 0
            var categoryName = productsGroupCategory.category
            val listProductsGroupByCategory: MutableList<ProductsGroupByCategory> = mutableListOf()

            for (product in productsGroupCategory.productList) {
                when (count) {
                    0 -> {
                        temporaryList.clear()
                        temporaryList.add(product)
                        count++
                    }
                    in (1..3) -> {
                        temporaryList.add(product)
                        count++
                    }
                    4 -> {
                        count = 0
                        listProductsGroupByCategory.add(ProductsGroupByCategory(categoryName, temporaryList.toMutableList()))
                        categoryName = ""
                        temporaryList.clear()
                        temporaryList.add(product)
                        count = 1;
                    }
                }
            }

        if (temporaryList.isNotEmpty()) {
            listProductsGroupByCategory.add(ProductsGroupByCategory(categoryName, temporaryList))
        }

        return listProductsGroupByCategory.toList()
    }
}