package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import com.rkcoding.studypoint.R
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject

@Composable
fun AddSubjectSection(
    modifier: Modifier = Modifier,
    subjectList: List<Subject>,
    addButtonClick: () -> Unit,
    subjectCardClick: (Subject) -> Unit
) {

    Column(modifier = modifier) {

        Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Add Subject",
                fontSize = 18.sp
            )

            IconButton(onClick = { addButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add subjects"
                )
            }

        }

        if (subjectList.isEmpty()){
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.books),
                contentDescription = "Books"
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "You don't have any Subject. \n Click the + Icon to Add Subject",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ){
           items(subjectList){ subject ->
               SubjectCard(
                   subjectName = subject.name,
                   backgroundColor = subject.color.map { Color(it) },
                   onCardClick = { subjectCardClick(subject) }
               )
           }
        }

    }

}


@Composable
fun SubjectCard(
    modifier: Modifier = Modifier,
    subjectName: String,
    backgroundColor: List<Color>,
    onCardClick: () -> Unit
) {

    Box(
        modifier = modifier
            .size(150.dp)
            .background(
                brush = Brush.verticalGradient(backgroundColor),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable {
                onCardClick()
            },
        contentAlignment = Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.books),
                contentDescription = "Books",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subjectName,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}