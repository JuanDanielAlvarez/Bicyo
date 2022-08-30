package com.bicyo.bicyo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun GroupCard(navController: NavHostController, cyclingGroup: CyclingGroup){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .clickable { navController.navigate("group_poll/${cyclingGroup.id}") }
    ){
        Column() {
            Text(text = cyclingGroup.name)
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun GroupCardPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        GroupCard(navController, CyclingGroup(1,"Grupo 1", listOf(), listOf()))
    }
}