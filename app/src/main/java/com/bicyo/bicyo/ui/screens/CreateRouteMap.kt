package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.entities.CyclingGroup
import com.bicyo.bicyo.entities.Route
import com.bicyo.bicyo.entities.User
import com.bicyo.bicyo.ui.components.RouteMapCreator
import com.bicyo.bicyo.ui.theme.BicyoTheme
import com.google.android.gms.maps.model.LatLng


@Composable
fun CreateRouteMap(navController:NavHostController, userId:Int, groupId:Int) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        val currentGroup = CyclingGroup(1,"Grupo Maravilla", listOf(), listOf())
        val currentRoute = Route(1,1,"Ruta 1",100.0f,user,null,mutableListOf())
        RouteMapCreator(currentRoute)

        Button(onClick = {
            //Agregar la ruta creada a la nube
            //Agregar la ruta al grupo correspondiente si es del caso
            //Agregar la ruta al usuario si es del caso
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