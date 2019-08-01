package com.shannan.azureair.features.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.shannan.azureair.R
import com.shannan.azureair.core.extension.failure
import com.shannan.azureair.core.extension.observe
import com.shannan.azureair.core.extension.viewModel
import com.shannan.azureair.core.platform.BaseFragment
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.interactor.models.User
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : BaseFragment() {

    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var splashViewModel: SplashViewModel

    override fun layoutId() = R.layout.fragment_splash

    override fun afterInflation(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        splashViewModel = viewModel(viewModelFactory) {
            failure(failure, ::handleFailure)
            observe<User, LiveData<User>>(cachedUser) {
                if (it != null) {
                    onSuccess()
                } else {
                    handleFailure(Failure.ServerError)
                }
            }
            observe<Int, LiveData<Int>>(progress) { progressBar.visibility = it!! }
            isUserCached()
        }
    }

    private fun onSuccess() {
        setErrorDialog("Success", "OK", "CANCEL", {}, {})
    }

    private fun handleFailure(failure: Failure?) {
        splashViewModel.progress.value = View.INVISIBLE
        setErrorDialog("Failure $failure", "OK", "CANCEL", {}, {})
//        activity?.finish()
    }
}