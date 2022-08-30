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
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.bicyo.bicyo.ui.components.RouteCard
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun Explore(navController: NavHostController,userId:Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Explore")
        Button(onClick = {
            navController.navigate("create_route/${userId}/0")
        }) {
            Text(text = "Create Route")
        }

        // placeholder data

        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        val routes = listOf(
            Route(1, 1, "Ruta 1",100.0f, user,null,mutableListOf()),
            Route(1, 1, "Ruta 1",100.0f, user,null,mutableListOf()),

        )
        for(route in routes){
            RouteCard(navController,route)
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun ExplorePreview() {
    val navController = rememberNavController()
    BicyoTheme {
        Explore(navController,0)
    }
}