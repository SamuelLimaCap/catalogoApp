package com.example.catalogoapp.ui.dbTransaction

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.R
import com.example.catalogoapp.model.CategoryEntity
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.exception.InvalidInputException
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class DbTransactionViewModel(
    private val repository: CatalogoRepository
): ViewModel() {

    val isTransactionDone: MutableLiveData<Boolean> = MutableLiveData()
    val gettedProductById: MutableLiveData<ProductEntity> = MutableLiveData()

    val errorTransactionInfo: MutableLiveData<Int> = MutableLiveData()

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

    fun addCategoryToDB(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            repository.insertCategory(categoryEntity)
        }
    }

    fun updateCategoryToBD(oldCategoryName: String, newCategoryName: String) {
        viewModelScope.launch {
            repository.updateCategory(oldCategoryName, newCategoryName)
        }
    }

    fun deleteCategory(categoryEntity: CategoryEntity) {
        viewModelScope.launch {
            try {
                repository.deleteCategory(categoryEntity)
            } catch (e: SQLiteConstraintException) {
                errorTransactionInfo.postValue(R.string.error_category_constraint_transaction)
            }
        }
    }

    suspend fun listAllCategories(): List<CategoryEntity> {
        return repository.getAllCategories()
    }

    suspend fun clearAllTables() = repository.clearAllTables()

    fun isProductContentValid(productEntity: ProductEntity): Boolean {
        if (productEntity.name.isEmpty()) {
            throw InvalidInputException(R.string.error_product_name_empty)
        }
        if (productEntity.categoryName.isEmpty()) {
            throw InvalidInputException(R.string.error_category_empty)
        }
        if (productEntity.unit.isEmpty()) {
            throw InvalidInputException(R.string.error_options_empty)
        }
        if (productEntity.price < 0.0F) {
            throw InvalidInputException(R.string.error_product_price_empty)
        }

        return true
    }

    fun isCategoryContentValid(categoryEntity: CategoryEntity): Boolean  {
        if (categoryEntity.category.isEmpty() || categoryEntity.category.isBlank()) {
            throw InvalidInputException(R.string.error_category_empty)
        }

        return true;
    }
}