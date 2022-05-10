package com.pim.feature

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class AppViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}