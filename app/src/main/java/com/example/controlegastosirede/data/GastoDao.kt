package com.example.controlegastosirede.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GastoDao {

    @Query("SELECT * FROM gastos ORDER BY data DESC")
    fun listarTodos(): Flow<List<Gasto>>

    @Query("SELECT * FROM gastos WHERE id = :id")
    fun buscarPorId(id: Int): Flow<Gasto?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(gasto: Gasto)

    @Update
    suspend fun atualizar(gasto: Gasto)

    @Delete
    suspend fun excluir(gasto: Gasto)
}