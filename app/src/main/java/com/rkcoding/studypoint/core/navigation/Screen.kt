package com.rkcoding.studypoint.core.navigation

sealed class Screen(val route: String) {

    data object DashBoardScreen: Screen("dashboardScreen")
    data object SubjectScreen: Screen("subjectScreen")
    data object TaskScreen: Screen("taskScreen")
    data object SessionScreen: Screen("sessionScreen")

}