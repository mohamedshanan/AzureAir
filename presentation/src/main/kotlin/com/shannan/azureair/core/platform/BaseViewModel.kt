
package com.shannan.azureair.core.platform


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shannan.azureair.domain.exception.Failure

/**
 * Base ViewModel class with default Failure handling.
 * @see ViewModel
 * @see Failure
 */
abstract class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Failure> = MutableLiveData()

    protected fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }
}