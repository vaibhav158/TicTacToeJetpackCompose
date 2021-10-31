package com.vaibhav.core.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasicApiResponse(
    val isSuccessful: Boolean,
    val message: String? = null
)
