package com.example.controlegastosirede

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.controlegastosirede.ui.navigation.AppNavigation
import com.example.controlegastosirede.viewmodel.GastoViewModel
import com.example.controlegastosirede.viewmodel.GastoViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: GastoViewModel by viewModels {
        GastoViewModelFactory((application as ControleGastosApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme{
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation(viewModel)
                }
            }
        }
    }
}
