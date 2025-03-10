package com.example.balltron

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.balltron.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ActivityMain : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val homeFragment = FragmentHome()

    private val fragmentMap = mapOf(
        R.id.nav_bottom_home to homeFragment,
    )

    private lateinit var bnv : BottomNavigationView

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bnv = binding.mainBnv

        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrame.id, FragmentHome())
            .addToBackStack(null)
            .commitAllowingStateLoss()

        bnv.setOnItemSelectedListener { menuItem ->
             fragmentMap[menuItem.itemId]?.let { fragment ->
                 replaceFragment(fragment)
                 true
             } ?: false
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    showExitConfirmationDialog()
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFrame.id, fragment)
            .commitAllowingStateLoss()
    }

    private fun showExitConfirmationDialog() {
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            return
        }

        backPressedTime = System.currentTimeMillis()
        Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
    }

    fun hideBottomNavigation() {
        bnv.visibility = View.GONE
    }

    fun showBottomNavigation() {
        bnv.visibility = View.VISIBLE
    }
}