package com.example.catalogoapp.ui.dbTransaction

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.databinding.ActivityDbTransactionBinding
import com.example.catalogoapp.repository.CatalogoRepository
import com.example.catalogoapp.ui.dbTransaction.fragments.EmptyFragmentDirections

class DbTransactionActivity : AppCompatActivity() {

    private lateinit var viewModel: DbTransactionViewModel
    lateinit var binding: ActivityDbTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory =
            DbTransactionVMProviderFactory(CatalogoRepository(AppDatabase(this)))
        viewModel =
            ViewModelProvider(this, viewModelFactory)[DbTransactionViewModel::class.java]



        val type = intent.extras?.getString("type") ?: ""
        val entity = intent.extras?.getString("entity") ?: ""
        val id = intent.extras?.getString("id") ?: "-1L"


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.db_transaction_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        when (type) {
            "add" -> {
                when (entity) {
                    "product" -> navController.navigate(R.id.action_emptyFragment_to_productTransactionFragment)
                    "category" -> navController.navigate(R.id.action_emptyFragment_to_addCategoryFragment)
                }
            }
            "edit" -> {
                when (entity) {
                    "product" -> {
                        Log.e("DbTransactionActivity", "chegou")
                        EmptyFragmentDirections.actionEmptyFragmentToProductTransactionFragment("edit", id.toLong())
                    }
                }
            }
        }



    }
}