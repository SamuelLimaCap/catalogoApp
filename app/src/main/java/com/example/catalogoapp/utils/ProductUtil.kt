package com.example.catalogoapp.utils

import java.text.SimpleDateFormat
import java.util.*

object ProductUtil {

    fun createImageNamePlusOtherName(otherName: String, date:Date = Date()): String {
        val now = SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ss", Locale.US).format(date)
        val notAllowedChars = arrayOf(",",".","/","\\",">","<","|",":","&" )
        var otherNameReassigned = otherName
        notAllowedChars.forEach { otherNameReassigned = otherNameReassigned.replace(it, "")}
        return "${now}_${otherNameReassigned}"
    }
}