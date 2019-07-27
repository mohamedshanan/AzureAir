package com.shannan.azureair.core.extension

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.shannan.azureair.core.platform.BaseActivity
import com.shannan.azureair.core.platform.BaseFragment
import com.shannan.azureair.core.platform.DialogHelper
import kotlinx.android.synthetic.main.activity_layout.*

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun BaseFragment.close() = fragmentManager?.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

inline fun Fragment.showAlertDialog(func: DialogHelper.() -> Unit): AlertDialog =
        DialogHelper(this.context!!).apply {
            func()
        }.create()

inline fun Fragment.showConfirmationDialog(title: String, message: String, positiveText: String, negativeText: String,
                                           crossinline positiveClickListener: () -> Unit,
                                           crossinline negativeClickListener: () -> Unit): AlertDialog {
    // Initialize a new instance of
    val builder = AlertDialog.Builder(activity as Context)

    // Set the alert dialog title
    builder.setTitle(title)

    // Display a message on alert dialog
    builder.setMessage(message)

    // Set a positive button and its click listener on alert dialog
    builder.setPositiveButton(positiveText) { _, _ ->
        positiveClickListener()
    }

    // Display a negative button on alert dialog
    builder.setNegativeButton(negativeText) { _, _ ->
        negativeClickListener()
    }

    // Finally, make the alert dialog using builder
    return builder.create()
}

inline fun Fragment.showConfirmationDialog(@StringRes title: Int,
                                           @StringRes message: Int,
                                           @StringRes positiveText: Int,
                                           @StringRes negativeText: Int,
                                           crossinline positiveClickListener: () -> Unit,
                                           crossinline negativeClickListener: () -> Unit): AlertDialog {

    return showConfirmationDialog(resources.getString(title), resources.getString(message), resources.getString(positiveText), resources.getString(negativeText),
            positiveClickListener, negativeClickListener)
}