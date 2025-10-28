package com.karoldm.pokedex.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.karoldm.pokedex.R
import com.karoldm.pokedex.adapters.ItemPokemonAdapter
import com.karoldm.pokedex.data.models.PokemonListItem
import com.karoldm.pokedex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val pokemons: MutableList<PokemonListItem> = mutableListOf()

    private lateinit var adapter: ItemPokemonAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configureComponents()
        configureAdapters()
    }

    private fun configureAdapters(){
        adapter = ItemPokemonAdapter(pokemons)
        binding.pokemonsList.layoutManager = LinearLayoutManager(this)
        binding.pokemonsList.adapter = adapter
    }

    fun configureComponents() {
        binding.button.setOnClickListener {
            print("searching by ${binding.inputSearch.text}")
            binding.inputSearch.text.clear()
        }
    }
}