package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class Project private constructor(
    @SerializedName("project_id")
    @Expose
    var projectId: Int? = 0,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,

    @SerializedName("manager")
    @Expose
    var manager: User? = null,

    @SerializedName("created_at")
    @Expose
    var createdAt: LocalDate? = null
) {
    class Builder {
        private var projectId: Int? = 0
        private var name: String? = null
        private var description: String? = null
        private var manager: User? = null
        private var createdAt: LocalDate? = null

        fun projectId(projectId: Int?) = apply { this.projectId = projectId }
        fun name(name: String?) = apply { this.name = name }
        fun description(description: String?) = apply { this.description = description }
        fun manager(manager: User?) = apply { this.manager = manager }
        fun createdAt(createdAt: LocalDate?) = apply { this.createdAt = createdAt }

        fun build() = Project(projectId, name, description, manager, createdAt)
    }
}