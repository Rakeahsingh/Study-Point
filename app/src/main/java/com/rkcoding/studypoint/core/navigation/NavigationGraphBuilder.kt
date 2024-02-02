package com.rkcoding.studypoint.core.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
            route = Screen.SubjectScreen.route + "?subjectId={subjectID}",
            arguments = listOf(
                navArgument("subjectId"){
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            }
        ){
            val subject = it.arguments?.getInt("subjectId") ?: -1
            SubjectScreen(
                navController = navController,
                subjects = subject
            )
        }

        composable(
            route = Screen.TaskScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            }
        ){
            TaskScreen(navController = navController)
        }

        composable(
            route = Screen.SessionScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(500, easing = LinearOutSlowInEasing)
                )
            }
        ){
            SessionScreen(navController = navController)
        }

    }
}