package com.bnkk.model

data class TimelinePost(
    val id: String,
    val author: String,
    val handle: String,
    val timeLabel: String,
    val body: String,
    val likes: Int,
    val replies: Int,
    val verified: Boolean = false,
    val mediaLabel: String? = null
)

data class StoryPreview(
    val id: String,
    val name: String,
    val initials: String,
    val isNew: Boolean = true
)

data class ChatPreview(
    val id: String,
    val name: String,
    val initials: String,
    val message: String,
    val time: String,
    val unreadCount: Int = 0,
    val online: Boolean = false
)
