package com.example.catalogoapp.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.ActivityAddCategoryBinding

class AddCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar(R.string.title_add_category)
    }

    private fun setActionBar(titleResource: Int) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(titleResource)
        }
    }
}