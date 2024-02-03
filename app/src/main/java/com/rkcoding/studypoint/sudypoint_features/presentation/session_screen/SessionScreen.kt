package com.rkcoding.studypoint.sudypoint_features.presentation.session_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.DeleteDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.sessionList
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.ButtonSection
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.TimerSection
import com.rkcoding.studypoint.sudypoint_features.presentation.task_screen.component.SubjectListBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
    navController: NavController
) {

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
    val subjects = listOf(
        Subject(name = "English", goalHours = 15f, color = Subject.subjectCardColor[0].map { it.toArgb() }, subjectId = 0),
        Subject(name = "Hindi", goalHours = 10f, color = Subject.subjectCardColor[1].map { it.toArgb() }, subjectId = 1),
        Subject(name = "Maths", goalHours = 5f, color = Subject.subjectCardColor[2].map { it.toArgb() }, subjectId = 2),
        Subject(name = "Science", goalHours = 25f, color = Subject.subjectCardColor[3].map { it.toArgb() }, subjectId = 3),
        Subject(name = "Computer", goalHours = 35f, color = Subject.subjectCardColor[4].map { it.toArgb() }, subjectId = 4),
        Subject(name = "Social Science", goalHours = 18f, color = Subject.subjectCardColor[0].map { it.toArgb() }, subjectId = 5),
    )

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val scope = rememberCoroutineScope()
    var bottomSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var deleteDialogOpen by remember { mutableStateOf(false) }

    DeleteDialog(
        isDialogOpen = deleteDialogOpen,
        bodyText = "Are you sure you want to Delete Session?" + "This action can't be undone.",
        title = "Delete Session?",
        onDismissRequest = { deleteDialogOpen = false },
        onConfirmButtonClick = {
            deleteDialogOpen = false
        }
    )

    SubjectListBottomSheet(
        state = sheetState,
        isOpen = bottomSheetOpen,
        onDismissRequest = { bottomSheetOpen = false },
        subject = subjects,
        onSubjectClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) bottomSheetOpen = false
            }
        }
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SessionTopAppBar(
                title = "Study Session",
                onBackButtonClick = { navController.popBackStack() },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding()
                )
                .padding(horizontal = 12.dp)
        ){
            item {
                TimerSection(
                    modifier = Modifier
                        .padding(horizontal = 60.dp)
                        .aspectRatio(1f)
                )
            }

            item {
                RelatedToSubject(
                    relatedTOSubject = "English",
                    onClick = { bottomSheetOpen = true }
                )
            }

            item {
                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    statButtonClick = { /*TODO*/ },
                    cancelButtonClick = { /*TODO*/ },
                    finishBUTTONcLICK = {  }
                )
            }

            sessionList(
                sectionTitle = "Study Session History",
                session = session,
                onDeleteIconClick = { deleteDialogOpen = true },
                onCardClick = {  }
            )

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionTopAppBar(
    title: String,
    onBackButtonClick: () -> Unit,
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
        }
    )

}


@Composable
fun RelatedToSubject(
    modifier: Modifier = Modifier,
    relatedTOSubject: String,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Related to Subject", style = MaterialTheme.typography.bodyLarge)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = relatedTOSubject,
                style = MaterialTheme.typography.bodyLarge
            )

            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown Icon"
                )
            }
        }
    }
}


