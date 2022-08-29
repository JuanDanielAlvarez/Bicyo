package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun Signup(navController: NavHostController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ){
        val currentUserId = 1
        val name = remember {
            mutableStateOf("")
        }
        val email = remember {
            mutableStateOf("")
        }
        val password = remember {
            mutableStateOf("")
        }

        val passwordVisible = remember {
            mutableStateOf(false)
        }
        Text(text = "BICyO",
            style = TextStyle(
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold)
        )
        Text(text = "registrarse",
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "Nombre")
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
                placeholder = { Text(text = "Nombre Apellido") },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFFFFFF)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Correo electrónico")
            TextField(
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = { Text(text = "example@email.com") },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFFFFFF)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Contraseña")
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                placeholder = { Text(text = "***********") },
                visualTransformation = if (passwordVisible.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFFFFFFF)
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("explore/${currentUserId}") },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF000000),
                contentColor = Color(0xFFFFFFFF)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "registrarse",
                fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .background(Color(0xFF000000))
                .fillMaxSize()
                .padding(top = 16.dp)
        ){
            Text(text = "registrate con redes sociales",
                color = Color.White,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("explore/${currentUserId}") },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFFFFFF),
                    contentColor = Color(0xFF000000)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "registrate con google",
                    fontSize = 20.sp)
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
fun SignupPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        Signup(navController)
    }
}