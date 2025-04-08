package com.example.grocerymanagement.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.grocerymanagement.R
import com.example.grocerymanagement.databinding.ActivityInventoryBinding
import com.example.grocerymanagement.databinding.ActivityShoppingBinding
import com.example.grocerymanagement.presentation.fragments.ListItemFragment
import com.example.grocerymanagement.presentation.fragments.ListShoppingFragment
import com.example.grocerymanagement.presentation.fragments.addFragment.AddItemFragment
import com.example.grocerymanagement.presentation.fragments.addFragment.AddShoppingListFragment
import com.example.grocerymanagement.presentation.viewModel.InventoryViewModel
import com.example.grocerymanagement.presentation.viewModel.ShoppingViewModel

class ShoppingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingBinding
    private lateinit var viewModel: ShoppingViewModel  // Khai báo ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShoppingViewModel::class.java]

        setSupportActionBar(binding.appBar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

        // Xử lý nút Back trên Toolbar
        binding.appBar.toolbar.setNavigationOnClickListener { handleBackPress() }

        // Xử lý nút Back trên điện thoại
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPress()
            }
        })

        // Hiển thị ListItemFragment ban đầu
        if (savedInstanceState == null) {
            replaceFragment(ListShoppingFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_shopping, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frameContainer)
                if (currentFragment is AddShoppingListFragment) {
                    Toast.makeText(this, "Bạn đang ở phần thêm sản phẩm!", Toast.LENGTH_SHORT).show()
                } else {
                    replaceFragment(AddShoppingListFragment())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Xử lý khi bấm Back hoặc nút trên Toolbar
    private fun handleBackPress() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }

    // Hàm chuyển đổi Fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .addToBackStack(null)
            .commit()
    }


}