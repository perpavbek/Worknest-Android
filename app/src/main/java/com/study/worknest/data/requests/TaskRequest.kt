package com.study.worknest.data.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class TaskRequest private constructor(

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = null,

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
    var assignedTo: Int? = null,

    @SerializedName("team_id")
    @Expose
    var teamId: Int? = null,

) {
    class Builder {
        private var name: String? = null
        private var projectId: Int? = null
        private var description: String? = null
        private var status: String? = null
        private var priority: String? = null
        private var deadline: LocalDate? = null
        private var assignedTo: Int? = null
        private var teamId: Int? = null

        fun name(name: String?) = apply { this.name = name }
        fun projectId(projectId: Int?) = apply { this.projectId = projectId }
        fun description(description: String?) = apply { this.description = description }
        fun status(status: String?) = apply { this.status = status }
        fun priority(priority: String?) = apply { this.priority = priority }
        fun deadline(deadline: LocalDate?) = apply { this.deadline = deadline }
        fun assignedTo(assignedTo: Int?) = apply { this.assignedTo = assignedTo }
        fun teamId(teamId: Int?) = apply { this.teamId = teamId }

        fun isFilled(): Boolean{
            return name != null && projectId != null && description != null && status != null && priority != null && deadline != null && assignedTo != null && teamId != null
        }
        fun build() = TaskRequest(name, projectId, description, status, priority, deadline, assignedTo, teamId)
    }
}