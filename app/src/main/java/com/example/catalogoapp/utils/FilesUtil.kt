package com.example.catalogoapp.utils

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogoapp.model.InternalStoragePhoto
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FilesUtil {

    companion object {
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
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                fos.close()
            }

            return directory.absolutePath;

        }
    }
}