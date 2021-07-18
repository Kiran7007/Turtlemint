package com.turtlemint.assignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.turtlemint.assignment.data.db.entity.Issue
import com.turtlemint.assignment.databinding.LayoutIssueItemBinding
import com.turtlemint.assignment.ui.issue.IssueViewModel

/**
 * IssueAdapter is responsible to covert people data into view by binding Issue model with the view.
 */
class IssueAdapter(
    private val viewModel: IssueViewModel,
    private val onItemClick: ((Issue) -> Unit)? = null
) :
    ListAdapter<Issue, IssueAdapter.ViewHolder>(IssueDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position), viewModel, onItemClick)

    /**
     * ViewHolder binds each item to the view, the object of this class recycles.
     */
    class ViewHolder(private val binding: LayoutIssueItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind the people model with the view.
         */
        fun bind(item: Issue?, viewModel: IssueViewModel, onItemClick: ((Issue) -> Unit)? = null) {
            item?.let {
                binding.item = item
                binding.viewmodel = viewModel
                binding.root.setOnClickListener { onItemClick?.invoke(item) }
            }
        }

        /**
         * Methods and variables in companion object are static.
         */
        companion object {
            // static method to create the instance of view holder.
            fun from(parent: ViewGroup): ViewHolder {
                val binding = LayoutIssueItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ViewHolder(binding)
            }
        }
    }

    /**
     * IssueDiffCallBack replace only those items in the list which is updated.
     */
    class IssueDiffCallBack : DiffUtil.ItemCallback<Issue>() {
        override fun areItemsTheSame(oldItem: Issue, newItem: Issue) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Issue, newItem: Issue) = oldItem == newItem
    }
}


