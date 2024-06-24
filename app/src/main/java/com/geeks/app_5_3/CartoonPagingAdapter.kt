package com.geeks.app_5_3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.geeks.app_5_3.databinding.ItemCharacterBinding
import com.geeks.app_5_3.models.Result

class CartoonPagingAdapter : PagingDataAdapter<com.geeks.app_5_3.models.Result, CartoonPagingAdapter.CharacterViewHolder>(CHARACTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        character?.let {
            holder.bind(it)
        }
    }

    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: com.geeks.app_5_3.models.Result) {
            binding.apply {
                Glide.with(itemView)
                    .load(character.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(imgCharacter)
            }
        }
    }

    companion object {
        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<com.geeks.app_5_3.models.Result>() {
            override fun areItemsTheSame(oldItem: com.geeks.app_5_3.models.Result, newItem: com.geeks.app_5_3.models.Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: com.geeks.app_5_3.models.Result, newItem: com.geeks.app_5_3.models.Result): Boolean {
                return oldItem == newItem
            }
        }
    }
}
