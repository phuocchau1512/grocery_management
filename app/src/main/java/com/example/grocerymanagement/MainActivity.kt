package com.example.grocerymanagement

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.grocerymanagement.databinding.ActivityMainBinding
import com.example.grocerymanagement.presentation.fragments.HomeFragment
import com.example.grocerymanagement.presentation.fragments.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Khởi tạo ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingNavigationView()

        // Hiển thị HomeFragment mặc định nếu chưa có
        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }
    }

    // Hàm thay thế fragment trong FrameLayout
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .commit()
    }

    private fun settingNavigationView(){
        // Thiết lập Toolbar
        setSupportActionBar(binding.appBarMain.toolbar)

        // Cấu hình DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBarMain.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Bắt sự kiện bấm vào NavigationView
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> showToast("Trang chủ")
                R.id.nav_logout -> clearUserData()
            }
            binding.drawerLayout.closeDrawers() // Đóng menu sau khi chọn
            true
        }

    }

    private fun showToast(s: String) {

    }

    private  fun clearUserData() {
        val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }

}