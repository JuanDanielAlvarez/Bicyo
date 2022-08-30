package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.entities.CyclingGroup
import com.bicyo.bicyo.entities.User
import com.bicyo.bicyo.ui.components.GroupsTopBar
import com.bicyo.bicyo.ui.components.UserCard
import com.bicyo.bicyo.ui.theme.BicyoTheme

@Composable
fun GroupMembers(navController: NavHostController, groupId: Int?) {
    //Placeholder data
    val currentGroup = CyclingGroup(
        groupId?:1,
        "Grupo 1",
        listOf(
            User(1,"juan.alvarez@epn.edu.ec","Juan Alvarez","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf()),
            User(2,"daniel.aimacana@epn.edu.ec","Daniel Aimacana","","https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg",1,1, listOf(), listOf())
        ),
        listOf()
    )

    GroupsTopBar(navController, currentCyclingGroup = currentGroup) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            for(member in currentGroup.members){
                Box(
                    modifier = Modifier
                        .padding(0.dp, 12.dp, 0.dp, 0.dp)
                        .width(max(300.dp, 350.dp))
                        .height(100.dp)
                        .clip(RoundedCornerShape(25))
                        .background(Color.LightGray)
                        .align(Alignment.CenterHorizontally)
                ) {
                UserCard(navController = navController, user = member)}
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
fun GroupMembersPreview() {
    val navController = rememberNavController()
    BicyoTheme {
        GroupMembers(navController, 1)
    }
}