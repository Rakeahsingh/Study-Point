package com.rkcoding.studypoint.sudypoint_features.presentation.dashboard_screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkcoding.studypoint.R
import com.rkcoding.studypoint.sudypoint_features.domain.model.Session

fun LazyListScope.sessionList(
    sectionTitle: String,
    session: List<Session>,
    onDeleteIconClick: (Session) -> Unit,
    onCardClick: (Int?) -> Unit
){
    item {

        Text(
            text = sectionTitle,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 10.dp, top = 16.dp, bottom = 10.dp)
        )
    }

    if (session.isEmpty()){
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.lamp),
                    contentDescription = "Books"
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "You don't have any Study Sessions. \n Click the + Icon to Add Sessions",
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }

        }
    }

    items(session){ sessions->
        SessionCardSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 4.dp),
            session = sessions,
            onCardClick = { onCardClick(sessions.sessionId) },
            onDeleteIconClick = { onDeleteIconClick(sessions) }
        )
    }


}


@Composable
fun SessionCardSection(
    modifier: Modifier = Modifier,
    session: Session,
    onCardClick: () -> Unit,
    onDeleteIconClick: () -> Unit
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
            Column {
                Text(
                    text = session.relatedToSubject,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "${session.date}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${session.duration} hr",
                style = MaterialTheme.typography.bodyMedium
            )
            IconButton(onClick = { onDeleteIconClick() }
            ) {
               Icon(
                   imageVector = Icons.Default.Delete,
                   contentDescription = "Delete session"
               )
            }

        }

    }

}