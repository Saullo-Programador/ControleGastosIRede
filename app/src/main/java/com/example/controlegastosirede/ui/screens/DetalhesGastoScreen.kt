package com.example.controlegastosirede.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlegastosirede.data.Gasto
import com.example.controlegastosirede.ui.theme.ControleGastosIRedeTheme
import com.example.controlegastosirede.viewmodel.GastoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetalhesGastoScreen(
    gastoId: Int,
    viewModel: GastoViewModel,
    onVoltar: () -> Unit,
    onEditar: () -> Unit
) {
    val gastoFlow = remember(gastoId) { viewModel.buscarPorId(gastoId) }
    val gasto by gastoFlow.collectAsState(initial = null)

    DetalhesGastoContent(
        gasto = gasto,
        onVoltar = onVoltar,
        onEditar = onEditar,
        onExcluir = { item ->
            viewModel.excluir(item)
            onVoltar()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesGastoContent(
    gasto: Gasto? = null,
    onVoltar: () -> Unit = {},
    onEditar: () -> Unit = {},
    onExcluir: (Gasto) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do gasto") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (gasto == null) {
                Text("Gasto não encontrado.")
            } else {
                Text(gasto.descricao, style = MaterialTheme.typography.headlineSmall)
                Text(formatarMoeda(gasto.valor), style = MaterialTheme.typography.headlineMedium)
                Text("Categoria: ${gasto.categoria}", style = MaterialTheme.typography.bodyLarge)
                Text(
                    "Registrado em: " + SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))
                        .format(Date(gasto.data)),
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(onClick = onEditar, modifier = Modifier.weight(1f)) {
                        Text("Editar")
                    }
                    OutlinedButton(
                        onClick = { onExcluir(gasto) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Excluir")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Detalhes do Gasto")
@Composable
fun PreviewDetalhesGastoScreen() {
    val sampleGasto = Gasto(
        id = 1,
        descricao = "Supermercado",
        valor = 350.75,
        categoria = "Alimentação",
        data = System.currentTimeMillis()
    )
    ControleGastosIRedeTheme {
        DetalhesGastoContent(
            gasto = sampleGasto,
            onVoltar = {},
            onEditar = {},
            onExcluir = {}
        )
    }
}

@Preview(showBackground = true, name = "Gasto Não Encontrado")
@Composable
fun PreviewDetalhesGastoNaoEncontrado() {
    ControleGastosIRedeTheme {
        DetalhesGastoContent(
            gasto = null,
            onVoltar = {},
            onEditar = {},
            onExcluir = {}
        )
    }
}
