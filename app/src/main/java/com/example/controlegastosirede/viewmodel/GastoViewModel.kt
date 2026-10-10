package com.example.controlegastosirede.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.controlegastosirede.data.Gasto
import com.example.controlegastosirede.data.GastoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GastoViewModel(private val repository: GastoRepository) : ViewModel() {

    val gastos: StateFlow<List<Gasto>> = repository.todosGastos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val total: StateFlow<Double> = repository.todosGastos
        .map { lista -> lista.sumOf { it.valor } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    fun buscarPorId(id: Int): Flow<Gasto?> = repository.buscarPorId(id)

    fun salvar(id: Int, descricao: String, valor: Double, categoria: String) {
        viewModelScope.launch {
            if (id == 0) {
                repository.inserir(Gasto(descricao = descricao, valor = valor, categoria = categoria))
            } else {
                repository.atualizar(
                    Gasto(id = id, descricao = descricao, valor = valor, categoria = categoria)
                )
            }
        }
    }

    fun excluir(gasto: Gasto) {
        viewModelScope.launch { repository.excluir(gasto) }
    }
}

class GastoViewModelFactory(private val repository: GastoRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GastoViewModel::class.java)) {
            return GastoViewModel(repository) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}
