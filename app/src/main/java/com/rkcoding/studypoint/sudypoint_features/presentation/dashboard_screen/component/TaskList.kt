package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkcoding.studypoint.R
import com.rkcoding.studypoint.core.utils.Priority
import com.rkcoding.studypoint.core.utils.toDateFormat
import com.rkcoding.studypoint.sudypoint_features.domain.model.Task


@RequiresApi(Build.VERSION_CODES.O)
fun LazyListScope.taskList(
    sectionTitle: String,
    task: List<Task>,
    onTaskCardClick: (Task) -> Unit,
    onTaskCheckBoxClick: (Task) -> Unit
){
    item {

        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(12.dp)
        )
    }

    if (task.isEmpty()){
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.tasks),
                    contentDescription = "Books"
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "You don't have any Upcoming Tasks. \n Click the + Icon to Add Tasks",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }
    }

    items(task){ tasks ->
        TaskCardSection(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            task = tasks,
            onCardClick = { onTaskCardClick(tasks) },
            onCheckBoxClick = { onTaskCheckBoxClick(tasks) }
        )
    }


}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskCardSection(
    modifier: Modifier = Modifier,
    task: Task,
    onCardClick: () -> Unit,
    onCheckBoxClick: () -> Unit
) {

    ElevatedCard(
        modifier = modifier
            .clickable { onCardClick() }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TaskCheckBox(
                isCompleted = task.isCompleted,
                borderColor = Priority.fromInt(value = task.priority).color,
                onCheckBoxClick = { onCheckBoxClick() }
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = task.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isCompleted){
                        TextDecoration.LineThrough
                    } else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = task.description,
                    maxLines = 1,
                    fontWeight = FontWeight.Light,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.isCompleted){
                        TextDecoration.LineThrough
                    } else TextDecoration.None
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = task.dueDate.toDateFormat(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

        }

    }

}


@Composable
fun TaskCheckBox(
    isCompleted: Boolean,
    borderColor: Color,
    onCheckBoxClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .size(25.dp)
            .clip(CircleShape)
            .border(2.dp, borderColor, CircleShape)
            .clickable {
                onCheckBoxClick()
            },
        contentAlignment = Alignment.Center
    ){

        AnimatedVisibility(visible = isCompleted) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "check",
                tint = Color.Green
            )
        }

    }

}
