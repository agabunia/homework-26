package com.example.homework_26.presentation.screen

import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_26.databinding.FragmentHomeBinding
import com.example.homework_26.presentation.adapter.ItemsRecyclerAdapter
import com.example.homework_26.presentation.base.BaseFragment
import com.example.homework_26.presentation.event.ItemEvent
import com.example.homework_26.presentation.state.ItemState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var itemsRecyclerAdapter: ItemsRecyclerAdapter

    override fun bind() {
        setItemsRecyclerAdapter()
    }

    override fun bindListeners() {
        binding.edSearchBar.addTextChangedListener(object : TextWatcher {
            var delay: Long = 1000
            var timer = Timer()

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                timer.cancel()
                timer.purge()
            }

            override fun afterTextChanged(s: Editable?) {
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        val search = binding.edSearchBar.text.toString()
                        viewModel.onEvent(ItemEvent.Filter(search))
                    }
                }, delay)
            }
        })
    }

    override fun bindObserves() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemState.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun setItemsRecyclerAdapter() {
        itemsRecyclerAdapter = ItemsRecyclerAdapter()
        binding.rvList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = itemsRecyclerAdapter
        }
        viewModel.onEvent(ItemEvent.FetchItems)
    }

    private fun handleState(state: ItemState) {
        state.itemList?.let {
            itemsRecyclerAdapter = ItemsRecyclerAdapter()
            binding.apply {
                rvList.layoutManager = LinearLayoutManager(requireContext())
                rvList.setHasFixedSize(true)
                rvList.adapter = itemsRecyclerAdapter
            }
            itemsRecyclerAdapter.submitList(it)
        }

        state.errorMessage?.let {
            toastMessage(it)
            viewModel.onEvent(ItemEvent.ResetErrorMessage)
        }

        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }

    private fun toastMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}