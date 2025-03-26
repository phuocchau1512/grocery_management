package com.example.grocerymanagement.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.grocerymanagement.R
import com.example.grocerymanagement.databinding.FragmentEditItemBinding
import com.example.grocerymanagement.databinding.FragmentListItemBinding
import com.example.grocerymanagement.presentation.viewModel.EditProductViewModel
import com.example.grocerymanagement.presentation.viewModel.InventoryViewModel


class ListItemFragment : Fragment() {

    private var _binding: FragmentListItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: InventoryViewModel  // Khai báo ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_item, container, false)
    }


}