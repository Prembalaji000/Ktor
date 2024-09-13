package com.example.ktorprac

import kotlinx.serialization.Serializable

@Serializable
data class PostItem(
    val postId: Int,
    val id: Int,
    val name: String,
    val email : String,
    val body: String
)
