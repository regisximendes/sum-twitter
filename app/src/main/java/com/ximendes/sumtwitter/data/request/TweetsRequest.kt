package com.ximendes.sumtwitter.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TweetsRequest(
    @SerializedName("token")
    @Expose
    val token: String,

    @SerializedName("secret")
    @Expose
    val secret: String
)
