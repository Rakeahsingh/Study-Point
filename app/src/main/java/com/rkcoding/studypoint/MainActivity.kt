package com.rkcoding.studypoint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.DashboardScreen
import com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen.SubjectScreen
import com.rkcoding.studypoint.ui.theme.StudyPointTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyPointTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    SubjectScreen()

                }
            }
        }
    }
}

