package com.example.spsassignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.spsassignment.navigation.NavigationGraph
import com.example.spsassignment.ui.theme.SPSAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SPSAssignmentTheme {
                val navController = rememberNavController()
                Scaffold(
                    content = { paddingValues ->
                        Surface(modifier = Modifier.padding(paddingValues)) {
                            NavigationGraph(navController = navController)
                        }
                    })
            }
        }
    }
}