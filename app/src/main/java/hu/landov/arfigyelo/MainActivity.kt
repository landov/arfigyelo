package hu.landov.arfigyelo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.landov.arfigyelo.presentation.common.ErrorBar
import hu.landov.arfigyelo.presentation.common.MainViewModel
import hu.landov.arfigyelo.presentation.listing.CategoryScreen
import hu.landov.arfigyelo.presentation.listing.FavoritesListScreen
import hu.landov.arfigyelo.presentation.productdetail.DetailScreen
import hu.landov.arfigyelo.ui.theme.ArfigyeloTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArfigyeloTheme {
                WatcherApp()
            }
        }
    }
}

@Composable
fun WatcherApp() {
    //TODO do not pass the navController pass the functions and viewModels?
    val viewModel: MainViewModel = viewModel()
    val navController = rememberNavController()
    val errorState by viewModel.errorState

    Column {

        if(errorState.message != "") {
            ErrorBar(errorState.message, errorState.retryable) { viewModel.updateAll() }
        }

        NavHost(navController, startDestination = "categories") {

            composable(route = "categories") {
                CategoryScreen(navController = navController)
            }

            composable(
                route = "categories/{parent_id}",
                arguments = listOf(navArgument("parent_id") {
                    type = NavType.IntType
                })
            ) {
                CategoryScreen(navController = navController)
            }

            composable(
                route = "product/{product_id}",
                arguments = listOf(navArgument("product_id") {
                    type = NavType.StringType
                })
            ) {
                DetailScreen()
            }

            composable(route = "favorites") {
                FavoritesListScreen(navController)
            }
        }

    }
}


