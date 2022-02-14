package com.example.catalogoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catalogoapp.data.db.dao.model.ProductEntity
import com.example.catalogoapp.repository.CatalogoRepository

class MainViewModel(
    val repository: CatalogoRepository
) : ViewModel() {

    private val productList: MutableLiveData<ProductEntity> = MutableLiveData()

}