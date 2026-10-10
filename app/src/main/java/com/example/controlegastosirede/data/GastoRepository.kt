package com.example.controlegastosirede.data

import kotlinx.coroutines.flow.Flow

class GastoRepository(private val dao: GastoDao) {

    val todosGastos: Flow<List<Gasto>> = dao.listarTodos()

    fun buscarPorId(id: Int): Flow<Gasto?> = dao.buscarPorId(id)

    suspend fun inserir(gasto: Gasto) = dao.inserir(gasto)

    suspend fun atualizar(gasto: Gasto) = dao.atualizar(gasto)

    suspend fun excluir(gasto: Gasto) = dao.excluir(gasto)
}
