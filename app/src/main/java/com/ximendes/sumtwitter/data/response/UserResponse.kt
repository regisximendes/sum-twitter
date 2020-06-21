package com.ximendes.sumtwitter.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse(
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("screen_name")
    @Expose
    val screenName: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("profile_image_url_https")
    @Expose
    val profileImageUrl: String
)
