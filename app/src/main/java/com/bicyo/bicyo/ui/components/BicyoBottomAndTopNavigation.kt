package com.bicyo.bicyo.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun BicyoBottomAndTopNavigation(
    navController: NavHostController,
    selectedItem: String,
    userId: Int,
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Explore, "explore")
                    },
                    label = { Text(text = "Explore") },
                    selected = selectedItem == "explore",
                    onClick = {
                        navController.navigate("explore/${userId}")
                    },
                    alwaysShowLabel = false
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Group, "groups")
                    },
                    label = { Text(text = "Groups") },
                    selected = selectedItem == "groups",
                    onClick = {
                        navController.navigate("groups/${userId}")
                    },
                    alwaysShowLabel = false
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Person, "")
                    },
                    label = { Text(text = "Profile") },
                    selected = selectedItem == "profile",
                    onClick = {
                        navController.navigate("profile/${userId}")
                    },
                    alwaysShowLabel = false
                )
            }
        }){
        content()
    }
}