package com.turtlemint.assignment.data.db.entity

import androidx.room.*
import com.squareup.moshi.Json
import com.turtlemint.assignment.util.DateUtil
import com.turtlemint.assignment.util.Mapper

@Entity(tableName = "issue")
data class Issue(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: Long? = 0,

    @Json(name = "title")
    @ColumnInfo(name = "title")
    val title: String? = "",

    @Json(name = "comments_url")
    @ColumnInfo(name = "commentsUrl")
    val commentsUrl: String? = "",

    @Json(name = "body")
    @ColumnInfo(name = "description")
    val description: String? = "",

    @Json(name = "updated_at")
    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = "",

    @Embedded
    @Json(name = "user")
    val user: User,
) {
    fun getUpdateTime() = DateUtil.getStandardTime(updatedAt)

    fun getShortDescription() = description?.take(200)
}

@Entity(tableName = "comment")
data class Comment(
    @PrimaryKey(autoGenerate = false)
    @Json(name = "id")
    @ColumnInfo(name = "id")
    val id: Long? = 0,

    @Json(name = "body")
    @ColumnInfo(name = "comment")
    val comment: String,

    @Json(name = "updated_at")
    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = "",

    @Embedded
    @Json(name = "user")
    val user: User,
) {
    fun getUpdateTime() = DateUtil.getStandardTime(updatedAt)
}

data class User(
    @Json(name = "login")
    @ColumnInfo(name = "username")
    val username: String? = "",

    @Json(name = "avatar_url")
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String? = "",
)