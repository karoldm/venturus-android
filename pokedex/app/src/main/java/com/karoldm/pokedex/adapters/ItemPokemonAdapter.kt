package com.karoldm.pokedex.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.karoldm.pokedex.data.models.PokemonListItem
import com.karoldm.pokedex.databinding.ItemPokemonBinding
import com.karoldm.pokedex.databinding.ItemPokemonBinding.*

class ItemPokemonAdapter(
    private val items: MutableList<PokemonListItem>
): RecyclerView.Adapter<ItemPokemonAdapter.ItemPokemonHolder>()
{
    class ItemPokemonHolder(val biding: ItemPokemonBinding) : RecyclerView.ViewHolder(biding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemPokemonHolder {
     val biding = inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemPokemonHolder(biding)
    }

    override fun onBindViewHolder(
        holder: ItemPokemonHolder,
        position: Int
    ) {
        val item = items[position]

        with(holder.biding){
            itemName.text = item.name
        }
    }

    override fun getItemCount(): Int = items.size

}