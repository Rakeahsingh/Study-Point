package com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen

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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.AddSubjectDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.DeleteDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.sessionList
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.taskList
import com.rkcoding.studypoint.sudypoint_features.presentation.subject_screen.component.CountCardSection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectScreen() {

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

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberLazyListState()
    val isFabExtended by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    var selectedColor by remember { mutableStateOf(Subject.subjectCardColor.random()) }
    var deleteSessionDialog by remember { mutableStateOf(false) }
    var deleteSubjectDialog by remember { mutableStateOf(false) }
    var isAddSubjectDialog by remember { mutableStateOf(false) }
    var subject by remember { mutableStateOf("") }
    var goalHour by remember { mutableStateOf("") }

    DeleteDialog(
        isDialogOpen = deleteSessionDialog,
        title = "Delete Session?",
        bodyText = "Are you sure, You want to delete this Session? Your studies hour will be reduce"
                + "by this session time. This action can not be under",
        onDismissRequest = { deleteSessionDialog = false },
        onConfirmButtonClick = {
            deleteSessionDialog = false
        }
    )

    DeleteDialog(
        isDialogOpen = deleteSubjectDialog,
        title = "Delete Subjects?",
        bodyText = "Are you sure, You want to delete this Subject? All related"
                + "task and study session will be permanently removed. This action can not be under ",
        onDismissRequest = { deleteSubjectDialog = false },
        onConfirmButtonClick = {
            deleteSubjectDialog = false
        }
    )

    AddSubjectDialog(
        isDialogOpen = isAddSubjectDialog,
        onDismissRequest = { isAddSubjectDialog = false },
        onConfirmButtonClick = {
            isAddSubjectDialog = false
        },
        subjectName = subject,
        onSubjectNameChange = { subject = it },
        goalHour = goalHour,
        onGoalHourChange = { goalHour = it },
        selectedColor = selectedColor,
        onColorChange = { selectedColor = it }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = "English",
                onBackButtonClick = { /*TODO*/ },
                onDeleteButtonClick = { deleteSubjectDialog = true },
                onEditButtonClick = { isAddSubjectDialog = true },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
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
                    goalHour = "15",
                    studyHour = "10",
                    progress = 0.75f
                )
            }

            taskList(
                sectionTitle = "Upcoming Tasks",
                task = tasks,
                onTaskCardClick = {  },
                onTaskCheckBoxClick = {  }
            )

            taskList(
                sectionTitle = "Completed Tasks",
                task = tasks,
                onTaskCardClick = {  },
                onTaskCheckBoxClick = {  }
            )

            sessionList(
                sectionTitle = "Recent Study Sessions",
                session = session,
                onDeleteIconClick = { deleteSessionDialog = true },
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


@Preview
@Composable
fun Preview() {
    SubjectScreen()
}