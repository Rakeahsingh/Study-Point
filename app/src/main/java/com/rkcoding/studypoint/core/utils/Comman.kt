package com.rkcoding.studypoint.core.utils

import androidx.compose.ui.graphics.Color
import com.rkcoding.studypoint.ui.theme.CustomGreen
import com.rkcoding.studypoint.ui.theme.Orange

enum class Priority(val title: String, val color: Color, val value: Int){
    LOW("Low", CustomGreen, 0),
    MEDIUM("Medium", Orange, 1),
    HIGH("High", Color.Red, 2);

    companion object{
        fun fromInt(value: Int) = entries.firstOrNull() { it.value == value } ?: MEDIUM
    }

}