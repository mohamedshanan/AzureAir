package com.shannan.azureair.core.extension

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.shannan.azureair.core.platform.DialogHelper

inline fun Activity.showAlertDialog(func: DialogHelper.() -> Unit): AlertDialog =
        DialogHelper(this).apply {
            func()
        }.create()
