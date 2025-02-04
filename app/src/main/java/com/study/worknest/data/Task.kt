package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.util.Date

class Task {
    @SerializedName("task_id")
    @Expose
    var taskId: Int? = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("priority")
    @Expose
    var priority: String? = null

    @SerializedName("deadline")
    @Expose
    var deadline: LocalDate? = null

    @SerializedName("assigned_to")
    @Expose
    var assignedTo: Int? = 0

    @SerializedName("team_id")
    @Expose
    var teamId: Int? = 0

    @Expose
    @SerializedName("created_at")
    var createdAt: LocalDate? = null
}