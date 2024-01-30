package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.AddSubjectSection
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.CountCardSection
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.SubjectCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {

    val subjects = listOf(
        Subject(name = "English", goalHours = 15f, color = Subject.subjectCardColor[0]),
        Subject(name = "Hindi", goalHours = 10f, color = Subject.subjectCardColor[1]),
        Subject(name = "Maths", goalHours = 5f, color = Subject.subjectCardColor[2]),
        Subject(name = "Science", goalHours = 25f, color = Subject.subjectCardColor[3]),
        Subject(name = "Computer", goalHours = 35f, color = Subject.subjectCardColor[4]),
        Subject(name = "Social Science", goalHours = 18f, color = Subject.subjectCardColor[0]),
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Study Point", fontWeight = FontWeight.Bold)
            })
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ){

            item {
                CountCardSection(
                    subjectCount = 15,
                    studiesCount = "10",
                    goalHour = "5"
                )
            }

            item {
               AddSubjectSection(
                   modifier = Modifier.fillMaxWidth(),
                   subjectList = subjects,
                   addButtonClick = {  }
               )
            }

        }

    }

}


@Preview
@Composable
fun Preview() {
    DashboardScreen()
}