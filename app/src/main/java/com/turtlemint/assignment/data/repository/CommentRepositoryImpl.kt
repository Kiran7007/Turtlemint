package com.turtlemint.assignment.data.repository

import com.turtlemint.assignment.data.Result
import com.turtlemint.assignment.data.api.ApiService
import com.turtlemint.assignment.data.db.dao.CommentDao
import com.turtlemint.assignment.data.db.dao.IssueDao
import com.turtlemint.assignment.data.db.entity.Comment
import com.turtlemint.assignment.data.db.entity.Issue

/**
 * IssueRepositoryImpl responsible for doing database and network transactions.
 */
class CommentRepositoryImpl(
    private val apiService: ApiService,
    private val commentDao: CommentDao
) : CommentRepository {

    companion object {
        private val TAG = IssueRepository::class.java.simpleName
    }

    override suspend fun fetchRemoteComments(url: String): Result<List<Comment>> {
        return try {
            val response = apiService.fetchComments(url)
            if (response.isSuccessful) {
                Result.Success(response.body() ?: emptyList())
            } else {
                Result.Error(RuntimeException(response.message()))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun fetchDataFromDB() = commentDao.fetchComments()

    override suspend fun update(comment: Comment) = commentDao.update(comment)

    override suspend fun insert(data: List<Comment>) = commentDao.insert(data)
}