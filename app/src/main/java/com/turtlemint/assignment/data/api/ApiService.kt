package com.turtlemint.assignment.data.api

import com.turtlemint.assignment.data.db.entity.Comment
import com.turtlemint.assignment.data.db.entity.Issue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * ApiService is responsible to define end points and its responses.
 */
interface ApiService {

    @GET("repos/square/okhttp/issues")
    suspend fun fetchIssues(): Response<List<Issue>>

    @GET
    suspend fun fetchComments(@Url url: String): Response<List<Comment>>
}