package com.ximendes.sumtwitter.util.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseTimeLineViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()
    val isLoading = MutableLiveData<Boolean>().apply { value = false }

    protected fun showProgressBar() = isLoading.postValue(true)

    protected fun hideProgressBar() = isLoading.postValue(false)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
