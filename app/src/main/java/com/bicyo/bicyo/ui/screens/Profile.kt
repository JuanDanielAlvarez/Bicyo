package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.entities.User
import com.bicyo.bicyo.ui.components.ImageFromUrl
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun Profile(navController: NavHostController, userId: Int?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Text(text = "Profile")

        val user = User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())

        ImageFromUrl(url = user.profilePictureUrl)

        Button(onClick = {
            navController.navigate("edit_profile/${userId}")
        }) {
            Text(text = "edit profile")
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    BicyoTheme {
        Profile(navController, 1)
    }
}