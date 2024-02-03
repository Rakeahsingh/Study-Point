package com.rkcoding.studypoint.sudypoint_features.data.mapper

import com.rkcoding.studypoint.sudypoint_features.data.local.entity.SubjectEntity
import com.rkcoding.studypoint.sudypoint_features.domain.model.Subject

fun SubjectEntity.toSubject(): Subject{
    return Subject(
        name = name,
        goalHours = goalHours,
        color = color,
        subjectId = subjectId,

    )
//    SubjectEntity.subjectCardColor = Subject.subjectCardColor
}


fun Subject.toSubjectEntity(): SubjectEntity{
    return SubjectEntity(
        name = name,
        goalHours = goalHours,
        color = color,
        subjectId = subjectId,
    )
//    Subject.subjectCardColor = SubjectEntity.subjectCardColor
}