package com.shannan.azureair.features.splash

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.shannan.azureair.BuildConfig
import com.shannan.azureair.core.Constants
import com.shannan.azureair.core.platform.BaseViewModel
import com.shannan.azureair.domain.exception.Failure
import com.shannan.azureair.domain.interactor.UseCase
import com.shannan.azureair.domain.interactor.cacheuser.CacheUserUseCase
import com.shannan.azureair.domain.interactor.getcacheduser.GetCachedUserUseCase
import com.shannan.azureair.domain.interactor.login.LoginUseCase
import com.shannan.azureair.domain.interactor.models.User
import javax.inject.Inject

class SplashViewModel
@Inject constructor(private val checkCachedKeyUseCase: GetCachedUserUseCase,
                    private val loginUseCase: LoginUseCase,
                    private val cachedUserUseCase: CacheUserUseCase) : BaseViewModel() {

    var progress = MutableLiveData<Int>()
    var cachedUser: MutableLiveData<User> = MutableLiveData()

    fun isUserCached() = run {
        progress.value = View.VISIBLE
        checkCachedKeyUseCase(GetCachedUserUseCase.Params(UseCase.None()))
        { it.either(::authenticate, ::setUserLiveDataValue) }
    }

    private fun authenticate(failure: Failure) = run {
        progress.value = View.VISIBLE
        loginUseCase(LoginUseCase.Params(BuildConfig.LH_CLIENT_ID, BuildConfig.LH_CLIENT_SECRET, Constants.LH_GRANT_TYPE))
        { it.either(::handleFailure, ::cacheUser) }
    }

    private fun cacheUser(user: User) {
        cachedUserUseCase(CacheUserUseCase.Params(user)) { it.either(::handleFailure, ::setUserLiveDataValue) }
    }

    private fun setUserLiveDataValue(user: User) {
        progress.value = View.INVISIBLE
        this.cachedUser.value = user
    }
}