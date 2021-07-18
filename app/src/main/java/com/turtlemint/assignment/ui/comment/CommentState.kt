package com.turtlemint.assignment.ui.comment

/**
 * CommentState holds the last state of the variable.
 */
sealed class CommentState {

    /**
     * Initial state of the view.
     */
    object Idle : CommentState()

    /**
     * Loading state of the view.
     */
    data class Loading(val isLoading: Boolean) : CommentState()

    /**
     * Error state of the view.
     */
    data class Error(val message: String?) : CommentState()
}