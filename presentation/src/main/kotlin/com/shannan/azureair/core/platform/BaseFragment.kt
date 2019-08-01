package com.shannan.azureair.core.platform

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.shannan.azureair.AndroidApplication
import com.shannan.azureair.R
import com.shannan.azureair.core.di.ApplicationComponent
import com.shannan.azureair.core.extension.viewContainer
import com.shannan.azureair.core.navigation.Navigator
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events
 * and showing errors and empty states.
 *
 * @see Fragment
 */
abstract class BaseFragment : Fragment() {

    @Inject
    internal lateinit var navigator: Navigator

    private lateinit var fragmentView: View
    private lateinit var progressBar: ProgressBar
    private lateinit var errorView: LinearLayout
    private lateinit var errorHeader: TextView
    private lateinit var errorText: TextView
    private lateinit var errorIcon: ImageView
    private lateinit var errorAction: AppCompatButton
    private lateinit var progressDialog: AlertDialog

    abstract fun layoutId(): Int
    abstract fun afterInflation(savedInstanceState: Bundle?)

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.layout_base, container, false)
        val baseView: ViewGroup = view.findViewById(R.id.layout_base_view)
        progressBar = view.findViewById(R.id.layout_progress_bar)
        errorView = view.findViewById(R.id.layout_error_view)
        errorHeader = view.findViewById(R.id.layout_error_header)
        errorText = view.findViewById(R.id.layout_error_text)
        errorIcon = view.findViewById(R.id.layout_error_image)
        errorAction = view.findViewById(R.id.layout_error_action)
        fragmentView = LayoutInflater.from(activity).inflate(layoutId(), container, false)
        baseView.addView(fragmentView)
        initProgressDialog()
        afterInflation(savedInstanceState)
        return view

    }

    private fun initProgressDialog() {
        val builder = AlertDialog.Builder(activity as Context)
        builder.setView(R.layout.layout_progress_dialog)
        progressDialog = builder.create()
    }

    open fun onBackPressed() {}

    internal fun notify(@StringRes message: Int) =
            Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    internal fun setLoading(isLoading: Boolean) {
        if (isAdded) {
            errorView.visibility = View.GONE
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
                fragmentView.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                fragmentView.visibility = View.VISIBLE
            }
        }
    }

    internal fun setError(@StringRes errorTextRes: Int,
                          @StringRes errorActionRes: Int,
                          @DrawableRes errorIcon: Int,
                          @Nullable errorActionListener: View.OnClickListener) {
        if (isAdded) {
            val errorActionString = if (errorActionRes == 0) null else resources.getString(errorActionRes)
            val errorActionIcon = if (errorIcon == 0) null else ContextCompat.getDrawable(activity as Context, errorIcon)
            setError(resources.getString(errorTextRes),
                    errorActionString,
                    errorActionIcon,
                    errorActionListener)
        }
    }

    internal fun setError(textString: String, @Nullable actionString: String?, icon: Drawable?, @Nullable actionListener: View.OnClickListener) {
        if (isAdded) {
            progressBar.visibility = View.GONE
            fragmentView.visibility = View.GONE
            errorView.visibility = View.VISIBLE

            if (actionString != null) {
                errorAction.setOnClickListener(actionListener)
                errorAction.visibility = View.VISIBLE
            }
            if (icon != null) {
                errorIcon.setImageDrawable(icon)
            }
            errorAction.text = actionString
            errorText.text = textString
        }
    }

    internal fun setError(@StringRes errorHeaderRes: Int,
                          @StringRes errorTextRes: Int,
                          @StringRes errorActionRes: Int,
                          @DrawableRes errorIcon: Int,
                          @Nullable errorActionListener: View.OnClickListener) {
        if (isAdded) {
            val errorActionString = if (errorActionRes == 0) null else resources.getString(errorActionRes)
            val errorActionIcon = if (errorIcon == 0) null else ContextCompat.getDrawable(activity as Context, errorIcon)
            setError(resources.getString(errorHeaderRes), resources.getString(errorTextRes),
                    errorActionString,
                    errorActionIcon,
                    errorActionListener)
        }
    }

    internal fun setError(headerString: String, textString: String, @Nullable actionString: String?, icon: Drawable?, @Nullable actionListener: View.OnClickListener) {
        if (isAdded) {
            progressBar.visibility = View.GONE
            fragmentView.visibility = View.GONE
            errorView.visibility = View.VISIBLE

            if (actionString != null) {
                errorAction.setOnClickListener(actionListener)
                errorAction.visibility = View.VISIBLE
            }
            if (icon != null) {
                errorIcon.setImageDrawable(icon)
            }
            errorAction.text = actionString
            errorHeader.text = headerString
            errorText.text = textString
        }
    }

    internal fun setViewAfterErrorShown() {
        progressBar.visibility = View.GONE
        fragmentView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    internal fun restoreView() {
        progressBar.visibility = View.GONE
        fragmentView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
    }

    internal fun setErrorDialog(@StringRes message: Int, @StringRes positive: Int, @StringRes negative: Int, positiveClickListener: () -> Unit, negativeClickListener: () -> Unit) {
        if (isAdded) {
            val errorMessage = resources.getString(message)
            val errorPositive = if (positive == 0) null else resources.getString(positive)
            val errorNegative = if (negative == 0) null else resources.getString(negative)
            setErrorDialog(errorMessage, errorPositive, errorNegative, positiveClickListener, negativeClickListener)
        }
    }


    fun setErrorDialog(message: String, positive: String?, negative: String?, positiveClickListener: () -> Unit, negativeClickListener: () -> Unit) {
        if (isAdded) {
            val errorDialog = AlertDialog.Builder(activity as Context)
            errorDialog.setCancelable(false)
            errorDialog.setMessage(message)

            // Set a positive button and its click listener on alert dialog
            errorDialog.setPositiveButton(positive) { _, _ ->
                positiveClickListener()
            }

            // Display a negative button on alert dialog
            errorDialog.setNegativeButton(negative) { _, _ ->
                negativeClickListener()
            }
            val dialog = errorDialog.create()
            if (!dialog.isShowing) {
                errorDialog.create().show()
            }
        }
    }

    internal fun showProgressDialog(@StringRes message: Int) {
        if (isAdded && !activity!!.isFinishing) {
            progressDialog.setMessage(getString(message))
            progressDialog.show()
        }
    }

    internal fun hideProgressDialog() {
        if (isAdded && !activity?.isFinishing!! && isProgressDialogShowing()) {
            progressDialog.dismiss()
        }
    }

    private fun isProgressDialogShowing(): Boolean {
        return progressDialog.isShowing
    }

}
