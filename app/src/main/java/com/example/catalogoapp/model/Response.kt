package com.example.catalogoapp.model

data class Response<T>(
    val isSuccess: T,
    val reason: String
)