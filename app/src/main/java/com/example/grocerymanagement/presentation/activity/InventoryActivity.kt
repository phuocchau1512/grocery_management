package com.example.grocerymanagement.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.grocerymanagement.R
import com.example.grocerymanagement.databinding.ActivityInventoryBinding

class InventoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Khởi tạo ViewBinding
        binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Thiết lập Toolbar
        setSupportActionBar(binding.appBar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Hiện nút back
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24) // Icon back tùy chỉnh

        // Xử lý sự kiện khi bấm nút back
        binding.appBar.toolbar.setNavigationOnClickListener {
            finish() // Đóng Activity khi bấm back
        }

    }

    // Thêm icon vào Toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_list, menu)
        return true
    }

    // Xử lý sự kiện khi bấm icon
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_qr_scan -> {
                val intent = Intent(this, QRScannerActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}