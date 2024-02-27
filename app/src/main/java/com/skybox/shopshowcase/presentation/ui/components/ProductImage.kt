package com.skybox.shopshowcase.presentation.ui.components

import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.RequestBuilderTransform
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.signature.ObjectKey
import com.skybox.shopshowcase.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductImage(
    url: String,
    modifier: Modifier = Modifier, signature: ObjectKey = ObjectKey(url),
    preloadRequest: RequestBuilder<Drawable>? = null,
    contentDescription: String = "",
    requestBuilderTransform: RequestBuilderTransform<Drawable> = { it },
) {

    GlideImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.FillBounds,
        loading = placeholder(R.drawable.loading_drawable),
        modifier = modifier,
    ) {
        var request = it

        if (preloadRequest != null) {
            request = request
                .thumbnail(
                    preloadRequest
                )
        }
        requestBuilderTransform(request.signature(signature))
    }
}