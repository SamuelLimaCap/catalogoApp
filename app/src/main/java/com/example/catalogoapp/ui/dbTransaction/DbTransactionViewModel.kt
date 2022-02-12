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

    suspend fun addProductToDB(productEntity: ProductEntity) {
        viewModelScope.launch {
            repository.insertProduct(productEntity)
        }
    }

    suspend fun addCategoryToDB(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)
        }
    }

    suspend fun listAllCategories(): List<CategoryEntity> {
        return repository.getAllCategories()
    }

    suspend fun clearAllTables() = repository.clearAllTables()
}