package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.ui.components.GroupCard
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun Groups(navController: NavHostController, userId: Int?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        val cyclingGroups = listOf(
            CyclingGroup(2,"Grupo 2", listOf(), listOf()),
            CyclingGroup(3,"Grupo 3", listOf(), listOf())
        )
        for(group in cyclingGroups){
            Box(
                modifier = Modifier
                    .padding(0.dp, 12.dp, 0.dp, 0.dp)
                    .width(max(300.dp, 350.dp))
                    .height(100.dp)
                    .clip(RoundedCornerShape(25))
                    .background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
            ) {
            GroupCard(navController,group)}
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