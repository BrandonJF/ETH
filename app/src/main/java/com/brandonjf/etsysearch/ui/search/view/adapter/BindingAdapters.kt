package com.brandonjf.etsysearch.ui.search.view.adapter

import android.databinding.BindingAdapter
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("setGlideImage")
fun setGlideImage(view: ImageView, uri: String) {
    Glide.with(view.context)
        .load(uri)
        .apply(RequestOptions().placeholder(ColorDrawable(Color.GRAY)))
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(view)
}