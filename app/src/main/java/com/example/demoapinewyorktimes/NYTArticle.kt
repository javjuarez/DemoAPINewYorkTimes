package com.example.demoapinewyorktimes

data class NYTArticle(
    var title: String,
    var url: String,
    var published_date: String,
    var media: List<Media>
)