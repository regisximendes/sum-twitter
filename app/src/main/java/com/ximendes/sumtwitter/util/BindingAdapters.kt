package com.ximendes.sumtwitter.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.ximendes.sumtwitter.R

object BindingAdapters {

    @BindingAdapter("loadingVisibility")
    @JvmStatic
    fun loadingVisibility(progressBar: ProgressBar, visibility: MutableLiveData<Boolean>) {
        progressBar.visibility = if (visibility.value ?: return) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    @BindingAdapter("imageCircleUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String?) {
        Glide.with(imageView.context.applicationContext)
            .load(imageUrl.orEmpty())
            .transition(
                DrawableTransitionOptions.withCrossFade(
                    DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(
                        true
                    ).build()
                )
            )
            .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_user_placeholder))
            .into(imageView)
    }
}
