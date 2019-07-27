package com.shannan.azureair.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import com.shannan.azureair.R
import com.shannan.azureair.R.id
import com.shannan.azureair.R.layout
import com.shannan.azureair.core.extension.inTransaction
import com.shannan.azureair.core.navigation.Navigator
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Base Activity class with helper methods for handling fragment transactions and back button
 * events.
 *
 * @see AppCompatActivity
 */
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    internal lateinit var navigator: Navigator

    private lateinit var activityView: ViewGroup
    private lateinit var baseView: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var errorView: LinearLayout
    private lateinit var errorHeader: TextView
    private lateinit var errorText: TextView
    private lateinit var errorIcon: ImageView
    private lateinit var errorAction: AppCompatButton
    private lateinit var toolbarView: Toolbar
    private lateinit var progressDialog: AlertDialog

    abstract fun layoutId(): Int

    abstract fun afterInflation(savedInstanceState: Bundle?)

    abstract fun getToolbarTitleResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_base)
        baseView = findViewById(id.layout_base_view)
        progressBar = findViewById(id.layout_progress_bar)
        errorView = findViewById(id.layout_error_view)
        errorHeader = findViewById(R.id.layout_error_header)
        errorText = findViewById(R.id.layout_error_text)
        errorIcon = findViewById(R.id.layout_error_image)
        errorAction = findViewById(R.id.layout_error_action)
        toolbarView = findViewById(R.id.toolbar)

        activityView = LayoutInflater.from(this).inflate(layoutId(), baseView, false) as ViewGroup
        baseView.addView(activityView)
        initProgressDialog()
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            if (getToolbarTitleResource() !== 0) {
                supportActionBar?.setTitle(getToolbarTitleResource())
            } else {
                supportActionBar?.hide()
            }
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
        afterInflation(savedInstanceState)
    }

    private fun initProgressDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setView(layout.layout_progress_dialog)
        progressDialog = builder.create()
    }

    override fun onBackPressed() {
        (supportFragmentManager.findFragmentById(
                id.fragmentContainer) as BaseFragment).onBackPressed()
        super.onBackPressed()
    }

    internal fun addFragment(savedInstanceState: Bundle?) =
            savedInstanceState ?: supportFragmentManager.inTransaction {
                add(id.fragmentContainer, fragment())
            }

    abstract fun fragment(): BaseFragment
}
