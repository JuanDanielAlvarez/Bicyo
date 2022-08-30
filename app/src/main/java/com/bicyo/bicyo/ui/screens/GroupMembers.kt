package com.bicyo.bicyo.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bicyo.bicyo.data.entities.CyclingGroup
import com.bicyo.bicyo.data.entities.User
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
            Text(text = "GroupMembers")
            for(member in currentGroup.members){
                UserCard(navController = navController, user = member)
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