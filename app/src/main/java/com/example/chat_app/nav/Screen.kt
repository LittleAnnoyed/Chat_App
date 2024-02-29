package com.example.chat_app.nav

sealed class Screen(val route: String) {


    data object LoginScreen: Screen("login_screen")
    data object RegisterScreen: Screen("register_screen")
    data object SetDataScreen: Screen("set_data_screen")

    data object HomeScreen: Screen("home_screen")
    data object FindUserScreen: Screen("find_user_screen")
    data object ChatScreen: Screen("chat_screen")

}