package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Role {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0

    @SerializedName("team_id")
    @Expose
    var teamId: Int? = 0
}