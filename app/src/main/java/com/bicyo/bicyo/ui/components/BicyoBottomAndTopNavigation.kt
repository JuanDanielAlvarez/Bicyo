package com.bicyo.bicyo.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import com.bicyo.bicyo.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bicyo.bicyo.MainScreen
import java.lang.reflect.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BicyoBottomAndTopNavigation(
    navController: NavHostController,
    selectedItem: String,
    userId: Int,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text= "Bycio") },
                navigationIcon = if (navController.previousBackStackEntry != null) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                } else {
                    null
                }

            )
        },
        bottomBar = {
            BottomAppBar {
                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Explore, stringResource(id = R.string.explore))
                    },
                    label = { Text(text = stringResource(id = R.string.explore_label)) },
                    selected = selectedItem == stringResource(id = R.string.explore),
                    onClick = {
                        navController.navigate("explore/${userId}")
                    },
                    alwaysShowLabel = false
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Group, "groups")
                    },
                    label = { Text(text = stringResource(id = R.string.groups_label)) },
                    selected = selectedItem == stringResource(id = R.string.groups),
                    onClick = {
                        navController.navigate("groups/${userId}")
                    },
                    alwaysShowLabel = false
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Person, "")
                    },
                    label = { Text(text = stringResource(id = R.string.profile_label)) },
                    selected = selectedItem == stringResource(id = R.string.profile),
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