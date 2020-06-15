package com.ximendes.sumtwitter.util.resourceprovider

import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes resId: Int): String
}