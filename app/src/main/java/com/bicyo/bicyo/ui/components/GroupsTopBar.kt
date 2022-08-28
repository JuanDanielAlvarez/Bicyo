package com.bicyo.bicyo.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.bicyo.bicyo.entities.CyclingGroup


@Composable
fun GroupsTopBar(
    navController: NavHostController,
    currentCyclingGroup: CyclingGroup,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Row {
                    Button(onClick = {
                        navController.navigate("group_poll/${currentCyclingGroup.id}")
                    }) {
                        Text(text = "Poll")
                    }

                    Button(onClick = {
                        navController.navigate("group_members/${currentCyclingGroup.id}")
                    }) {
                        Text(text = "Members")
                    }
                }
            }
        }) {
        content()
    }
}