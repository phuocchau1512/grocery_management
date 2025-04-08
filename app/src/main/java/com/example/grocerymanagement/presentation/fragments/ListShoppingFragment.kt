package com.example.grocerymanagement.presentation.fragments

import androidx.fragment.app.Fragment


class ListShoppingFragment : Fragment() {



    /*private var _binding: FragmentListShoppingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InventoryViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[InventoryViewModel::class.java]
        adapter = ProductAdapter(emptyList(), this)

        settingSwipeDelete()

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(ItemSpacingDecoration(24))

        viewModel.product.observe(viewLifecycleOwner) { productList ->
            binding.progressBar.visibility = View.GONE
            if (productList.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.GONE
            }
            adapter.updateData(productList)
        }

        viewModel.getProductInInvent()
    }

    private fun settingSwipeDelete() {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val productToDelete = adapter.getProductAt(position)

                AlertDialog.Builder(requireContext())
                    .setTitle("Xác nhận xóa")
                    .setMessage("Bạn có chắc muốn xóa sản phẩm này?")
                    .setPositiveButton("Xóa") { _, _ ->
                        viewModel.deleteProductInInvent(productToDelete.id.toString())
                        viewModel.getProductInInvent() // Cập nhật lại danh sách
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("Hủy") { dialog, _ ->
                        dialog.dismiss()
                        adapter.notifyDataSetChanged() // Khôi phục lại hiển thị
                    }
                    .show()
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    fun onItemClick(product: Product) {
        val fragment = if (product.is_private == 0) {
            EditItemPublicFragment()
        } else {
            EditItemFragment()
        }

        fragment.arguments = Bundle().apply {
            putParcelable("selected_product", product)
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/

}