package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.bicyo.bicyo.ui.components.RouteCard
import com.bicyo.bicyo.ui.components.RouteMapViewer
import com.bicyo.bicyo.ui.theme.BicyoTheme
import com.google.android.gms.maps.model.LatLng

@Composable
fun RouteScreen(navController: NavHostController, routeId: Int?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Route")

        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        val currentRoute = Route(1,1,"Ruta 1",100.0f,user,null,mutableListOf(
            LatLng(-0.21, -78.49),
            LatLng(-0.22, -78.49)
        ))

        //Placeholder data

        RouteMapViewer(currentRoute)
        RouteCard(navController, Route(1, 1, "Ruta 1",100.0f, user,null,mutableListOf()),user)

    }
}


@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun RoutePreview() {
    val navController = rememberNavController()
    BicyoTheme {
        RouteScreen(navController, 1)
    }
}