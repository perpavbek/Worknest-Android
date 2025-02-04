package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class Team {
    @SerializedName("team_id")
    @Expose
    var taskId: Int? = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0

    @SerializedName("lead")
    @Expose
    var lead: User? = null

    @SerializedName("members")
    @Expose
    var members: MutableList<Int>? = mutableListOf()

    @Expose
    @SerializedName("created_at")
    var createdAt: LocalDate? = null
}