package com.github.dragon925.rickandmorty.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.dragon925.rickandmorty.databinding.ItemCharacterBinding
import com.github.dragon925.rickandmorty.databinding.ItemEpisodeBinding
import com.github.dragon925.rickandmorty.databinding.ItemLocationBinding
import com.github.dragon925.rickandmorty.ui.models.CharacterItem
import com.github.dragon925.rickandmorty.ui.models.EpisodeItem
import com.github.dragon925.rickandmorty.ui.models.Item
import com.github.dragon925.rickandmorty.ui.models.LocationItem
import java.lang.IllegalArgumentException

class ItemListAdapter() : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(
    ItemDiffCallback()
) {

    companion object {
        private const val CHARACTER = 0
        private const val EPISODE = 1
        private const val LOCATION = 2
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is CharacterItem -> CHARACTER
        is EpisodeItem -> EPISODE
        is LocationItem -> LOCATION
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            CHARACTER -> CharacterItemViewHolder(
                ItemCharacterBinding.inflate(inflater, parent, false)
            )
            EPISODE -> EpisodeItemViewHolder(
                ItemEpisodeBinding.inflate(inflater, parent, false)
            )
            LOCATION -> LocationItemViewHolder(
                ItemLocationBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalArgumentException("Unexpected item type")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    abstract inner class ItemViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: Item)
    }

    private inner class CharacterItemViewHolder(
        private val binding: ItemCharacterBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(item: Item) {
            if (item !is CharacterItem) return

            with(binding) {
                tvName.text = item.name
                tvState.text = item.status.name // TODO status name and color
                tvSpecies.text = item.species
                //TODO gender
                tvOriginLocation.text = item.origin
                tvLastLocation.text = item.location
                tvEpisodeCount.text = item.episodes.toString()
                //TODO image
            }
        }
    }

    private inner class EpisodeItemViewHolder(
        private val binding: ItemEpisodeBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(item: Item) {
            if (item !is EpisodeItem) return

            with(binding) {
                tvName.text = item.name
                tvDate.text = item.airDate
                tvEpisode.text = item.episode
                tvCharactersCount.text = item.characters.toString()
            }
        }
    }

    private inner class LocationItemViewHolder(
        private val binding: ItemLocationBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(item: Item) {
            if (item !is LocationItem) return

            with(binding) {
                tvName.text = item.name
                tvType.text = item.type
                tvDimension.text = item.dimension
                tvCharactersCount.text = item.residents.toString()
            }
        }
    }

    private class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {

        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

}