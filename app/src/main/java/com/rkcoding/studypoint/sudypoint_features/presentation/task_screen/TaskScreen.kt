package com.rkcoding.studypoint.sudypoint_features.presentation.task_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rkcoding.studypoint.core.utils.Priority
import com.rkcoding.studypoint.core.utils.toDateFormat
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.DeleteDialog
import com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component.TaskCheckBox
import com.rkcoding.studypoint.sudypoint_features.presentation.task_screen.component.SubjectListBottomSheet
import com.rkcoding.studypoint.sudypoint_features.presentation.task_screen.component.TaskDatePicker
import com.rkcoding.studypoint.ui.theme.CustomBlue
import kotlinx.coroutines.launch
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navController: NavController
) {

    val subjects = listOf(
        Subject(name = "English", goalHours = 15f, color = Subject.subjectCardColor[0], subjectId = 0),
        Subject(name = "Hindi", goalHours = 10f, color = Subject.subjectCardColor[1], subjectId = 1),
        Subject(name = "Maths", goalHours = 5f, color = Subject.subjectCardColor[2], subjectId = 2),
        Subject(name = "Science", goalHours = 25f, color = Subject.subjectCardColor[3], subjectId = 3),
        Subject(name = "Computer", goalHours = 35f, color = Subject.subjectCardColor[4], subjectId = 4),
        Subject(name = "Social Science", goalHours = 18f, color = Subject.subjectCardColor[0], subjectId = 5),
    )

    val scope = rememberCoroutineScope()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isTitleError by rememberSaveable { mutableStateOf<String?>(null) }
    var isDescriptionError by rememberSaveable { mutableStateOf<String?>(null) }

    isTitleError = when{
        title.isBlank() -> "Please Enter Task Title"
        title.length < 4 -> "Task Title is too short"
        title.length > 25 -> "Task Title is too Long"
        else -> null
    }

    isDescriptionError = when{
        description.isBlank() -> "Please Enter Task Description"
        description.length < 10 -> "Task Description is too short"
        description.length > 100 -> "Task Description is too Long"
        else -> null
    }

    var deleteDialogOpen by remember { mutableStateOf(false) }

    var datePickerDialogOpen by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    var bottomSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    DeleteDialog(
        isDialogOpen = deleteDialogOpen,
        bodyText = "Are you sure you want to Delete Tasks?" + "This action can't be undone",
        title = "Delete Tasks?",
        onDismissRequest = { deleteDialogOpen = false },
        onConfirmButtonClick = {
            deleteDialogOpen = false
                               }
    )

    TaskDatePicker(
        state = datePickerState,
        isOpen = datePickerDialogOpen,
        onDismissRequest = { datePickerDialogOpen = false },
        onConfirmButtonClick = {
            datePickerDialogOpen = false
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
        topBar = {
            TopAppBar(
                isTaskExits = true,
                isCompleted = false,
                checkBoxBorderColor = Color.Red,
                onDeleteButtonClick = {
                    deleteDialogOpen = true
                },
                onBackButtonCLick = { navController.popBackStack() },
                onCheckBoxClick = {  }
            )
        }
    ) { paddingValue ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValue.calculateTopPadding())
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it},
                label = {
                    Text(text = "Title")
                },
                singleLine = true,
                isError = isTitleError != null && title.isNotBlank(),
                supportingText = {Text(text = isTitleError.orEmpty())}
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
                label = {
                    Text(text = "Description")
                },
                isError = isDescriptionError != null && description.isNotBlank(),
                supportingText = {
                    Text(text = isDescriptionError.orEmpty())
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Due Date", style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = datePickerState.selectedDateMillis.toDateFormat(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontStyle = FontStyle.Italic
                )

                IconButton(onClick = { datePickerDialogOpen = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date Range"
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Priority", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Priority.entries.forEach { priority ->
                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgroundColor = priority.color ,
                        labelColor = if (priority == Priority.MEDIUM) Color.White else Color.White.copy(alpha = 0.7f) ,
                        borderColor = if (priority == Priority.MEDIUM) Color.White else Color.Transparent,
                        onClick = {  }
                    )
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Related to Subject", style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = sheetState.currentValue.name,
                    style = MaterialTheme.typography.bodyLarge
                )

                IconButton(onClick = { bottomSheetOpen = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "DropDown Icon"
                    )
                }
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CustomBlue,
                    contentColor = Color.White
                ),
                enabled = isTitleError == null && isDescriptionError == null
            ) {
                Text(
                    text = "Save Task",
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    isTaskExits: Boolean,
    isCompleted: Boolean,
    checkBoxBorderColor: Color,
    onDeleteButtonClick: () -> Unit,
    onBackButtonCLick: () -> Unit,
    onCheckBoxClick: () -> Unit
) {

    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onBackButtonCLick() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigation icon"
                )
            }
        },
        title = { Text(text = "Task", style = MaterialTheme.typography.headlineMedium) },
        actions = {
            if (isTaskExits){
                TaskCheckBox(
                    isCompleted = isCompleted,
                    borderColor = checkBoxBorderColor,
                    onCheckBoxClick = onCheckBoxClick
                )

                IconButton(onClick = onDeleteButtonClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete icon"
                    )
                }

            }
        }
    )

}


@Composable
fun PriorityButton(
    modifier: Modifier = Modifier,
    label: String,
    backgroundColor: Color,
    labelColor: Color,
    borderColor: Color,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(5.dp)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp))
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = labelColor)
    }

}


//@Preview
//@Composable
//fun Pre() {
//    TaskScreen()
//}