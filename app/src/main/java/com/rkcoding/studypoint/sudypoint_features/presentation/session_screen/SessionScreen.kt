package com.rkcoding.studypoint.sudypoint_features.presentation.session_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rkcoding.studypoint.core.utils.Constants.ACTION_SERVICE_CANCEL
import com.rkcoding.studypoint.core.utils.Constants.ACTION_SERVICE_START
import com.rkcoding.studypoint.core.utils.Constants.ACTION_SERVICE_STOP
import com.rkcoding.studypoint.core.utils.ShowSnackBarEvent
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.DeleteDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.sessionList
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.ButtonSection
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.ServiceHelper
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.StudySessionTimerService
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.TimerSection
import com.rkcoding.studypoint.sudypoint_features.presentation.session_screen.component.TimerState
import com.rkcoding.studypoint.sudypoint_features.presentation.task_screen.component.SubjectListBottomSheet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.DurationUnit

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
    viewModel: SessionViewModel = hiltViewModel(),
    navController: NavController,
    timerService: StudySessionTimerService
) {

    val state by viewModel.state.collectAsState()

    val hours by timerService.hours
    val minutes by timerService.minutes
    val second by timerService.seconds
    val currentTimerState by timerService.currentState


    val context = LocalContext.current

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val scope = rememberCoroutineScope()
    var bottomSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var deleteDialogOpen by remember { mutableStateOf(false) }

    val snackBarState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(key1 = true){
        viewModel.snackBar.collectLatest { event ->
            when(event){
                ShowSnackBarEvent.NavigateUp -> navController.popBackStack()
                is ShowSnackBarEvent.ShowSnakeBar -> {
                    snackBarState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = state.subjects){
        val subjectId = timerService.subjectId.value
        viewModel.onEvent(SessionEvent.UpdateSubjectIdAndRelatedSubject(
            subjectId = subjectId,
            relatedToSubject = state.subjects.find { it.subjectId == subjectId }?.name
        ))
    }

    DeleteDialog(
        isDialogOpen = deleteDialogOpen,
        bodyText = "Are you sure you want to Delete Session?" + "This action can't be undone.",
        title = "Delete Session?",
        onDismissRequest = { deleteDialogOpen = false },
        onConfirmButtonClick = {
            viewModel.onEvent(SessionEvent.DeleteSession)
            deleteDialogOpen = false
        }
    )

    SubjectListBottomSheet(
        state = sheetState,
        isOpen = bottomSheetOpen,
        onDismissRequest = { bottomSheetOpen = false },
        subject = state.subjects,
        onSubjectClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) bottomSheetOpen = false
            }
            viewModel.onEvent(SessionEvent.OnRelatedSubjectChange(it))
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarState)},
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
                .padding(top = 12.dp)

        ){
            item {
                TimerSection(
                    modifier = Modifier
                        .padding(horizontal = 60.dp)
                        .aspectRatio(1f),
                    hour = hours,
                    minute = minutes,
                    second = second
                )
            }

            item {
                RelatedToSubject(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    relatedTOSubject = state.relatedToSubject ?: "",
                    onClick = { bottomSheetOpen = true },
                    second = second
                )
            }

            item {
                ButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    statButtonClick = {
                        if (state.subjectId != null && state.relatedToSubject != null) {
                            ServiceHelper.triggerForegroundService(
                                context = context,
                                action = if (currentTimerState == TimerState.STARTED) {
                                    ACTION_SERVICE_STOP
                                } else {
                                    ACTION_SERVICE_START
                                }
                            )
                            timerService.subjectId.value = state.subjectId
                        }else{
                            viewModel.onEvent(SessionEvent.NotifyToUpdateSubject)
                        }
                    },
                    cancelButtonClick = {
                        ServiceHelper.triggerForegroundService(
                            context = context,
                            action = ACTION_SERVICE_CANCEL
                        )
                    },
                    finishButtonClick = {
                        val duration = timerService.duration.toLong(DurationUnit.SECONDS)
                        if (duration > 30){
                            ServiceHelper.triggerForegroundService(
                                context = context,
                                action = ACTION_SERVICE_CANCEL
                            )
                        }

                        viewModel.onEvent(SessionEvent.SaveSession(duration))
                    },
                    timerState = currentTimerState,
                    second = second
                )
            }

            sessionList(
                sectionTitle = "Study Session History",
                session = state.sessions,
                onDeleteIconClick = {
                    deleteDialogOpen = true
                    viewModel.onEvent(SessionEvent.OnDeleteSessionButtonClick(it))
                },
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
    onClick: () -> Unit,
    second: String
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

            IconButton(
                onClick = onClick,
                enabled = second == "00"
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown Icon"
                )
            }
        }
    }
}


