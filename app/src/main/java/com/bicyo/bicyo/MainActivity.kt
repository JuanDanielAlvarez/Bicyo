package com.bicyo.bicyo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bicyo.bicyo.ui.components.BicyoBottomAndTopNavigation
import com.bicyo.bicyo.ui.screens.*
import com.bicyo.bicyo.ui.theme.BicyoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BicyoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") { Login(navController) }
        composable("signup") { Signup(navController) }

        composable("explore/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "explore",backStackEntry.arguments?.getInt("routeId")?:0) {
                Explore(navController, backStackEntry.arguments?.getInt("userId")?:0)
            }
        }

        composable(
            "route/{routeId}",
            arguments = listOf(navArgument("routeId") { type = NavType.IntType })
        ) { backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "explore",backStackEntry.arguments?.getInt("routeId")?:0) {
                RouteScreen(navController, backStackEntry.arguments?.getInt("routeId"))
            }
        }

        composable(
            "create_route/{userId}/{groupId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ){backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "explore",backStackEntry.arguments?.getInt("routeId")?:0) {
                CreateRouteMap(
                    navController,
                    backStackEntry.arguments?.getInt("userId")?:0,
                    backStackEntry.arguments?.getInt("groupId")?:0
                )
            }
        }

        composable(
            "groups/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "groups",backStackEntry.arguments?.getInt("routeId")?:0) {
                Groups(navController, backStackEntry.arguments?.getInt("userId"))
            }
        }

        composable(
            "group_poll/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.IntType })
        ) { backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "groups",backStackEntry.arguments?.getInt("routeId")?:0) {
                GroupPoll(navController, backStackEntry.arguments?.getInt("groupId"))
            }
        }

        composable(
            "group_members/{groupId}",
            arguments = listOf(navArgument("groupId") { type = NavType.IntType })
        ) { backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "groups",backStackEntry.arguments?.getInt("routeId")?:0) {
                GroupMembers(navController, backStackEntry.arguments?.getInt("groupId"))
            }
        }

        composable(
            "profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "profile",backStackEntry.arguments?.getInt("routeId")?:0) {
                Profile(navController, backStackEntry.arguments?.getInt("userId"))
            }
        }

        composable(
            "edit_profile/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            BicyoBottomAndTopNavigation(navController, "profile",backStackEntry.arguments?.getInt("routeId")?:0) {
                EditProfile(navController, backStackEntry.arguments?.getInt("userId"))
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BicyoTheme {
        MainScreen()
    }
}