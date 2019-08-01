package com.shannan.azureair.features.splash

import android.os.Bundle
import com.shannan.azureair.R
import com.shannan.azureair.core.platform.BaseActivity
import com.shannan.azureair.core.platform.BaseFragment

class SplashActivity : BaseActivity() {

    override fun getToolbarTitleResource() = 0

    override fun layoutId(): Int = R.layout.activity_layout

    override fun fragment(): BaseFragment = SplashFragment.newInstance()

    override fun afterInflation(savedInstanceState: Bundle?) {
        addFragment(savedInstanceState)
    }
}