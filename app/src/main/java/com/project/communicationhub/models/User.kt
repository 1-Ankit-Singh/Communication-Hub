package com.project.communicationhub.models

data class User(
    val name: String,
    val imageUrl: String,
    val thumbImage: String,
    val uid: String,
    val deviceToken: String,
    val status: String,
    val online: Boolean
) {
    //Empty [Constructor] for Firebase
    constructor() : this("", "", "", "", "", "Hey There, I am using CLIQUE", false)


    constructor(name: String, imageUrl: String, thumbImage: String, uid: String) :
            this(name, imageUrl, thumbImage, uid = uid, "", status = "Hey There, I am using CLIQUE", online = false)

}