package com.example.demoapinewyorktimes

import com.google.gson.annotations.SerializedName

data class NYTJsonResponse(
    @SerializedName("status") var status: String,
    @SerializedName("copyright") var copyright: String,
    @SerializedName("num_results") var num_results: Number,
    @SerializedName("results") var results: List<NYTArticle>
)
