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
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun RouteCard(navController: NavHostController, route: Route){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .clickable { navController.navigate("route/${route.id}") }
    ){
        Column() {
            Text(text = route.name)
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun RouteCardPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        RouteCard(navController, Route(1, 1, "Ruta 1",100.0f, user,null, mutableListOf()))
    }
}