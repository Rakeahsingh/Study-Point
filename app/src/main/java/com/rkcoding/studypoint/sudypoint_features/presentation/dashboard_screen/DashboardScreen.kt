package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rkcoding.studypoint.core.navigation.Screen
import com.rkcoding.studypoint.core.utils.ShowSnackBarEvent
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.AddSubjectDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.AddSubjectSection
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.CountCardSection
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.DeleteDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.sessionList
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.taskList
import com.rkcoding.studypoint.ui.theme.DarkBlue
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val task by viewModel.tasks.collectAsState()
    val sessions by viewModel.sessions.collectAsState()

    val snakeBarHost = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true){
        viewModel.snakeBar.collectLatest { event ->
            when(event){
                is ShowSnackBarEvent.ShowSnakeBar -> {
                    snakeBarHost.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }


//    val subjects = listOf(
//        Subject(name = "English", goalHours = 15f, color = Subject.subjectCardColor[0].map { it.toArgb() }, subjectId = 0),
//        Subject(name = "Hindi", goalHours = 10f, color = Subject.subjectCardColor[1].map { it.toArgb() }, subjectId = 1),
//        Subject(name = "Maths", goalHours = 5f, color = Subject.subjectCardColor[2].map { it.toArgb() }, subjectId = 2),
//        Subject(name = "Science", goalHours = 25f, color = Subject.subjectCardColor[3].map { it.toArgb() }, subjectId = 3),
//        Subject(name = "Computer", goalHours = 35f, color = Subject.subjectCardColor[4].map { it.toArgb() }, subjectId = 4),
//        Subject(name = "Social Science", goalHours = 18f, color = Subject.subjectCardColor[0].map { it.toArgb() }, subjectId = 5),
//    )

    val tasks = listOf(
        Task(
            title = "Preparing notes",
            description = "",
            dueDate = 0L,
            priority = 0,
            relatedToSubject = "",
            isCompleted = false,
            taskSubjectId = 0,
            taskId = 0
        ),
        Task(
            title = "complete homework",
            description = "",
            dueDate = 0L,
            priority = 1,
            relatedToSubject = "",
            isCompleted = true,
            taskSubjectId = 1,
            taskId = 1
        ),
        Task(
            title = "Assignments",
            description = "",
            dueDate = 0L,
            priority = 2,
            relatedToSubject = "",
            isCompleted = false,
            taskSubjectId = 2,
            taskId = 2
        )
    )

    val session = listOf(
        Session(
            sessionId = 0,
            sessionSubjectId = 0,
            relatedToSubject = "English",
            date = 0L,
            duration = 2
        ),
        Session(
            sessionId = 1,
            sessionSubjectId = 1,
            relatedToSubject = "Hindi",
            date = 0L,
            duration = 4
        ),
        Session(
            sessionId = 2,
            sessionSubjectId = 2,
            relatedToSubject = "Math",
            date = 0L,
            duration = 6
        )
    )

    var isAddSubjectDialog by remember {
        mutableStateOf(false)
    }

    var deleteDialog by remember {
        mutableStateOf(false)
    }

    DeleteDialog(
        isDialogOpen = deleteDialog,
        bodyText = "Are you sure, You want to delete this Session? Your studies hour will be reduce"
                + "by this session time. This action can not be under",
        onDismissRequest = { deleteDialog = false },
        onConfirmButtonClick = {
            viewModel.onEvent(DashboardEvent.DeleteSession)
            deleteDialog = false
        },
        title = "Delete Session?"
    )

    AddSubjectDialog(
        isDialogOpen = isAddSubjectDialog,
        onDismissRequest = { isAddSubjectDialog = false },
        onConfirmButtonClick = {
            viewModel.onEvent(DashboardEvent.SaveSubject)
            isAddSubjectDialog = false
        },
        subjectName = state.subjectName,
        onSubjectNameChange = { viewModel.onEvent(DashboardEvent.OnSubjectNameChange(it)) },
        goalHour = state.goalStudiesHour,
        onGoalHourChange = { viewModel.onEvent(DashboardEvent.OnGoalHourChange(it)) },
        selectedColor = state.subjectCardColor,
        onColorChange = { viewModel.onEvent(DashboardEvent.OnSubjectCardColorChange(it)) }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snakeBarHost) },
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Study Point",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold)
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
                    subjectCount = state.totalSubjectCountHour,
                    studiesCount = state.totalStudiesHour.toString(),
                    goalHour = state.totalGoalStudiesHour.toString()
                )
            }

            item {
               AddSubjectSection(
                   modifier = Modifier.fillMaxWidth(),
                   subjectList = state.subject,
                   addButtonClick = { isAddSubjectDialog = true },
                   subjectCardClick = { subject ->
                       navController.navigate(
                           Screen.SubjectScreen.route + "?subjectId=${subject.subjectId}"
                       )
                   }
               )
            }
            
            item{
                Button(
                    onClick = { navController.navigate(Screen.SessionScreen.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DarkBlue,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Start Study Session",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            taskList(
                sectionTitle = "Upcoming Tasks",
                task = task,
                onTaskCardClick = { navController.navigate(Screen.TaskScreen.route) },
                onTaskCheckBoxClick = { task ->
                    viewModel.onEvent(DashboardEvent.OnTaskCompleteChange(task))
                }
            )

            sessionList(
                sectionTitle = "Recent Study Sessions",
                session = sessions,
                onDeleteIconClick = { deleteDialog = true },
                onCardClick = { navController.navigate(Screen.SessionScreen.route) }
            )

        }

    }

}



