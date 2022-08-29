package com.bicyo.bicyo.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.entities.Route
import com.bicyo.bicyo.entities.User
import com.bicyo.bicyo.ui.components.RouteCard
import com.bicyo.bicyo.ui.theme.BicyoTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Explore(navController: NavHostController,userId:Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Explore")
        // placeholder data
        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        val routes = listOf(
            Route(1, 1, "Ruta 1",100.0f, user,null),
            Route(1, 1, "Ruta 1",100.0f, user,null),
        )
        Box(
            modifier = Modifier
                .padding(0.dp, 12.dp, 0.dp, 0.dp)
                .width(max(300.dp, 350.dp))
                .height(100.dp)
                .clip(RoundedCornerShape(25))
                .background(Color.LightGray)
                .align(Alignment.CenterHorizontally)
        ) {
            routeCard(nameRoute = "Ruta 1", user = user)
        }
        Box(
            modifier = Modifier
                .padding(0.dp, 12.dp, 0.dp, 0.dp)
                .width(max(300.dp, 350.dp))
                .height(100.dp)
                .clip(RoundedCornerShape(25))
                .background(Color.LightGray)
                .align(Alignment.CenterHorizontally)
        ) {
            routeCard(nameRoute = "Ruta 2", user = user)
        }
        for(route in routes){
            RouteCard(navController,route)
        }
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("create_route/${userId}/0") },
                    contentColor = Color.White,
                    backgroundColor = Color(0xFF000000)) {
                Icon(Icons.Filled.Crop,
                    contentDescription = "Create route button")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {}
    }
}

@Composable
fun routeCard(
    nameRoute: String,
    user: User
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 0.dp, 0.dp, 0.dp)
    ){
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                Icons.Filled.PedalBike,
                contentDescription = "bike icon",
                Modifier.size(40.dp),
                tint = Color.Black)
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = nameRoute, textAlign = TextAlign.End)
        }
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .width(150.dp)
            ){
                Text(text = "Distancia")
                Text(text = "18.00 km")
            }
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .width(150.dp)
            ){
                Text(text = "Autor")
                Text(text = user.name)
            }
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