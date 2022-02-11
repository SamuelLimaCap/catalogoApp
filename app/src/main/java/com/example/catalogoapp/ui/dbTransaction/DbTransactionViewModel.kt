package com.example.catalogoapp.ui.dbTransaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalogoapp.data.db.ProductEntity
import com.example.catalogoapp.repository.CatalogoRepository
import kotlinx.coroutines.launch

class DbTransactionViewModel(
    private val repository: CatalogoRepository
): ViewModel() {

    val isTransactionDone: MutableLiveData<Boolean> = MutableLiveData()

    fun addProductToDB(productEntity: ProductEntity) {
        viewModelScope.launch {
            repository.insertProduct(productEntity)
        }
    }
}