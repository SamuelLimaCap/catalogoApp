package com.example.catalogoapp.ui.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.databinding.CategoryListFragmentBinding
import com.example.catalogoapp.repository.CatalogoRepository
import com.example.catalogoapp.ui.adapters.CategoryListAdapter

class CategoryListFragment : Fragment() {


    private lateinit var viewModel: CategoryListViewModel
    lateinit var binding: CategoryListFragmentBinding
    private val adapter = CategoryListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModelFactory = CategoryListVMProviderFactory(CatalogoRepository(AppDatabase(requireContext())))
        viewModel = ViewModelProvider(this, viewModelFactory).get(CategoryListViewModel::class.java)
        binding = CategoryListFragmentBinding.inflate(inflater, container, false)
        setupRecyclerView()

        viewModel.categoryList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.getAllCategories()
    }

    private fun setupRecyclerView() {
        binding.rvListCategory.adapter = adapter
        binding.rvListCategory.layoutManager = LinearLayoutManager(context)
    }

}