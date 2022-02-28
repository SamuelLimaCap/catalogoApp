package com.example.catalogoapp.data.db.dao.model

import androidx.room.ColumnInfo

data class CategoryCountProduct(
    @ColumnInfo(name="categoryName") val categoryName: String,
    @ColumnInfo(name="countProduct") val countProduct: Int
)
