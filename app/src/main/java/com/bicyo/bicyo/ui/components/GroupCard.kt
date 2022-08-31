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
import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun GroupCard(navController: NavHostController, cyclingGroup: CyclingGroup){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { navController.navigate("group_poll/${cyclingGroup.id}") }
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
                Icon(painter = painterResource(id = R.drawable.personas),
                    contentDescription = stringResource(id = R.string.user_icon_desc),
                    Modifier.size(40.dp),
                    tint = Color.Black)
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = cyclingGroup.name, textAlign = TextAlign.End)
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
                    Text(text = stringResource(id = R.string.members))
                    Text(text = "${cyclingGroup.members.size}")
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
fun GroupCardPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        GroupCard(navController, CyclingGroup(1,"Grupo 1", listOf(), listOf()))
    }
}