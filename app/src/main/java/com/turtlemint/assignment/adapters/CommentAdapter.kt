package com.turtlemint.assignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtlemint.assignment.data.db.entity.Comment
import com.turtlemint.assignment.data.db.entity.Issue
import com.turtlemint.assignment.databinding.LayoutCommentItemBinding
import com.turtlemint.assignment.databinding.LayoutIssueItemBinding
import com.turtlemint.assignment.ui.comment.CommentViewModel
import com.turtlemint.assignment.ui.issue.IssueViewModel

/**
 * CommentAdapter is responsible to covert people data into view by binding comment model with the view.
 */
class CommentAdapter(private val viewModel: CommentViewModel) :
    ListAdapter<Comment, CommentAdapter.ViewHolder>(CommentDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), viewModel)

    /**
     * ViewHolder binds each item to the view, the object of this class recycles.
     */
    class ViewHolder(private val binding: LayoutCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind the people model with the view.
         */
        fun bind(item: Comment?, viewModel: CommentViewModel) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel
            }
        }

        /**
         * Methods and variables in companion object are static.
         */
        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): ViewHolder {
                val binding = LayoutCommentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    /**
     * CommentDiffCallBack replace only those items in the list which is updated.
     */
    class CommentDiffCallBack : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Comment, newItem: Comment) = oldItem == newItem
    }
}


