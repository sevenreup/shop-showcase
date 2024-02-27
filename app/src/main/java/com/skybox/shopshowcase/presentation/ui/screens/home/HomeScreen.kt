package com.skybox.shopshowcase.presentation.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person3
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.skybox.shopshowcase.presentation.ui.components.DefaultSnackbar
import com.skybox.shopshowcase.presentation.ui.screens.cart.CartScreen
import com.skybox.shopshowcase.presentation.ui.screens.feed.FeedScreen
import com.skybox.shopshowcase.presentation.ui.screens.orders.OrdersScreen
import com.skybox.shopshowcase.presentation.ui.screens.product.ProductScreen
import com.skybox.shopshowcase.presentation.ui.screens.profile.ProfileScreen

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val resourceId: Int) {
    data object Feed : Screen("/", Icons.Filled.Home, R.string.home)
    data object Cart : Screen("/cart", Icons.Filled.ShoppingCart, R.string.cart)
    data object Profile : Screen("/profile", Icons.Filled.Person3, R.string.profile)
}

val items = listOf(
    Screen.Feed,
    Screen.Cart,
    Screen.Profile
)

@Composable
fun HomeScreen(navController: NavHostController) {
    val snackHostState = remember { SnackbarHostState() }

    Scaffold(bottomBar = {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val shouldShowBottomNavigation = when (currentDestination?.route) {
            Screen.Feed.route, Screen.Cart.route, Screen.Profile.route -> true
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
    }, snackbarHost = {
        DefaultSnackbar(
            snackBarHostState = snackHostState,
            onDismiss = {
                snackHostState.currentSnackbarData?.dismiss()
            }
        )
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "/",
            Modifier.padding(innerPadding)
        ) {
            createNavGraph(snackBarHostState = snackHostState, navigateToRoute = { route ->
                navController.navigate(route)
            }, navigateBack = { navController.popBackStack() })
        }
    }
}

fun NavGraphBuilder.createNavGraph(
    snackBarHostState: SnackbarHostState, navigateToRoute: (route: String) -> Unit,
    navigateBack: () -> Unit
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
        ProductScreen(productId, navigateBack = navigateBack)
    }
    composable("/profile") {
        ProfileScreen(navigate = navigateToRoute)
    }
    composable("/cart") {
        CartScreen()
    }
    composable("/orders") {
        OrdersScreen(navigateBack = navigateBack)
    }
}