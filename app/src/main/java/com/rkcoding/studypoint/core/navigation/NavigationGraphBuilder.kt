package com.rkcoding.studypoint.core.navigation

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.DashboardScreen
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.SessionScreen
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.StudySessionTimerService
import com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen.SubjectScreen
import com.rkcoding.studypoint.sudypoint_features.presentation.task_screen.TaskScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraphBuilder(
    timerService: StudySessionTimerService
) {

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
            route = Screen.SubjectScreen.route + "?subjectId={subjectId}",
            arguments = listOf(
                navArgument("subjectId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(900)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(900)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(900)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(900)
                )
            }
        ){
            val subject = it.arguments?.getInt("subjectId") ?: -1
            SubjectScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.TaskScreen.route + "?subjectId={subjectId}/taskId={taskId}",
            arguments = listOf(
                navArgument("subjectId"){
                    type = NavType.IntType
                    defaultValue = -1
                },

                navArgument("taskId"){
                    type = NavType.IntType
                    defaultValue = -1
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(900)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(900)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(900)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(900)
                )
            }
        ){
            val subject = it.arguments?.getInt("subjectId") ?: -1
            val task = it.arguments?.getInt("taskId") ?: -1
            TaskScreen(navController = navController)
        }

        composable(
            route = Screen.SessionScreen.route,
            deepLinks = listOf(navDeepLink {
                uriPattern = "study_point://dashboard/session"
                action = Intent.ACTION_VIEW
            }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(900)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(900)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(900)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(900)
                )
            }
        ){
            SessionScreen(
                navController = navController,
                timerService = timerService
            )
        }

    }
}