package com.turtlemint.assignment.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.turtlemint.assignment.adapters.CommentAdapter
import com.turtlemint.assignment.databinding.CommentFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * CommentFragment shows the people list.
 */
class CommentFragment : Fragment() {

    /**
     * CommentViewModel injected bu dependency injection.
     */
    private val viewModel by viewModel<CommentViewModel>()

    /**
     * Binder to bind data with the view.
     */
    private lateinit var binding: CommentFragmentBinding

    /**
     * Converts the simple data into view and set to the recycler view.
     */
    private lateinit var adapter: CommentAdapter

    /**
     * Fragment arguments.
     */
    private val arguments: CommentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CommentFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observerState()
    }

    /**
     * Initialize the view.
     */
    private fun initView() {
        binding.tvTitle.text = arguments.title
        adapter = CommentAdapter(viewModel)
        binding.recyclerView.adapter = adapter
    }

    /**
     * Observe the states.
     */
    private fun observerState() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    is CommentState.Idle -> {
                        viewModel.commentIntent.send(CommentIntent.FetchComment(arguments.commentUrl))
                    }
                    is CommentState.Loading -> {
                        if (it.isLoading) {
                            setViewVisibility(View.GONE, View.GONE, View.VISIBLE)
                        } else {
                            setViewVisibility(View.VISIBLE, View.GONE, View.GONE)
                        }
                    }
                    is CommentState.Success -> {
                        adapter.submitList(it.list)
                    }
                    is CommentState.Error -> {
                        setViewVisibility(View.GONE, View.VISIBLE, View.GONE)
                        it.message?.let { message -> shoToast(message) }
                    }
                }
            }
        }
    }

    /**
     * Update view visibility.
     */
    private fun setViewVisibility(recyclerView: Int, emptyText: Int, progressBar: Int) {
        binding.recyclerView.visibility = recyclerView
        binding.tvEmpty.visibility = emptyText
        binding.progressCircular.visibility = progressBar
    }

    /**
     * Shows message.
     */
    private fun shoToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}