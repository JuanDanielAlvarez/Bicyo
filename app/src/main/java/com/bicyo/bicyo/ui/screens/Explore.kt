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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


import com.bicyo.bicyo.ui.components.ImageFromUrl

import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User

import com.bicyo.bicyo.ui.components.RouteCard
import com.bicyo.bicyo.ui.theme.BicyoTheme
import com.bicyo.bicyo.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Explore(navController: NavHostController,userId:Int) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(750.dp)) {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("create_route/${userId}/0") },
                    contentColor = Color.Black,
                    backgroundColor = Color(0xFFFFFFFF),
                    modifier = Modifier.width(50.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.boton_mas),
                        contentDescription = stringResource(id = R.string.create_button_desc))
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) {Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            // placeholder data
            val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
            val routes = listOf(
                Route(1, 1, "Parque metropolitano",40.0f, user,null,mutableListOf()),
                Route(1, 1, "Ruta de las iglesias",20.0f, user,null,mutableListOf()),
                Route(1, 1, "Parque bicentenario",55.0f, user,null,mutableListOf()),
                Route(1, 1, "Panecillo",12.0f, user,null,mutableListOf()),
                Route(1, 1, "Ciclopaseo",44.0f, user,null,mutableListOf()),
            )

            for(route in routes){
                Box(
                    modifier = Modifier

                        .padding(0.dp, 12.dp, 0.dp, 0.dp)
                        .width(max(300.dp, 350.dp))
                        .height(100.dp)
                        .clip(RoundedCornerShape(25))
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally)
                ) {
                    RouteCard(navController,route,user)
                }
            }
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