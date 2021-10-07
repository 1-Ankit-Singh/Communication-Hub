package com.project.communicationhub.news

import com.google.gson.annotations.SerializedName

class Articles(
    //@SerializedName("title")
    val title: String,
    //@SerializedName("description")
    val description: String,
    //@SerializedName("content")
    val content: String,
    //@SerializedName("urlToImage")
    val urlToImage: String,
    //@SerializedName("url")
    val url: String
) {
}