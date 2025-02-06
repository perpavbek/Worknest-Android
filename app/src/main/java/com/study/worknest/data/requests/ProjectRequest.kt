package com.study.worknest.data.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProjectRequest private constructor(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("description")
    @Expose
    var description: String? = null,
) {
    class Builder {
        private var name: String? = null
        private var description: String? = null

        fun name(name: String?) = apply { this.name = name }
        fun description(description: String?) = apply { this.description = description }

        fun isFilled(): Boolean{
            return name != null && description != null
        }

        fun build() = ProjectRequest(name, description)
    }
}