package com.example.eterationcase.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.eterationcase.R
import com.example.eterationcase.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bottom nav

        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHost

        val navController = navHost.navController

        binding.bottomNavigation.setupWithNavController(navController)


        }
    }
