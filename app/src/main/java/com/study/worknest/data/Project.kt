package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class Project {
    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("manager")
    @Expose
    var manager: User? = null

    @Expose
    @SerializedName("created_at")
    var createdAt: LocalDate? = null
}