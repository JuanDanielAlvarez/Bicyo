package com.bicyo.bicyo.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.R



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
                        Text(text = stringResource(id = R.string.poll))
                    }

                    Button(onClick = {
                        navController.navigate("group_members/${currentCyclingGroup.id}")
                    }) {
                        Text(text = stringResource(id = R.string.members))
                    }
                }
            }
        }) {
        content()
    }
}