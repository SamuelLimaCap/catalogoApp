package com.example.catalogoapp.ui.add

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.ActivityAddProductBinding

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fillGroupSpinner()
        setActionBar(R.string.title_add_product)
    }

    private fun setActionBar(titleResource: Int) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(titleResource)
        }
    }

    private fun fillGroupSpinner() {

        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_group_options,
            android.R.layout.simple_spinner_item
        ).also {
                arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.groupOptionsSpinner.adapter = arrayAdapter
        }
    }
}