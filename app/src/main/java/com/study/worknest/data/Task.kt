package com.study.worknest.data

import java.time.LocalDate

data class Task(
    var taskId: Int?,
    var name: String,
    var projectId: Int,
    var description: String,
    var status: String,
    var priority: String,
    var deadline: LocalDate,
    var assignedTo: Int,
    var teamId: Int
)