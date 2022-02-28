package com.example.catalogoapp.utils

import android.graphics.pdf.PdfDocument
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

class ExportToPdf(val file: File) {

    private val pdfDocument: PdfDocument = PdfDocument()
    var currentPage: Int = 1
        private set
    private var isAlreadyClosed = false;

    init {
        clearFile()
    }
    fun drawOnPdf(view: View) {
        val pageInfo =
            PdfDocument.PageInfo.Builder(view.width, view.height, currentPage)
                .create()
        val page = pdfDocument.startPage(pageInfo)
        view.draw(page.canvas)
        pdfDocument.finishPage(page)

        currentPage++;
    }

    fun writeAndClosePDF(): Boolean {
        if (isAlreadyClosed) return false


        val outputStream = FileOutputStream(file)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()
        isAlreadyClosed = true
        return true
    }

    private fun clearFile() {
        if (file.exists()) PrintWriter(file).close()
    }

}