package com.example.homework_26.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_26.databinding.ItemLayoutBinding
import com.example.homework_26.presentation.model.FlattenedItems

class ItemsRecyclerAdapter :
    ListAdapter<FlattenedItems, ItemsRecyclerAdapter.ItemViewHolder>(ItemDiffUtil()) {

    class ItemDiffUtil : DiffUtil.ItemCallback<FlattenedItems>() {
        override fun areItemsTheSame(oldItem: FlattenedItems, newItem: FlattenedItems): Boolean {
            return oldItem.item.id == newItem.item.id
        }

        override fun areContentsTheSame(oldItem: FlattenedItems, newItem: FlattenedItems): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return ItemViewHolder(ItemLayoutBinding.inflate(inflate, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class ItemViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var item: FlattenedItems
        private lateinit var dotsRecyclerAdapter: DotsRecyclerAdapter

        fun bind() {
            item = currentList[adapterPosition]
            with(binding) {
                tvText.text = item.item.name

                val maxLevel = 4
                dotsRecyclerAdapter = if (item.parentCount > 4) {
                    DotsRecyclerAdapter(maxLevel)
                } else {
                    DotsRecyclerAdapter(item.parentCount)
                }
                rvDots.adapter = dotsRecyclerAdapter
                rvDots.layoutManager =
                    LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

}