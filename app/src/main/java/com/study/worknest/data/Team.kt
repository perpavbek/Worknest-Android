package com.study.worknest.data

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate


class Team constructor(
    @SerializedName("team_id")
    @Expose
    var teamId: Int? = 0,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0,

    @SerializedName("lead")
    @Expose
    var lead: User? = null,

    @SerializedName("members")
    @Expose
    var members: MutableList<Int>? = mutableListOf(),

    @SerializedName("created_at")
    @Expose
    var createdAt: LocalDate? = null
) {
    class Builder {
        private var teamId: Int? = 0
        private var name: String? = null
        private var projectId: Int? = 0
        private var lead: User? = null
        private var members: MutableList<Int>? = mutableListOf()
        private var createdAt: LocalDate? = null

        fun teamId(teamId: Int?) = apply { this.teamId = teamId }
        fun name(name: String?) = apply { this.name = name }
        fun projectId(projectId: Int?) = apply { this.projectId = projectId }
        fun lead(lead: User?) = apply { this.lead = lead }
        fun members(members: MutableList<Int>?) = apply { this.members = members }
        fun createdAt(createdAt: LocalDate?) = apply { this.createdAt = createdAt }

        fun build() = Team(teamId, name, projectId, lead, members, createdAt)
    }
}