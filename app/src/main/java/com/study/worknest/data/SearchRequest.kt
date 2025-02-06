package com.study.worknest.data

import com.google.gson.annotations.SerializedName

data class SearchRequest (
    @SerializedName("prompt")
    var prompt: String? = ""
)