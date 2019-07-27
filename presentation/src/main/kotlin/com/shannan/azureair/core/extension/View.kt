
package com.shannan.azureair.core.extension

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String) =
        Glide.with(this.context.applicationContext)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(this)!!

fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: FragmentActivity) {
    val target: Target<Drawable> = ImageViewBaseTarget(this, activity)
    Glide.with(context.applicationContext).load(url).into(target)
}

private class ImageViewBaseTarget (var imageView: ImageView?, var activity: FragmentActivity?) : BaseTarget<Drawable>() {
    override fun removeCallback(cb: SizeReadyCallback?) {
        imageView = null
        activity = null
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>) {
        imageView?.setImageDrawable(resource)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun getSize(cb: SizeReadyCallback) = cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)
}

fun TextView.copyTextToClipBoard() {
    val clipboardManager: ClipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("asdsdas", text)
    clipboardManager.primaryClip = clip
}
