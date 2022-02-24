package com.example.catalogoapp.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.view.View
import com.example.catalogoapp.model.InternalStoragePhoto
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI

object FilesUtil {

    fun createImagesDirectory(applicationContext: Context, directoryName: String) {
        val cw = ContextWrapper(applicationContext)
        cw.getDir(directoryName, Context.MODE_PRIVATE)

    }

    fun loadInternalStoragePhotos(filesDir: File): List<InternalStoragePhoto> {
        val listImages =
            filesDir.listFiles().find { it.isDirectory && it.name == "catalogoImages" }?.listFiles()
        return listImages?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") }
            ?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            }?.toList() ?: listOf()
    }

    fun saveBitmapToFile(file: File): File? {
        return try {

            // BitmapFactory options to downsize the image
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            o.inSampleSize = 6
            // factor of downsizing the image
            var inputStream = FileInputStream(file)
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o)
            inputStream.close()

            // The new size we want to scale to
            val REQUIRED_SIZE = 75

            // Find the correct scale value. It should be the power of 2.
            var scale = 1
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                o.outHeight / scale / 2 >= REQUIRED_SIZE
            ) {
                scale *= 2
            }
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            inputStream = FileInputStream(file)
            val selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2)
            inputStream.close()

            // here i override the original image file
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            selectedBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            file
        } catch (e: Exception) {
            null
        }
    }

    fun getImageBitmapFromCatalogoImages(imageName: String, applicationContext: Context): Bitmap {
        val cw = ContextWrapper(applicationContext)
        val directory = cw.getDir("catalogoImages", Context.MODE_PRIVATE)
        val filePath = File(directory, "$imageName.jpg")
        val bytes = filePath.readBytes()
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)


    }


    fun savePhotoToInternalStorage(
        applicationContext: Context,
        fileName: String,
        bmp: Bitmap
    ): String {
        val cw = ContextWrapper(applicationContext)
        val directory = cw.getDir("catalogoImages", Context.MODE_PRIVATE)
        val filePath = File(directory, "$fileName.jpg")

        var fos: FileOutputStream? = null
        fos = FileOutputStream(filePath)
        try {
            bmp.compress(Bitmap.CompressFormat.PNG,90, fos)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fos.close()
        }

        return directory.absolutePath;

    }

    fun exportAsPDF(context: Context, nameFile: String, view: View): URI {

        val file = File(context.filesDir, "$nameFile.pdf")
        val outputStream = FileOutputStream(file)

        val pdfDocument = PdfDocument()
        val pageInfo =
            PdfDocument.PageInfo.Builder(view.width, view.height, 1)
                .create()
        val page = pdfDocument.startPage(pageInfo)

        view.draw(page.canvas)

        pdfDocument.finishPage(page)
        pdfDocument.writeTo(outputStream)
        pdfDocument.close()

        return file.toURI()
    }

    fun writeFileExternalStorage(URI: URI, isPermissionGranted: Boolean): URI? {
        if (!isExternalStorageWritable()
            || !isPermissionGranted
        ) {
            return null
        }

        val file = File(Environment.getExternalStorageDirectory(), "exportedPdf.pdf")
        var outputStream: FileOutputStream? = null
        outputStream = FileOutputStream(file, true)

        try {
            file.createNewFile()
            outputStream.write(File(URI).readBytes())
        } catch (e: IOException) {
            return null
        } finally {
            outputStream.close()
        }

        return file.toURI()
    }

    private fun isExternalStorageWritable(): Boolean {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            return true
        }
        return false
    }

}