package com.example.moviespetproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.moviespetproject.model.FilmDatabase


private lateinit var navController: NavController



class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val navHostFragment:NavHostFragment =supportFragmentManager.findFragmentById(R.id.mainScreenFragment) as NavHostFragment
//        navController = navHostFragment.navController

        setContentView(R.layout.activity_main)
    }
}