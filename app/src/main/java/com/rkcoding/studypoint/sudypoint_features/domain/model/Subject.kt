package com.rkcoding.studypoint.sudypoint_features.domain.model

import androidx.compose.ui.graphics.Color
import com.rkcoding.studypoint.ui.theme.gradient1
import com.rkcoding.studypoint.ui.theme.gradient2
import com.rkcoding.studypoint.ui.theme.gradient3
import com.rkcoding.studypoint.ui.theme.gradient4
import com.rkcoding.studypoint.ui.theme.gradient5

data class Subject(
    val name: String,
    val goalHours: Float,
    val color: List<Color>
){

    companion object{
        val subjectCardColor = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }

}
