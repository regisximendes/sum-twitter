package com.ximendes.sumtwitter.data.mapper

import com.ximendes.sumtwitter.data.domain.Tweet
import com.ximendes.sumtwitter.data.response.TweetResponse

fun TweetResponse.toTweet(): Tweet {
    return Tweet(
        fullName = user?.name.orEmpty(),
        userName = "@${user?.screenName.orEmpty()}",
        description = user?.description.orEmpty(),
        profileImageUrl = user?.profileImageUrl.orEmpty(),
        tex = text
    )
}

fun List<TweetResponse>.toTweetList(): List<Tweet> {
    return map { it.toTweet() }
}

fun TweetResponse.toSimpleTweet(): String {
    return text
}

fun List<TweetResponse>.toSimpleTweetList(): List<String> {
    return map { it.toSimpleTweet() }
}
