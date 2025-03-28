package com.example.grocerymanagement.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerymanagement.data.model.Product
import com.example.grocerymanagement.databinding.FragmentListItemBinding
import com.example.grocerymanagement.presentation.adapter.OnItemClickListener
import com.example.grocerymanagement.presentation.adapter.ProductAdapter
import com.example.grocerymanagement.presentation.viewModel.InventoryViewModel


class ListItemFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InventoryViewModel  // Khai báo ViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[InventoryViewModel::class.java]
        // Khởi tạo Adapter
        adapter = ProductAdapter(emptyList(),this)

        settingSwipeDelete()

        // Cấu hình RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Quan sát dữ liệu từ ViewModel
        viewModel.product.observe(viewLifecycleOwner) { productList ->
            binding.progressBar.visibility = View.GONE
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

    private fun settingSwipeDelete(){

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val productToDelete = adapter.getProductAt(position) // Lấy sản phẩm cần xóa

                AlertDialog.Builder(requireContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa sản phẩm này?")
                    .setPositiveButton("Xóa") { _, _ ->
                        viewModel.deleteProductInInvent(productToDelete.id.toString()) // Gọi API xóa
                    }
                    .setNegativeButton("Hủy") { dialog, _ ->
                        dialog.dismiss()
                        adapter.notifyItemChanged(position) // Khôi phục item nếu hủy
                    }
                    .show()

            }
        })

        // Gắn vào RecyclerView
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    override fun onItemClick(product: Product) {
        TODO("Not yet implemented")
    }


}