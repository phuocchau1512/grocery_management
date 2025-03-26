package com.example.grocerymanagement.presentation.fragments

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerymanagement.R
import com.example.grocerymanagement.databinding.FragmentEditItemBinding
import com.example.grocerymanagement.databinding.FragmentListItemBinding
import com.example.grocerymanagement.domain.model.MenuItem
import com.example.grocerymanagement.presentation.activity.InventoryActivity
import com.example.grocerymanagement.presentation.adapter.MenuAdapter
import com.example.grocerymanagement.presentation.adapter.ProductAdapter
import com.example.grocerymanagement.presentation.viewModel.EditProductViewModel
import com.example.grocerymanagement.presentation.viewModel.InventoryViewModel


class ListItemFragment : Fragment() {

    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InventoryViewModel  // Khai báo ViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[InventoryViewModel::class.java]
        // Khởi tạo Adapter
        adapter = ProductAdapter(emptyList())

        // Cấu hình RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Quan sát dữ liệu từ ViewModel
        viewModel.product.observe(viewLifecycleOwner) { productList ->
            if ( productList.isNullOrEmpty() ){
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            }
            else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
                adapter.updateData(productList)
            }
        }

        // Gọi hàm để lấy dữ liệu sản phẩm
        viewModel.getProductInInvent()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}