package com.vaibhav.presentation.navigation

sealed class Screen(val route: String) {

    object EnterUserNameScreen: Screen("enter_user_name_screen")

}