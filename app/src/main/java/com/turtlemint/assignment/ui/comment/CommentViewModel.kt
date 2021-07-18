package com.turtlemint.assignment.ui.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.turtlemint.assignment.data.Result
import com.turtlemint.assignment.data.db.entity.Comment
import com.turtlemint.assignment.data.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * CommentViewModel is loosely coupled with the activity or fragment, it receives the intent from
 * the view and perform the action and provides the response in the form of live data
 */
class CommentViewModel(private val repository: CommentRepository) : ViewModel() {

    companion object {
        private val TAG = CommentViewModel::class.java.simpleName
    }

    /**
     * Observes the intent.
     */
    val commentIntent = Channel<CommentIntent>(Channel.UNLIMITED)

    /**
     * Manage the states.
     */
    private val _state = MutableStateFlow<CommentState>(CommentState.Idle)
    val state: StateFlow<CommentState> get() = _state

    /**
     * Provides the data to the view in the form of live data.
     */
    val comments: LiveData<List<Comment>> get() = repository.fetchDataFromDB().asLiveData()

    init {
        handleIntent()
    }

    /**
     * handle the intent.
     */
    private fun handleIntent() {
        viewModelScope.launch {
            commentIntent.consumeAsFlow().collect {
                when (it) {
                    is CommentIntent.FetchComment -> fetchDataFromRemote(it.url)
                }
            }
        }
    }

    /**
     * Gets the data from the local database.
     */
    private fun fetchDataFromLocal(url: String) {
        if (comments.value.isNullOrEmpty()) {
            fetchDataFromRemote(url)
        }
    }

    /**
     * Gets the data from the remote server.
     */
    private fun fetchDataFromRemote(url: String) {
        viewModelScope.launch {
            try {
                _state.value = CommentState.Loading(true)
                when (val fetchComments = repository.fetchRemoteComments(url)) {
                    is Result.Success -> {
                        withContext(Dispatchers.IO) { repository.insert(fetchComments.data) }
                    }
                    is Result.Error -> {
                        _state.value =
                            CommentState.Error("Please check the network connection and try again")
                    }
                }
                _state.value = CommentState.Loading(false)
            } catch (e: Exception) {
                Log.d(TAG, "Error while fetching the data")
                _state.value = CommentState.Loading(false)
            }
        }
    }
}