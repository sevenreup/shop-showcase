package com.skybox.shopshowcase.presentation.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.skybox.shopshowcase.R
import com.skybox.shopshowcase.presentation.ui.screens.cart.CartScreen
import com.skybox.shopshowcase.presentation.ui.screens.feed.FeedScreen
import com.skybox.shopshowcase.presentation.ui.screens.product.ProductScreen

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    data object Feed : Screen("/", Icons.Filled.Home, R.string.home)
    data object Cart : Screen("/cart", Icons.Filled.ShoppingCart, R.string.cart)
}

val items = listOf(
    Screen.Feed,
    Screen.Cart,
)

@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(bottomBar = {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val shouldShowBottomNavigation = when (currentDestination?.route) {
            Screen.Feed.route, Screen.Cart.route -> true
            else -> false
        }
        if (shouldShowBottomNavigation) NavigationBar {
            items.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "/",
            Modifier.padding(innerPadding)
        ) {
            createNavGraph(navigateToRoute = { route ->
                navController.navigate(route)
            })
        }
    }
}

fun NavGraphBuilder.createNavGraph(
    navigateToRoute: (route: String) -> Unit,
) {
    composable("/") {
        FeedScreen(
            navigate = navigateToRoute
        )
    }
    composable(
        "/product/{id}",
        arguments = listOf(navArgument("id") { type = NavType.StringType })
    ) { backStackEntry ->
        val productId = backStackEntry.arguments?.getString("id") ?: ""
        ProductScreen(productId)
    }
    composable("/cart") {
        CartScreen()
    }
}