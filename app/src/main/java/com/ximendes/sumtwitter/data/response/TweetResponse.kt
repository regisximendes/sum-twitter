package com.ximendes.sumtwitter.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TweetResponse(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("text")
    @Expose
    val text: String,

    @SerializedName("user")
    @Expose
    var user: UserResponse? = null
)
