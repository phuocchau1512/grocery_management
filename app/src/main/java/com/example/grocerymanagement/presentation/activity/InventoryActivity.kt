package com.example.grocerymanagement.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.grocerymanagement.R
import com.example.grocerymanagement.databinding.ActivityInventoryBinding
import com.example.grocerymanagement.presentation.fragments.AddItemFragment
import com.example.grocerymanagement.presentation.fragments.ListItemFragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class InventoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInventoryBinding

    private val scanLauncher = registerForActivityResult(ScanContract()) { result ->
        result.contents?.let {
            AlertDialog.Builder(this)
                .setTitle("Kết quả")
                .setMessage(it)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        } ?: Toast.makeText(this, "Không có dữ liệu!", Toast.LENGTH_SHORT).show()
    }

    private val requestCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            scanCode()
        } else {
            Toast.makeText(this, "Cần cấp quyền camera!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInventoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            replaceFragment(ListItemFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_qr_scan -> {
                checkPermissionAndScan()
                true
            }
            R.id.action_add -> {
                val currentFragment = supportFragmentManager.findFragmentById(R.id.frameContainer)
                if (currentFragment is AddItemFragment) {
                    Toast.makeText(this, "Bạn đang ở phần thêm sản phẩm!", Toast.LENGTH_SHORT).show()
                } else {
                    replaceFragment(AddItemFragment())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkPermissionAndScan() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            scanCode()
        } else {
            requestCameraPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun scanCode() {
        val options = ScanOptions().apply {
            setPrompt("Đưa mã vào giữa màn hình")
            setBeepEnabled(true)
            setOrientationLocked(true)
            setCaptureActivity(CustomScannerActivity::class.java)
            setCameraId(0)
        }
        scanLauncher.launch(options)
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
