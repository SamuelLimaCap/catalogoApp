package com.example.catalogoapp.model.exception

//Throws a resource ID
class InvalidInputException(val resourceIdMessage: Int) : Exception() {}