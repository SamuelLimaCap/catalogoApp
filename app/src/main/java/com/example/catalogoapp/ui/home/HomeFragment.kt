package com.example.catalogoapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catalogoapp.R
import com.example.catalogoapp.data.db.AppDatabase
import com.example.catalogoapp.databinding.HomeFragmentBinding
import com.example.catalogoapp.repository.CatalogoRepository
import com.example.catalogoapp.ui.adapters.ProductsGroupByCategoryAdapter
import com.example.catalogoapp.ui.dbTransaction.DbTransactionActivity

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private val productsGroupByCategoryAdapter by lazy { ProductsGroupByCategoryAdapter() }

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(this.context, R.anim.rotate_open_anim)
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(this.context, R.anim.rotate_close_anim)
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(this.context, R.anim.from_bottom_anim)
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(this.context, R.anim.to_bottom_anim)
    }
    private var isFABbuttonActive: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        val homeVMFactory = HomeVMFactory(CatalogoRepository(AppDatabase(requireContext())))
        viewModel = ViewModelProvider(this, homeVMFactory)[HomeViewModel::class.java]

        init()
        setupRecyclerView()
        setupFABs()

        viewModel.listProductsGroupByCategory.observe(viewLifecycleOwner) {
            productsGroupByCategoryAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.getListProductsByCategory()
    }

    private fun init() {
        isFABbuttonActive = false;
        (requireActivity() as AppCompatActivity).supportActionBar?.title = getString(R.string.title_home)
    }


    private fun setupRecyclerView() {
        binding.rvListProduct.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productsGroupByCategoryAdapter
        }

    }

    private fun setupFABs() {
        binding.fabAdd.setOnClickListener {
            isFABbuttonActive = !isFABbuttonActive
            setSubFABsVisibility(isFABbuttonActive)
            setSubFABsAnimation(isFABbuttonActive)
        }

        binding.fabAddFood.setOnClickListener {
            val intent = Intent(this.context, DbTransactionActivity::class.java)
            intent.putExtra("type", "add")
            intent.putExtra("entity", "product")

            startActivity(intent)
        }
        binding.fabAddCategory.setOnClickListener {
            val intent = Intent(this.context, DbTransactionActivity::class.java)
            intent.putExtra("type", "add")
            intent.putExtra("entity", "category")

            startActivity(intent)
        }
    }

    private fun setSubFABsAnimation(isMainFABActive: Boolean) {
        if (isMainFABActive) {
            binding.fabAddFood.startAnimation(fromBottom)
            binding.fabAddCategory.startAnimation(fromBottom)
            binding.fabAdd.startAnimation(rotateOpen)
        } else {
            binding.fabAdd.startAnimation(rotateClose)
        }
    }

    private fun setSubFABsVisibility(isMainFABActive: Boolean) {
        if (isMainFABActive) {
            binding.fabAddCategory.show()
            binding.fabAddFood.show()
        } else {
            binding.fabAddFood.hide()
            binding.fabAddCategory.hide()
        }

    }


}