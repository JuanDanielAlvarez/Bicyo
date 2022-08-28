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
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun Login(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        val currentUserId = 1
        Text(text = "Login")
        Button(onClick = { navController.navigate("signup") }) {
            Text(text = "SIGN UP")
        }
        Button(onClick = { navController.navigate("explore/${currentUserId}") }) {
            Text(text = "LOG IN")
        }
    }
}


@Preview(
    showBackground = true,
    widthDp = 414,
    heightDp = 736,
)
@Composable
fun LoginPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        Login(navController)
    }
}