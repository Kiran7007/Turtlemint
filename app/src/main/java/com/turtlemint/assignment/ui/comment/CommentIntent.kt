package com.turtlemint.assignment.ui.comment

/**
 * CommentIntent triggers the event to the viewModel.
 */
sealed class CommentIntent {

    /**
     * Trigger the intent to fetch the data from remote.
     */
    data class FetchComment(val url: String) : CommentIntent()
}