package com.skybox.shopshowcase.presentation.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.signature.ObjectKey

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductImage(
    url: String,
    signature: ObjectKey,
    preloadRequest: RequestBuilder<Drawable>,
    modifier: Modifier = Modifier,
    contentDescription: String = ""
) {
    GlideImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
    ) {
        it
            .thumbnail(
                preloadRequest
            )
            .signature(signature)
    }
}