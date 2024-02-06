package com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rkcoding.studypoint.core.navigation.Screen
import com.rkcoding.studypoint.core.utils.ShowSnackBarEvent
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.AddSubjectDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.DeleteDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.sessionList
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.taskList
import com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen.component.CountCardSection
import kotlinx.coroutines.flow.collectLatest


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen(
    navController: NavController,
    viewModel: SubjectViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    val snackBarHost = remember {
       SnackbarHostState()
    }

    LaunchedEffect(key1 = true){
        viewModel.snackBarEvent.collectLatest { event ->
            when(event){
                is ShowSnackBarEvent.ShowSnakeBar -> {
                    snackBarHost.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                ShowSnackBarEvent.NavigateUp -> {
                    navController.popBackStack()
                }
            }
        }
    }

//    LaunchedEffect(key1 = state.goalStudiesHour, key2 = state.studiedHour){
//        viewModel.onEvent(SubjectEvent.UpdateSubject)
//    }



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

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFabExtended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    var deleteSessionDialog by remember { mutableStateOf(false) }
    var deleteSubjectDialog by remember { mutableStateOf(false) }
    var isAddSubjectDialog by remember { mutableStateOf(false) }


    DeleteDialog(
        isDialogOpen = deleteSessionDialog,
        title = "Delete Session?",
        bodyText = "Are you sure, You want to delete this Session? Your studies hour will be reduce"
                + " by this session time. This action can not be under",
        onDismissRequest = { deleteSessionDialog = false },
        onConfirmButtonClick = {
            viewModel.onEvent(SubjectEvent.DeleteSession)
            deleteSessionDialog = false
        }
    )

    DeleteDialog(
        isDialogOpen = deleteSubjectDialog,
        title = "Delete Subjects?",
        bodyText = "Are you sure, You want to delete this Subject? All related"
                + " task and study session will be permanently removed. This action can not be under ",
        onDismissRequest = { deleteSubjectDialog = false },
        onConfirmButtonClick = {
            viewModel.onEvent(SubjectEvent.DeleteSubject)
            deleteSubjectDialog = false
        }
    )

    AddSubjectDialog(
        isDialogOpen = isAddSubjectDialog,
        onDismissRequest = { isAddSubjectDialog = false },
        onConfirmButtonClick = {
            viewModel.onEvent(SubjectEvent.UpdateSubject)
            isAddSubjectDialog = false
        },
        subjectName = state.subjectName,
        onSubjectNameChange = { viewModel.onEvent(SubjectEvent.OnSubjectNameChange(it)) },
        goalHour = state.goalStudiesHour,
        onGoalHourChange = { viewModel.onEvent(SubjectEvent.OnGoalHourChange(it)) },
        selectedColor = state.subjectCardColor,
        onColorChange = { viewModel.onEvent(SubjectEvent.OnSubjectColorChange(it)) }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHost) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = state.subjectName,
                onBackButtonClick = { navController.popBackStack() },
                onDeleteButtonClick = { deleteSubjectDialog = true },
                onEditButtonClick = { isAddSubjectDialog = true },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { navController.navigate(Screen.TaskScreen.route) },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add tasks")},
                text = { Text(text = "Add Tasks") },
                expanded = isFabExtended
            )
        }
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ){

            item {
                CountCardSection(
                    goalHour = state.goalStudiesHour,
                    studyHour = state.studiedHour.toString(),
                    progress = state.progress
                )
            }

            taskList(
                sectionTitle = "Upcoming Tasks",
                task = state.upcomingTasks,
                onTaskCardClick = { task ->
                    navController.navigate(
                        route = Screen.TaskScreen.route + "?subjectId=${task.taskSubjectId}/taskId=${task.taskId}"
                    )
                },
                onTaskCheckBoxClick = {  task ->
                    viewModel.onEvent(SubjectEvent.OnTaskCompleteChange(task))
                }
            )

            taskList(
                sectionTitle = "Completed Tasks",
                task = state.completedTasks,
                onTaskCardClick = { task ->
                    navController.navigate(
                        route = Screen.TaskScreen.route + "?subjectId=${task.taskSubjectId}/taskId=${task.taskId}"
                    )
                },
                onTaskCheckBoxClick = { task ->
                    viewModel.onEvent(SubjectEvent.OnTaskCompleteChange(task))
                }
            )

            sessionList(
                sectionTitle = "Recent Study Sessions",
                session = session,
                onDeleteIconClick = { session ->
                    viewModel.onEvent(SubjectEvent.OnSessionDeleteButtonClick(session))
                    deleteSessionDialog = true
                                    },
                onCardClick = {  }
            )

        }
    }

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {

    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = { onBackButtonClick() }
            ) {
              Icon(
                  imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                  contentDescription = "navigation back"
              )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        actions = {
            IconButton(onClick = { onDeleteButtonClick() }
            ) {
               Icon(
                   imageVector = Icons.Default.Delete,
                   contentDescription = "delete subject"
               )
            }

            IconButton(onClick = { onEditButtonClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "edit subject"
                )
            }
        }
    )
}


