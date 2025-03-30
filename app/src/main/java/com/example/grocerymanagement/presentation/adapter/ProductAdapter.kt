package com.example.grocerymanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grocerymanagement.R
import com.example.grocerymanagement.domain.model.Product
import com.example.grocerymanagement.data.source.retrofit.RetrofitClient
import com.example.grocerymanagement.databinding.ItemProductBinding

class ProductAdapter(
    private var productList: List<Product>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.txtProductName.text = product.name
        holder.binding.txtBarcode.text = product.barcode
        holder.binding.txtDescription.text = product.description
        holder.binding.txtQuantity.text = product.quantity.toString()

        Glide.with(holder.itemView.context)
            .load(RetrofitClient.getBaseUrl() + product.img) // URL ảnh
            .placeholder(R.drawable.baseline_image_24) // Ảnh hiển thị khi tải
            .error(R.drawable.baseline_image_24) // Ảnh hiển thị khi lỗi
            .into(holder.binding.imgProduct) // Đặt vào ImageView

        // Bắt sự kiện click vào item
        holder.itemView.setOnClickListener {
            listener.onItemClick(productList[holder.adapterPosition]) 
        }
    }

    override fun getItemCount(): Int = productList.size

    fun updateData(newList: List<Product>) {
        val diffCallback = ProductDiffCallback(productList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        productList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(position: Int) {
        val mutableList = productList.toMutableList() // Chuyển sang MutableList để có thể xóa
        mutableList.removeAt(position)
        productList = mutableList
        notifyItemRemoved(position)
    }

    fun getProductAt(position: Int): Product {
        return productList[position]
    }
}

class ProductDiffCallback(
    private val oldList: List<Product>,
    private val newList: List<Product>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.name == newItem.name &&
                oldItem.barcode == newItem.barcode &&
                oldItem.img == newItem.img &&
                oldItem.description == newItem.description &&
                oldItem.quantity == newItem.quantity
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

interface OnItemClickListener {
    fun onItemClick(product: Product)
}


