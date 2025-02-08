package com.study.worknest.data.requests

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SignUpRequest private constructor(
    @SerializedName("username")
    @Expose
    private var username: String? = null,

    @SerializedName("email")
    @Expose
    private var email: String? = null,

    @SerializedName("phone_number")
    @Expose
    private var phoneNumber: String? = null,

    @SerializedName("password")
    @Expose
    private var password: String? = null,

) {
    class Builder {
        private var username: String? = null
        private var email: String? = null
        private var phoneNumber: String? = null
        private var password: String? = null

        fun username(username: String?) = apply { this.username = username }
        fun email(email: String?) = apply { this.email = email }
        fun phoneNumber(phoneNumber: String?) = apply { this.phoneNumber = phoneNumber }
        fun password(password: String?) = apply { this.password = password }

        fun isFilled(): Boolean{
            return username != null && email != null && phoneNumber != null && password != null
        }

        fun build() = SignUpRequest(username, email, phoneNumber, password)
    }
}