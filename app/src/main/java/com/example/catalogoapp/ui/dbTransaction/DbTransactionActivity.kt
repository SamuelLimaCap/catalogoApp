package com.example.catalogoapp.ui.dbTransaction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.databinding.ActivityDbTransactionBinding
import com.example.catalogoapp.repository.CatalogoRepository
import com.example.catalogoapp.ui.dbTransaction.fragments.EmptyFragmentDirections

//intent arguments are on EmptyFragment
class DbTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDbTransactionBinding
    private lateinit var viewModel: DbTransactionViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory =
            DbTransactionVMProviderFactory(CatalogoRepository(AppDatabase(this)))
        viewModel = ViewModelProvider(this, viewModelFactory)[DbTransactionViewModel::class.java]

        binding = ActivityDbTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }


}