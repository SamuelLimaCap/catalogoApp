package com.example.catalogoapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catalogoapp.R
import com.example.catalogoapp.databinding.HomeFragmentBinding
import com.example.catalogoapp.ui.adapters.ProductListAdapter
import com.example.catalogoapp.ui.dbTransaction.DbTransactionActivity

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private val productListAdapter by lazy { ProductListAdapter() }

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.from_bottom_anim) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this.context, R.anim.to_bottom_anim) }
    private var isFABbuttonActive: Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        init()
        setActionBar(R.string.title_home)
        setupRecyclerView()
        setupFABs()
        return binding.root
    }

    private fun init() {
        isFABbuttonActive = false;
    }

    private fun setActionBar(titleResource: Int) {
        activity?.actionBar?.apply {
            title = resources.getText(titleResource)
            show()
        }
    }

    private fun setupRecyclerView() {
        binding.rvListProduct.apply {
            layoutManager = GridLayoutManager(this.context,2)
            adapter = productListAdapter
        }

    }

    private fun setupFABs() {
        binding.fabAdd.setOnClickListener {
            isFABbuttonActive = !isFABbuttonActive
            setSubFABsVisibility(isFABbuttonActive)
            setSubFABsAnimation(isFABbuttonActive)
        }

        binding.fabAddFood.setOnClickListener {
            //TODO start activity with intent and extras
            val intent = Intent(this.context, DbTransactionActivity::class.java)
            intent.putExtra("type","add")
            intent.putExtra("entity", "product")

            startActivity(intent)
        }
        binding.fabAddCategory.setOnClickListener {
            val intent = Intent(this.context, DbTransactionActivity::class.java)
            intent.putExtra("type","add")
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
    private fun setSubFABsVisibility(isMainFABActive : Boolean) {
        if (isMainFABActive) {
            binding.fabAddFood.show()
            binding.fabAddCategory.show()
        } else {
            binding.fabAddFood.hide()
            binding.fabAddCategory.hide()

        }

    }



}