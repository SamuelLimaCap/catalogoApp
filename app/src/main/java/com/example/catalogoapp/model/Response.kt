package com.example.catalogoapp.model

data class Response<T>(
    val type: T,
    val reason: String
)