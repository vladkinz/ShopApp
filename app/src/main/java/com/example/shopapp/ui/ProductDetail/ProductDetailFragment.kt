package com.example.shopapp.ui.product_ditails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil3.load
import com.example.shopapp.databinding.FragmentProductDetailBinding
import com.example.shopapp.data.api.RetrofitService

import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productId = arguments?.getInt("PRODUCT_ID") ?: -1

        if (productId != -1) {
            loadProductInfo(productId)
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadProductInfo(id: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val product = RetrofitService.api.getProductById(id)

                with(binding) {
                    tvTitle.text = product.title
                    tvPrice.text = "${product.price} $"
                    tvDescription.text = product.description

                    imgProduct.load(product.image)
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}