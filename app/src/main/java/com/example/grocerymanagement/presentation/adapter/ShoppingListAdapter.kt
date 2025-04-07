package com.example.grocerymanagement.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerymanagement.databinding.ItemShoppingListBinding
import com.example.grocerymanagement.presentation.adapter.adapterItem.ShoppingList

class ShoppingListAdapter(
    private var list: List<ShoppingList>,
    private val listener: OnShoppingListClickListener
) : RecyclerView.Adapter<ShoppingListAdapter.ShoppingListViewHolder>() {

    class ShoppingListViewHolder(val binding: ItemShoppingListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val binding = ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        val shoppingList = list[position]
        holder.binding.txtListName.text = shoppingList.name
        holder.binding.txtDateCreated.text = "Ngày tạo: ${shoppingList.dateCreated}"
        holder.binding.txtItemCount.text = "Số mặt hàng: ${shoppingList.itemCount}"

        // Click item
        holder.itemView.setOnClickListener {
            listener.onListClick(list[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<ShoppingList>) {
        val diffCallback = ShoppingListDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getListAt(position: Int): ShoppingList = list[position]
}

class ShoppingListDiffCallback(
    private val oldList: List<ShoppingList>,
    private val newList: List<ShoppingList>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos].id == newList[newPos].id
    }

    override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
        return oldList[oldPos] == newList[newPos]
    }
}

interface OnShoppingListClickListener {
    fun onListClick(list: ShoppingList)
}
