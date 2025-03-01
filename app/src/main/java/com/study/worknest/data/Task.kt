package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class Task private constructor(
    @SerializedName("task_id")
    @Expose
    var taskId: Int? = 0,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("priority")
    @Expose
    var priority: String? = null,

    @SerializedName("deadline")
    @Expose
    var deadline: LocalDate? = null,

    @SerializedName("assigned_to")
    @Expose
    var assignedTo: Int? = 0,

    @SerializedName("team_id")
    @Expose
    var teamId: Int? = 0,

    @SerializedName("created_at")
    @Expose
    var createdAt: LocalDate? = null
) {
    class Builder {
        private var taskId: Int? = 0
        private var name: String? = null
        private var projectId: Int? = 0
        private var description: String? = null
        private var status: String? = null
        private var priority: String? = null
        private var deadline: LocalDate? = null
        private var assignedTo: Int? = 0
        private var teamId: Int? = 0
        private var createdAt: LocalDate? = null

        fun taskId(taskId: Int?) = apply { this.taskId = taskId }
        fun name(name: String?) = apply { this.name = name }
        fun projectId(projectId: Int?) = apply { this.projectId = projectId }
        fun description(description: String?) = apply { this.description = description }
        fun status(status: String?) = apply { this.status = status }
        fun priority(priority: String?) = apply { this.priority = priority }
        fun deadline(deadline: LocalDate?) = apply { this.deadline = deadline }
        fun assignedTo(assignedTo: Int?) = apply { this.assignedTo = assignedTo }
        fun teamId(teamId: Int?) = apply { this.teamId = teamId }
        fun createdAt(createdAt: LocalDate?) = apply { this.createdAt = createdAt }

        fun build() = Task(taskId, name, projectId, description, status, priority, deadline, assignedTo, teamId, createdAt)
    }
}