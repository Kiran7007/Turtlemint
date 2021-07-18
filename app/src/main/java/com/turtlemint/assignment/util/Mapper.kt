package com.turtlemint.assignment.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.turtlemint.assignment.R

/**
 * Mapper provides modified data to the view.
 */
object Mapper {

    @JvmStatic
    @BindingAdapter("app:profileImage")
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context)
            .load(imageUrl).apply(RequestOptions().placeholder(R.mipmap.ic_launcher_round).fitCenter())
            .into(view)
    }
}