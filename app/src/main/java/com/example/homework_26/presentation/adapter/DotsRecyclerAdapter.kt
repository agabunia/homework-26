package com.example.homework_26.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.homework_26.databinding.DotLayoutBinding

class DotsRecyclerAdapter(private val dots: Int?) : Adapter<DotsRecyclerAdapter.DotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DotViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        return DotViewHolder(DotLayoutBinding.inflate(inflate, parent, false))
    }

    override fun getItemCount(): Int {
        return dots ?: 0
    }

    override fun onBindViewHolder(holder: DotViewHolder, position: Int) {

    }

    inner class DotViewHolder(private val binding: DotLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}