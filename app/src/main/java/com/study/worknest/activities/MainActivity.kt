package com.study.worknest.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.study.worknest.API.APIService
import com.study.worknest.R
import com.study.worknest.fragments.HomeFragment
import com.study.worknest.fragments.ProfileFragment
import com.study.worknest.fragments.ProjectsFragment
import com.study.worknest.fragments.TeamsFragment
import com.study.worknest.fragments.dialogs.CreateElementDialogFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = APIService.getInstance(this)?.getCookieJar()?.getTokenFromCookies()
        if (token.isNullOrEmpty()) {
            Toast.makeText(this, "Please log in again", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            loadFragment(HomeFragment())
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_projects -> {
                    loadFragment(ProjectsFragment())
                    true
                }
                R.id.nav_teams -> {
                    loadFragment(TeamsFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.nav_create -> {
                    val dialog = CreateElementDialogFragment.newInstance()
                    dialog.show(supportFragmentManager, "CreateElementDialog")
                    false
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}