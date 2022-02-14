package com.example.catalogoapp.ui.dbTransaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.data.db.CategoryEntity
import com.example.catalogoapp.data.db.ProductEntity
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class DbTransactionViewModel(
    private val repository: CatalogoRepository
): ViewModel() {

    val isTransactionDone: MutableLiveData<Boolean> = MutableLiveData()
    val gettedProductById: MutableLiveData<ProductEntity> = MutableLiveData()

    suspend fun addProductToDB(productEntity: ProductEntity) {
        viewModelScope.launch {
            repository.insertProduct(productEntity)
        }
    }

    suspend fun updateProductOnBD(productEntity: ProductEntity) {
        viewModelScope.launch {
            repository.updateProduct(productEntity)
        }
    }

    fun deleteProductByIdOnBD(productId: Long) {
        viewModelScope.launch {
            repository.deleteProductById(productId)
        }
    }

    fun getProductById(productId: Long) {
        viewModelScope.launch {
            gettedProductById.postValue(repository.getProductById(productId))
        }
    }

    suspend fun addCategoryToDB(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)
        }
    }

    fun updateCategoryToBd(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.deleteCategory(categoryEntity)
        }
    }

    suspend fun listAllCategories(): List<CategoryEntity> {
        return repository.getAllCategories()
    }

    suspend fun clearAllTables() = repository.clearAllTables()
}