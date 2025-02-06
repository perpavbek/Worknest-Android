package com.study.worknest.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User private constructor(
    @SerializedName("user_id")
    @Expose
    var userId: Int? = 0,

    @SerializedName("username")
    @Expose
    var username: String? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String? = null,

    @SerializedName("roles")
    @Expose
    var roles: MutableList<Role>? = mutableListOf()
) {
    class Builder {
        private var userId: Int? = 0
        private var username: String? = null
        private var email: String? = null
        private var phoneNumber: String? = null
        private var roles: MutableList<Role>? = mutableListOf()

        fun userId(userId: Int?) = apply { this.userId = userId }
        fun username(username: String?) = apply { this.username = username }
        fun email(email: String?) = apply { this.email = email }
        fun phoneNumber(phoneNumber: String?) = apply { this.phoneNumber = phoneNumber }
        fun roles(roles: MutableList<Role>?) = apply { this.roles = roles }

        fun build() = User(userId, username, email, phoneNumber, roles)
    }
}
