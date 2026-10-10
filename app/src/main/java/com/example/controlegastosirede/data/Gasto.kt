package com.example.controlegastosirede.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gastos")
data class Gasto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val descricao: String,
    val valor: Double,
    val categoria: String,
    val data: Long = System.currentTimeMillis()
)