package com.example.catalogoapp.utils

import android.graphics.BitmapFactory
import com.google.common.truth.Truth
import org.junit.Test
import java.io.File

class ImageResizerTest {

    @Test
    fun `ImageReziser is compressing image right`() {
        val fileInitial = File("src/test/java/com/example/catalogoapp/20190501_101051.jpg")
        val filePathInitial = fileInitial.path
        val bitmap = BitmapFactory.decodeFile(filePathInitial)
        val imageRezised = ImageResizer.reduceBitmapSize(bitmap, 240_000)

        val fileAlreadyResized =
            File("src/test/java/com/example/catalogoapp/2022-41-24T02_41_12_oi.jpg")
        val bitMapAlreadyRezised = BitmapFactory.decodeFile(fileAlreadyResized.path)
        Truth.assertThat(imageRezised).isEqualTo(bitMapAlreadyRezised)


    }

}