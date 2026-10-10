package com.example.controlegastosirede.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlegastosirede.data.Gasto
import com.example.controlegastosirede.ui.theme.ControleGastosIRedeTheme
import com.example.controlegastosirede.viewmodel.GastoViewModel
import java.text.NumberFormat
import java.util.Locale

fun formatarMoeda(valor: Double): String =
    NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(valor)

@Composable
fun ListaGastosScreen(
    viewModel: GastoViewModel,
    onAdicionar: () -> Unit,
    onAbrirDetalhes: (Int) -> Unit
) {
    val gastos by viewModel.gastos.collectAsState()
    val total by viewModel.total.collectAsState()

    ListaGastosContent(
        gastos = gastos,
        total = total,
        onAdicionar = onAdicionar,
        onAbrirDetalhes = onAbrirDetalhes
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaGastosContent(
    gastos: List<Gasto>,
    total: Double,
    onAdicionar: () -> Unit,
    onAbrirDetalhes: (Int) -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Controle de Gastos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdicionar) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar gasto")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Total gasto", style = MaterialTheme.typography.labelLarge)
                        Text(
                            formatarMoeda(total),
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                if (gastos.isEmpty()) {
                    Text(
                        "Nenhum gasto cadastrado ainda.\nToque no + para adicionar.",
                        modifier = Modifier.padding(top = 32.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(gastos, key = { it.id }) { gasto ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onAbrirDetalhes(gasto.id) }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(gasto.descricao, style = MaterialTheme.typography.titleMedium)
                                        Text(gasto.categoria, style = MaterialTheme.typography.bodySmall)
                                    }
                                    Text(
                                        formatarMoeda(gasto.valor),
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, name = "Com Gastos")
@Composable
fun PreviewListaGastosScreen() {
    val sampleGastos = listOf(
        Gasto(id = 1, descricao = "Mercado", valor = 250.50, categoria = "Alimentação"),
        Gasto(id = 2, descricao = "Combustível", valor = 150.00, categoria = "Transporte"),
        Gasto(id = 3, descricao = "Cinema", valor = 45.00, categoria = "Lazer")
    )
    ControleGastosIRedeTheme {
        ListaGastosContent(
            gastos = sampleGastos,
            total = sampleGastos.sumOf { it.valor },
            onAdicionar = {},
            onAbrirDetalhes = {}
        )
    }
}

@Preview(showBackground = true, name = "Lista Vazia")
@Composable
fun PreviewListaGastosScreenVazia() {
    ControleGastosIRedeTheme {
        ListaGastosContent(
            gastos = emptyList(),
            total = 0.0,
            onAdicionar = {},
            onAbrirDetalhes = {}
        )
    }
}
