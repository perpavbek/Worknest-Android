package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Role private constructor(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0,

    @SerializedName("team_id")
    @Expose
    var teamId: Int? = 0
) {
    class Builder {
        private var name: String? = null
        private var projectId: Int? = 0
        private var teamId: Int? = 0

        fun name(name: String?) = apply { this.name = name }
        fun projectId(projectId: Int?) = apply { this.projectId = projectId }
        fun teamId(teamId: Int?) = apply { this.teamId = teamId }

        fun build() = Role(name, projectId, teamId)
    }
}
