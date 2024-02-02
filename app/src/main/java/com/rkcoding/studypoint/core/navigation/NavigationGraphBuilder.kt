package com.rkcoding.studypoint.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.DashboardScreen
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.SessionScreen
import com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen.SubjectScreen
import com.rkcoding.studypoint.sudypoint_features.presentation.task_screen.TaskScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraphBuilder() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.DashBoardScreen.route
    ){
        composable(
            route = Screen.DashBoardScreen.route
        ) {
            DashboardScreen(navController = navController)
        }

        composable(
            route = Screen.SubjectScreen.route
        ){
            SubjectScreen()
        }

        composable(
            route = Screen.TaskScreen.route
        ){
            TaskScreen()
        }

        composable(
            route = Screen.SessionScreen.route
        ){
            SessionScreen()
        }

    }

}