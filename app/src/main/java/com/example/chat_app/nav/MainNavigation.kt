package com.example.chat_app.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chat_app.presentation.auth.first.LoginScreen
import com.example.chat_app.presentation.auth.first.RegisterScreen
import com.example.chat_app.presentation.chat.ChatScreen
import com.example.chat_app.presentation.find.FindUserScreen
import com.example.chat_app.presentation.group.create.GroupCreateScreen
import com.example.chat_app.presentation.home.HomeScreen
import com.example.chat_app.util.Constants.CHAT_ID

@Composable
fun MainNavigation(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(route = "${Screen.ChatScreen.route}/{${CHAT_ID}") { backStackEntry ->
            ChatScreen(
                navController = navController,
                chatId = backStackEntry.arguments!!.getString(CHAT_ID)
            )
        }
        composable(route = Screen.AddToGroupScreen.route) {
            GroupCreateScreen(navController = navController)
        }
        composable(route = Screen.FindUserScreen.route) {
            FindUserScreen(navController = navController)
        }
    }
}

