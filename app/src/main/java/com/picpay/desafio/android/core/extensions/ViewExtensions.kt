package com.picpay.desafio.android.core.extensions

import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE

fun View.visible() {
    visibility = VISIBLE
}

fun View.gone() {
    visibility = GONE
}

fun View.invisible() {
    visibility = INVISIBLE
}
