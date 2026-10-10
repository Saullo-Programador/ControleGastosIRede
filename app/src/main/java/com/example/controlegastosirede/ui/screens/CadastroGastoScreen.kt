package com.example.controlegastosirede.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlegastosirede.ui.theme.ControleGastosIRedeTheme
import com.example.controlegastosirede.viewmodel.GastoViewModel
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun CadastroGastoScreen(
    gastoId: Int,
    viewModel: GastoViewModel,
    onVoltar: () -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var valorTexto by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var erro by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(gastoId) {
        if (gastoId != 0) {
            viewModel.buscarPorId(gastoId).firstOrNull()?.let {
                descricao = it.descricao
                valorTexto = it.valor.toString()
                categoria = it.categoria
            }
        }
    }

    CadastroGastoContent(
        isEdicao = gastoId != 0,
        descricao = descricao,
        onDescricaoChange = { descricao = it },
        valorTexto = valorTexto,
        onValorTextoChange = { valorTexto = it },
        categoria = categoria,
        onCategoriaChange = { categoria = it },
        erro = erro,
        onVoltar = onVoltar,
        onSalvar = {
            val valor = valorTexto.replace(",", ".").toDoubleOrNull()
            when {
                descricao.isBlank() -> erro = "Informe a descrição."
                valor == null || valor <= 0 -> erro = "Informe um valor válido."
                categoria.isBlank() -> erro = "Informe a categoria."
                else -> {
                    viewModel.salvar(gastoId, descricao.trim(), valor, categoria.trim())
                    onVoltar()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroGastoContent(
    isEdicao: Boolean,
    descricao: String,
    onDescricaoChange: (String) -> Unit,
    valorTexto: String,
    onValorTextoChange: (String) -> Unit,
    categoria: String,
    onCategoriaChange: (String) -> Unit,
    erro: String?,
    onVoltar: () -> Unit,
    onSalvar: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdicao) "Editar gasto" else "Novo gasto") },
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
            OutlinedTextField(
                value = descricao,
                onValueChange = onDescricaoChange,
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = valorTexto,
                onValueChange = onValorTextoChange,
                label = { Text("Valor (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = categoria,
                onValueChange = onCategoriaChange,
                label = { Text("Categoria (ex.: Alimentação)") },
                modifier = Modifier.fillMaxWidth()
            )

            erro?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Button(
                onClick = onSalvar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}

@Composable
fun ControleGastosContent(
    isEdicao: Boolean = false,
    descricao: String = "",
    valorTexto: String = "",
    categoria: String = "",
    erro: String? = null,
    onVoltar: () -> Unit = {},
    onSalvar: () -> Unit = {}
) {
    CadastroGastoContent(
        isEdicao = isEdicao,
        descricao = descricao,
        onDescricaoChange = {},
        valorTexto = valorTexto,
        onValorTextoChange = {},
        categoria = categoria,
        onCategoriaChange = {},
        erro = erro,
        onVoltar = onVoltar,
        onSalvar = onSalvar
    )
}

@Preview(showBackground = true, name = "Novo Gasto")
@Composable
fun PreviewCadastroGastosScreen() {
    ControleGastosIRedeTheme {
        ControleGastosContent()
    }
}

@Preview(showBackground = true, name = "Editar Gasto")
@Composable
fun PreviewEditarGastosScreen() {
    ControleGastosIRedeTheme {
        ControleGastosContent(
            isEdicao = true,
            descricao = "Supermercado",
            valorTexto = "250.50",
            categoria = "Alimentação"
        )
    }
}
