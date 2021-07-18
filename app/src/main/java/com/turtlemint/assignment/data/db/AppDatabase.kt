package com.turtlemint.assignment.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.turtlemint.assignment.data.db.dao.CommentDao
import com.turtlemint.assignment.data.db.dao.IssueDao
import com.turtlemint.assignment.data.db.entity.Comment
import com.turtlemint.assignment.data.db.entity.Issue
import com.turtlemint.assignment.util.DATABASE_VERSION

/**
 * AppDatabase manages all the database configuration and transaction.
 */
@Database(
    entities = [Issue::class, Comment::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun issueDao(): IssueDao
    abstract fun commentDao(): CommentDao
}