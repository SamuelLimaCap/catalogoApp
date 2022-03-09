package com.example.catalogoapp.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.example.catalogoapp.R
import com.example.catalogoapp.model.ProductEntity
import com.example.catalogoapp.model.ProductsGroupByCategory
import java.lang.IllegalArgumentException

object ProductsGroupByCategoryUtils {

    fun transformListIntoExportList(productsGroupCategory: ProductsGroupByCategory): List<ProductsGroupByCategory> {
        val newList = mutableListOf<ProductsGroupByCategory>()
        val temporaryList: MutableList<ProductEntity> = mutableListOf()
        var count = 0
        val categoryName = productsGroupCategory.category
        val listProductsGroupByCategory: MutableList<ProductsGroupByCategory> = mutableListOf()

        for (product in productsGroupCategory.productList) {
            when (count) {
                0 -> {
                    temporaryList.clear()
                    temporaryList.add(product)
                    count++
                }
                in (1..3) -> {
                    temporaryList.add(product)
                    count++
                }
                4 -> {
                    count = 0
                    listProductsGroupByCategory.add(ProductsGroupByCategory(categoryName,
                        temporaryList.toMutableList()))
                    temporaryList.clear()
                    temporaryList.add(product)
                    count = 1;
                }
            }
        }

        if (temporaryList.isNotEmpty()) {
            listProductsGroupByCategory.add(ProductsGroupByCategory(categoryName, temporaryList))
        }

        return listProductsGroupByCategory.toList()
    }

    @ColorInt
    fun getColorFromStringResource(color: String, context: Context): Int {
        val arrayColor = context.resources.getStringArray(R.array.spinner_colors)
        val arrayColorValues = context.resources.getStringArray(R.array.spinner_colors_values)

        val indexColor = arrayColor.indexOf(color)
        if (indexColor == -1) throw IllegalArgumentException("color \"$color\" doesn't exists on arrayColor")

        val colorName = arrayColorValues[indexColor]
        val colorId = context.resources.getIdentifier(colorName, "color", context.packageName)

        return context.resources.getColor(colorId)

    }
    fun getBgImageFromColor(color: Int, context: Context): Drawable? {
        val res = context.resources
        return when (color) {
            res.getColor(R.color.category_pink) -> res.getDrawable(R.drawable.pink_image)
            res.getColor(R.color.category_dark_blue) -> res.getDrawable(R.drawable.dark_blue)
            res.getColor(R.color.category_dark_orange) -> res.getDrawable(R.drawable.dark_orange)
            res.getColor(R.color.category_light_purple) -> res.getDrawable(R.drawable.brown_image)
            res.getColor(R.color.category_light_blue) -> res.getDrawable(R.drawable.light_blue)
            else -> res.getDrawable(R.drawable.brown_image)
        }
    }
}