package com.github.dragon925.rickandmorty.ui.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.dragon925.rickandmorty.R
import com.github.dragon925.rickandmorty.databinding.ItemCharacterBinding
import com.github.dragon925.rickandmorty.databinding.ItemCharacterShortBinding
import com.github.dragon925.rickandmorty.databinding.ItemEpisodeBinding
import com.github.dragon925.rickandmorty.databinding.ItemEpisodeShortBinding
import com.github.dragon925.rickandmorty.databinding.ItemLocationBinding
import com.github.dragon925.rickandmorty.domain.models.CharacterStatus
import com.github.dragon925.rickandmorty.domain.models.Gender
import com.github.dragon925.rickandmorty.ui.models.CharacterItem
import com.github.dragon925.rickandmorty.ui.models.CharacterShortItem
import com.github.dragon925.rickandmorty.ui.models.EpisodeItem
import com.github.dragon925.rickandmorty.ui.models.EpisodeShortItem
import com.github.dragon925.rickandmorty.ui.models.Item
import com.github.dragon925.rickandmorty.ui.models.LocationItem
import java.lang.IllegalArgumentException

class ItemListAdapter(
    private val openItem: (id: Long) -> Unit
) : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(
    ItemDiffCallback()
) {

    companion object {
        private const val CHARACTER = 0
        private const val EPISODE = 1
        private const val LOCATION = 2
        private const val CHARACTER_SHORT = 3
        private const val EPISODE_SHORT = 4
    }

    override fun getItemViewType(position: Int): Int = when(getItem(position)) {
        is CharacterItem -> CHARACTER
        is EpisodeItem -> EPISODE
        is LocationItem -> LOCATION
        is CharacterShortItem -> CHARACTER_SHORT
        is EpisodeShortItem -> EPISODE_SHORT
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
            CHARACTER_SHORT -> CharacterShortItemViewHolder(
                ItemCharacterShortBinding.inflate(inflater, parent, false)
            )
            EPISODE_SHORT -> EpisodeShortItemViewHolder(
                ItemEpisodeShortBinding.inflate(inflater, parent, false)
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

            val resources = binding.root.resources
            val theme = binding.root.context.theme
            val color: Int = when(item.status) {
                CharacterStatus.ALIVE -> resources.getColor(R.color.green_500, theme)
                CharacterStatus.DEAD -> resources.getColor(R.color.red_500, theme)
                CharacterStatus.UNKNOWN -> resources.getColor(R.color.grey_500, theme)
            }
            val icon: Int = when(item.gender) {
                Gender.FEMALE -> R.drawable.ic_gender_female_14
                Gender.MALE -> R.drawable.ic_gender_male_14
                Gender.GENDERLESS -> 0
                Gender.UNKNOWN -> 0
            }

            with(binding) {
                root.setOnClickListener { openItem(item.id) }
                tvName.text = item.name
                tvState.text = item.status.name // TODO status name
                tvSpecies.text = item.species
                viewState.backgroundTintList = ColorStateList.valueOf(color)
                tvSpecies.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, icon, 0)
                tvOriginLocation.text = item.origin
                tvLastLocation.text = item.location
                tvEpisodeCount.text = item.episodes.toString()
                //TODO image
            }
        }
    }

    private inner class CharacterShortItemViewHolder(
        private val binding: ItemCharacterShortBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(item: Item) {
            if (item !is CharacterShortItem) return

            val resources = binding.root.resources
            val theme = binding.root.context.theme
            val color: Int = when(item.status) {
                CharacterStatus.ALIVE -> resources.getColor(R.color.green_500, theme)
                CharacterStatus.DEAD -> resources.getColor(R.color.red_500, theme)
                CharacterStatus.UNKNOWN -> resources.getColor(R.color.grey_500, theme)
            }
            val icon: Int = when(item.gender) {
                Gender.FEMALE -> R.drawable.ic_gender_female_14
                Gender.MALE -> R.drawable.ic_gender_male_14
                Gender.GENDERLESS -> 0
                Gender.UNKNOWN -> 0
            }

            with(binding) {
                root.setOnClickListener { openItem(item.id) }
                tvName.text = item.name
                tvState.text = item.status.name // TODO status name
                tvSpecies.text = item.species
                viewState.backgroundTintList = ColorStateList.valueOf(color)
                tvSpecies.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, icon, 0)
                // TODO image
            }
        }
    }

    private inner class EpisodeItemViewHolder(
        private val binding: ItemEpisodeBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(item: Item) {
            if (item !is EpisodeItem) return

            with(binding) {
                root.setOnClickListener { openItem(item.id) }
                tvName.text = item.name
                tvDate.text = item.airDate
                tvEpisode.text = item.episode
                tvCharactersCount.text = item.characters.toString()
            }
        }
    }

    private inner class EpisodeShortItemViewHolder(
        private val binding: ItemEpisodeShortBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(item: Item) {
            if (item !is EpisodeShortItem) return

            with(binding) {
                root.setOnClickListener { openItem(item.id) }
                tvName.text = item.name
                tvDate.text = item.airDate
                tvEpisode.text = item.episode
            }
        }
    }

    private inner class LocationItemViewHolder(
        private val binding: ItemLocationBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(item: Item) {
            if (item !is LocationItem) return

            with(binding) {
                root.setOnClickListener { openItem(item.id) }
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