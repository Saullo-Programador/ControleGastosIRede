package com.example.controlegastosirede.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.controlegastosirede.ui.screens.CadastroGastoScreen
import com.example.controlegastosirede.ui.screens.DetalhesGastoScreen
import com.example.controlegastosirede.ui.screens.ListaGastosScreen
import com.example.controlegastosirede.viewmodel.GastoViewModel


object Rotas {
    const val LISTA = "lista"
    const val CADASTRO = "cadastro?id={id}"
    const val DETALHES = "detalhes/{id}"

    fun cadastro(id: Int = 0) = "cadastro?id=$id"
    fun detalhes(id: Int) = "detalhes/$id"
}

@Composable
fun AppNavigation(viewModel: GastoViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Rotas.LISTA) {

        composable(Rotas.LISTA) {
            ListaGastosScreen (
                viewModel = viewModel,
                onAdicionar = { navController.navigate(Rotas.cadastro()) },
                onAbrirDetalhes = { id -> navController.navigate(Rotas.detalhes(id)) }
            )
        }

        composable(
            route = Rotas.CADASTRO,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
                defaultValue = 0
            })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            CadastroGastoScreen(
                gastoId = id,
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() }
            )
        }

        composable(
            route = Rotas.DETALHES,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: 0
            DetalhesGastoScreen(
                gastoId = id,
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() },
                onEditar = { navController.navigate(Rotas.cadastro(id)) }
            )
        }
    }
}
