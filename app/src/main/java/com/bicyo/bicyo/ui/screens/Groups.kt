package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.entities.CyclingGroup
import com.bicyo.bicyo.ui.components.GroupCard
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun Groups(navController: NavHostController, userId: Int?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Groups")
        val cyclingGroups = listOf(
            CyclingGroup(2,"Ggupo 2", listOf(), listOf()),
            CyclingGroup(3,"Ggupo 3", listOf(), listOf())
        )
        for(group in cyclingGroups){
            GroupCard(navController,group)
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun GroupsPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        Groups(navController, 1)
    }
}