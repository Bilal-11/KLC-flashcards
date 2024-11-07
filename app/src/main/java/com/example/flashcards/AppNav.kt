package com.example.flashcards

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class Screen(val title : String){
  QueryScreen(title = "Query"),
  FlashScreen(title = "Flash")
}

@Composable
fun AppNav(
  navController : NavHostController =  rememberNavController(),
  viewModel: FlashViewModel
) {
  NavHost(
    navController = navController,
    startDestination = Screen.QueryScreen.title
  ) {
      composable(route = Screen.QueryScreen.title){
        QueryScreen(
          toFlash = {
            navController.popBackStack()
            navController.navigate(Screen.FlashScreen.title)
          },
          viewModel = viewModel
        )
      }

      composable(route = Screen.FlashScreen.title){
        QuizScreen(
          toQuery = {
            navController.popBackStack()
            navController.navigate(Screen.QueryScreen.title)
          },
          viewModel = viewModel
        )
      }

  }
}
