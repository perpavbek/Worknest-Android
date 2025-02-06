package com.study.worknest.data.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

class TeamRequest private constructor(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("project_id")
    @Expose
    var projectId: Int? = null,

    @SerializedName("lead")
    @Expose
    var lead: Int? = null,

    @SerializedName("members")
    @Expose
    var members: MutableList<Int> = mutableListOf(),

) {
    class Builder {
        private var name: String? = null
        private var projectId: Int? = null
        private var lead: Int? = null
        private var members: MutableList<Int> = mutableListOf()

        fun name(name: String?) = apply { this.name = name }
        fun projectId(projectId: Int?) = apply { this.projectId = projectId }
        fun lead(lead: Int?) = apply { this.lead = lead }
        fun members(members: MutableList<Int>) = apply { this.members = members}
        fun addMember(member: Int) = apply { this.members.add(member) }
        fun isFilled(): Boolean{
            return (name != null && projectId != null && lead != null && members.size >= 2)
        }
        fun build() = TeamRequest(name, projectId, lead, members)
    }
}