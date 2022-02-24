package com.example.catalogoapp.utils

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class ProductUtilTest {

    private lateinit var date: Date
    private lateinit var dateFormatted: String

    @Before
    fun setup() {
        date = Date()
        dateFormatted = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss", Locale.US).format(date)
    }

    @Test
    fun `createImageNamePlusOtherName should return blank on not allowed character in the end`() {

        val name = "${dateFormatted}_test"
        Assert.assertEquals(
            name,
            ProductUtil.createImageNamePlusOtherName("test,./\\><||:", date)
        )
    }

    @Test
    fun `createImageNamePlusOtherName should return blank on not allowed character in the middle`() {
        val name = "${dateFormatted}_test"
        Assert.assertEquals(
            name,
            ProductUtil.createImageNamePlusOtherName("te,./\\s><||:t", date)
        )
    }

    @Test
    fun `createImageNamePlusOtherName should return blank on not allowed character in the start`() {
        val name = "${dateFormatted}_test"
        Assert.assertEquals(
            name,
            ProductUtil.createImageNamePlusOtherName(",./\\><||:test", date)
        )
    }
}