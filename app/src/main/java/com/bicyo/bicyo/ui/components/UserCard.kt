package com.bicyo.bicyo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.R
import com.bicyo.bicyo.entities.CyclingGroup
import com.bicyo.bicyo.entities.Route
import com.bicyo.bicyo.entities.User
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun UserCard(navController: NavHostController, user: User){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp, 0.dp, 0.dp)
                .background(Color.LightGray)
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ImageFromUrl(url = user.profilePictureUrl)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = user.name, textAlign = TextAlign.End)
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
                    Text(text = stringResource(id = R.string.description))
                    Text(text = user.description)
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
fun UserCardPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        UserCard(navController, user)
    }
}