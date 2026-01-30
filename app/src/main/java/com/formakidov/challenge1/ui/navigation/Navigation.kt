package com.formakidov.challenge1.ui.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.formakidov.challenge1.data.model.Album
import com.formakidov.challenge1.ui.screens.detail.DetailScreen
import com.formakidov.challenge1.ui.screens.list.AlbumListScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    data object List : Screen("list_screen")
    data object Detail : Screen("detail_screen/{albumJson}") {
        fun createRoute(album: Album): String {
            val json = Json.encodeToString(album)
            val encodedJson = Uri.encode(json)
            return "detail_screen/$encodedJson"
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.List.route
    ) {
        composable(Screen.List.route) {
            AlbumListScreen(
                onAlbumClick = { album ->
                    navController.navigate(Screen.Detail.createRoute(album))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("albumJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val albumJson = backStackEntry.arguments?.getString("albumJson")
            val album = albumJson?.let {
                Json.decodeFromString<Album>(it)
            }

            if (album != null) {
                DetailScreen(
                    album = album,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
