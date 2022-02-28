package com.example.catalogoapp

import android.app.Application
import android.graphics.BitmapFactory
import androidx.core.util.AtomicFile
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.catalogoapp.utils.ImageResizer
import com.google.common.io.Resources.getResource
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File


@RunWith(
    value = AndroidJUnit4::class
)
class ImageResizerTest {

    private lateinit var context: Application

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Application>()
    }

    /*
    @Test
    fun imageResizer_is_compressing_image_right() {
        val fileInitial = File(("20190501_101051.jpg").path)
        val filePathInitial = fileInitial.absolutePath
        val bitmap = BitmapFactory.decodeFile(filePathInitial)
        val imageResized = ImageResizer.reduceBitmapSize(bitmap, 240_000)

        val fileAlreadyResized =
            File("com/example/catalogoapp","2022-41-24T02_41_12_oi.jpg")
        val bitMapAlreadyResized = BitmapFactory.decodeFile(fileAlreadyResized.absolutePath)
        Truth.assertThat(imageResized).isEqualTo(bitMapAlreadyResized)
    }

     */

}