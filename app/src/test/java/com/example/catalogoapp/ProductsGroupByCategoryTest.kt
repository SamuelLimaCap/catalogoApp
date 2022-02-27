package com.example.catalogoapp

import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.utils.ProductsGroupByCategoryUtils
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test

class ProductsGroupByCategoryTest {

    private lateinit var list: ProductsGroupByCategory
    private lateinit var expectedList: List<ProductsGroupByCategory>

    @Before
    fun setup() {
        list = ProductsGroupByCategory("test",
            listOf(ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1")))

        expectedList = listOf<ProductsGroupByCategory>(
            ProductsGroupByCategory("test",
                listOf(ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                    ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                    ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                    ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"))),
            ProductsGroupByCategory("",
                listOf(ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                    ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1"),
                    ProductEntity(0, "t1", 0.0F, "c1", "c1", "c1")))
        )
    }

    @Test
    fun `returns two groups grouped by 4 produtcs, one with category name and one blank in categoryName`() {
        val result = ProductsGroupByCategoryUtils.transformListIntoExportList(list);
        Truth.assertThat(result).isEqualTo(expectedList)

    }
}