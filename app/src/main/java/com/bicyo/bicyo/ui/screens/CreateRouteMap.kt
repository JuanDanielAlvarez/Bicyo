package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.entities.Route
import com.bicyo.bicyo.entities.User
import com.bicyo.bicyo.ui.components.RouteMap
import com.bicyo.bicyo.ui.theme.BicyoTheme


@Composable
fun CreateRouteMap(navController:NavHostController, userId:Int, groupId:Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Create Route")

        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        val currentRoute = Route(1,1,"Ruta 1",100.0f,user,null,mutableListOf())
        RouteMap(currentRoute)

        Button(onClick = {
            navController.navigate("explore/${userId}")
        }) {
            Text(text = "Create")
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun CreateRoutePreview() {
    val navController = rememberNavController()
    BicyoTheme {
        CreateRouteMap(navController,1,1)
    }
}