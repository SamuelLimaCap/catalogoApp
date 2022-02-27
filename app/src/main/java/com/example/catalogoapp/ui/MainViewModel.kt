package com.example.catalogoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainViewModel(
    val repository: CatalogoRepository
) : ViewModel() {

    private val productList: MutableLiveData<ProductEntity> = MutableLiveData()

    val listProductsByCategory = MutableLiveData<List<ProductsGroupByCategory>>()
    val listCategory = MutableLiveData<List<CategoryEntity>>()

    val allProductsByCategory = MutableLiveData<ProductsGroupByCategory>()
    val mapCategoryCountProduct = MutableLiveData<Map<String, Int>>()


    fun getCategories() {
        viewModelScope.launch { listCategory.postValue(repository.getAllCategories()); println(listCategory.value) }

    }

    fun getProductsGroupByCategories(listCategory: List<String>) {
        viewModelScope.launch {
            val list: MutableList<ProductsGroupByCategory> = mutableListOf()
            for (category in listCategory) {
                val products = repository.getAllProductsByCategory(category)
                val productsGroupByCategory = ProductsGroupByCategory(category, products)
                list.add(productsGroupByCategory)
            }
            listProductsByCategory.postValue(list.toList())

            return@launch
        }
    }

    fun getProductByCategory(categoryName: String) {
        viewModelScope.launch {
            repository.getAllProductsByCategory(categoryName)
        }
    }

    fun getMapCountGroupByCategory() {
        viewModelScope.launch {
            val list: MutableList<String> = mutableListOf()
            listProductsByCategory.value?.forEach {
                list.add(it.category)
            }
            mapCategoryCountProduct.postValue(repository.getCountByListCategory(list))
        }
    }

    /**
     * mapCategoryCountProduct = a map that contains category as string key and a count of product as the value
     *
     * returns the number of page that is needed to load the full content on PDF
     *
     * A PDF can load a maximum of 4 products on one page. If a category has more than 4, then theses products will be
     * loaded on a next page.
     *
     * If a product list of a category ends and the map yet contains category to load, then the next category
     * will be loaded on a new page
     */
    fun calculatePageNumbers(mapCategoryCountProduct: Map<String, Int>): Int {
        var pageCount: Int = 0
        val lastCategory = ""
        for ((category, quantity) in mapCategoryCountProduct.entries) {
            //If this category is new, then it'll be loaded on a new page
            if (category != lastCategory) {
                pageCount++
            }
            val quantityDividedBy4: Double = quantity/4.toDouble()
            //The contents will be loaded grouped by 4 products per page.
            //It's discounted one page because we're already on the first page, but with no products yet.
            if (quantityDividedBy4 > 1) {

                pageCount+= Math.ceil((quantityDividedBy4 - 1)).toInt();
            }
        }
        return pageCount
    }


}