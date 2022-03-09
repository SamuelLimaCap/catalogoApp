package com.example.catalogoapp.model

data class ProductTastes(
    var id: Long,
    val taste: String?,
    val price: Double?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductTastes

        if (id != other.id) return false
        if (taste != other.taste) return false
        if (price != other.price) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + taste.hashCode()
        result = 31 * result + price.hashCode()
        return result
    }
}