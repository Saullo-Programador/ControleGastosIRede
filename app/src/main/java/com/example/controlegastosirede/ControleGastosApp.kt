package com.example.controlegastosirede

import android.app.Application
import com.example.controlegastosirede.data.AppDatabase
import com.example.controlegastosirede.data.GastoRepository

class ControleGastosApp : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { GastoRepository(database.gastoDao()) }
}