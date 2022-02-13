package com.myprojects.testappjoke

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (WebFragment.getInstance() != getVisibleFragment()) {
            loadFragment(JokesFragment.getInstance())
        }
        val bar: NavigationBarView = findViewById(R.id.bottom_navigation)
        bar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.page_joke -> {
                    loadFragment(JokesFragment.getInstance())
                }
                R.id.page_web -> {
                    loadFragment(WebFragment.getInstance())
                }
            }
            false
        }
    }

    private fun loadFragment(fragment: Fragment?) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_content, fragment!!)
        ft.commit()
    }

    override fun onBackPressed() {
        val fragment: Fragment? = getVisibleFragment()
        val backPressedListener: IOnBackPressed?
        if (fragment is IOnBackPressed) {
            backPressedListener = fragment
            backPressedListener.onBackPressed()
        } else {
            super.onBackPressed()
        }
    }

    //get Current fragment
    @SuppressLint("RestrictedApi")
    fun getVisibleFragment(): Fragment? {
        val fragmentManager: FragmentManager = this@MainActivity.supportFragmentManager
        for (fragment in fragmentManager.getFragments()) {
            if (fragment.isMenuVisible()) return fragment
        }
        return null
    }
}