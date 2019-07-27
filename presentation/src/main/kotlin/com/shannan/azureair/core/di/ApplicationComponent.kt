
package com.shannan.azureair.core.di

import com.shannan.azureair.AndroidApplication
import com.shannan.azureair.core.di.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)

}
