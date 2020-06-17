package com.ximendes.sumtwitter.data.mapper

import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.data.response.TweetResponse

fun TweetResponse.toTweet(): Tweet {
    return Tweet(
        fullName = user?.name.orEmpty(),
        userName = "@${user?.screenName.orEmpty()}",
        description = text,
        profileImageUrl = user?.profileImageUrl.orEmpty()
    )
}

fun List<TweetResponse>.toTweetList(): List<Tweet> {
    return map { it.toTweet() }
}