package com.bicyo.bicyo.ui.components

import android.graphics.drawable.PaintDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PedalBike
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.data.entities.Route
import com.bicyo.bicyo.data.entities.User
import com.bicyo.bicyo.ui.theme.BicyoTheme
import com.bicyo.bicyo.R

@Composable
fun RouteCard(navController: NavHostController, route: Route, user: User){
    Card(
        modifier = Modifier

            .fillMaxWidth()
            .background(Color.Blue)
            .clickable { navController.navigate("route/${route.id}") }
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(Color.LightGray)
                .fillMaxSize()
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(painter = painterResource(id = R.drawable.bicicleta),
                    contentDescription = stringResource(id = R.string.bike_icon_desc),
                    Modifier.size(40.dp),
                    tint = Color.Black)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = route.name, textAlign = TextAlign.End)
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
                    Text(text = stringResource(id = R.string.distance))
                    Text(text = "${route.distance} km")
                }
                ImageFromUrl(url = user.profilePictureUrl)
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .width(150.dp)
                ){
                    Text(text = stringResource(id = R.string.author))
                    Text(text = user.name)
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
fun RouteCardPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())

        RouteCard(navController,
            Route(1, 1, "Ruta 1",100.0f, user,null,mutableListOf()),
            User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf()))

    }
}