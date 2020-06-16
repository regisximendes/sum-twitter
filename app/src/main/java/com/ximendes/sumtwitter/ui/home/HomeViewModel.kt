package com.ximendes.sumtwitter.ui.home

import androidx.lifecycle.ViewModel
import com.ximendes.sumtwitter.data.repository.home.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(val repository: HomeRepository) : ViewModel() {

    init {
        getUserTimeline()
    }

   private fun getUserTimeline(){
       val disposable = repository.getUserTimeline()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
           .subscribe({
               val a = it
               val b = a

           }, {

               handleError()
           })
    }

   private fun handleError(){

    }
}
