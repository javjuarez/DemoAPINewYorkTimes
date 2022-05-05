package com.example.demoapinewyorktimes

import com.google.gson.annotations.SerializedName

data class Media(
    @SerializedName("media-metadata") var mediaMetadata: List<MediaMetadata>
)