package com.rkcoding.studypoint.sudypoint_features.data.local.entity

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rkcoding.studypoint.ui.theme.gradient1
import com.rkcoding.studypoint.ui.theme.gradient2
import com.rkcoding.studypoint.ui.theme.gradient3
import com.rkcoding.studypoint.ui.theme.gradient4
import com.rkcoding.studypoint.ui.theme.gradient5

@Entity
data class SubjectEntity(
    val name: String,
    val goalHours: Float,
    val color: List<Color>,
    @PrimaryKey(autoGenerate = true)
    val subjectId: Int? = null
)
{
    companion object{
        val subjectCardColor = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
