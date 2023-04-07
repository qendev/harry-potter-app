package com.example.harrypotterapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.databinding.CharacterItemBinding
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CharactersAdapter @Inject constructor() : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>(), Filterable {


    var characters = mutableListOf<CharactersItem>()
    private var clickInterface: ClickInterface<CharactersItem>? = null

    fun updateCharactersList(harryPotterCharacters: ArrayList<CharactersItem>) {
        this.characters = harryPotterCharacters.toMutableList()
        notifyItemRangeInserted(0, harryPotterCharacters.size)
    }

    private var filteredCharacterList: List<CharactersItem> = characters


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CharactersAdapter.CharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        return CharactersViewHolder(binding)


    }

    override fun onBindViewHolder(holder: CharactersAdapter.CharactersViewHolder, position: Int) {
        val character = characters[position]
        holder.view.textViewName.text = "Name:${character.name}"
        holder.view.textViewYearOfBirth.text = "Year Of Birth:${character.yearOfBirth}"
        holder.view.textViewHouse.text = "House:${character.house}"
        holder.view.textViewWandWood.text = "${character.wand.wood},"
        holder.view.textViewWandCore.text = "${character.wand.core},"
        holder.view.textViewWandLength.text = "${character.wand.length.toString()}"
        Glide.with(holder.view.imageViewCharacter.context)
            .load(character.image)
//            .circleCrop()
            .into(holder.view.imageViewCharacter)

        holder.view.characterCard.setOnClickListener {
            clickInterface?.onClick(character)
        }

    }

    override fun getItemCount(): Int {
        return characters.size
    }

    fun setItemClick(clickInterface: ClickInterface<CharactersItem>) {
        this.clickInterface = clickInterface
    }

    interface ClickInterface<T> {
        fun onClick(data: T)
    }


    class CharactersViewHolder(val view: CharacterItemBinding) : RecyclerView.ViewHolder(view.root){
//        private  val textViewName:TextView =view.textViewName
//        private val textViewHouse:TextView = view.textViewHouse
//        private val textViewYearOfBirth:TextView = view.textViewYearOfBirth
//        private val textViewWandWood:TextView = view.textViewWandWood
//        private val textViewWandCore:TextView = view.textViewWandCore
//        private val textViewWandLength:TextView = view.textViewWandLength
//        private val imageViewCharacter:ImageView = view.imageViewCharacter

//        fun bind(character: Character) {
//            textViewName.text =character.toString()
//            textViewHouse.text = character.toString()
//            textViewWandWood.text =textViewWandWood.toString()
//            textViewWandCore.text = character.toString()
//            textViewWandLength.text = character.toString()
//            textViewYearOfBirth.text = character.toString()
//            imageViewCharacter.setImageResource(0).toString()
//
//
//
//        }


    }

    //for implementing search functionality
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    characters
                } else {
                    characters.filter { it.name.contains(constraint, true) }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCharacterList = results?.values as? List<CharactersItem> ?: emptyList()
                notifyDataSetChanged()
            }
        }
    }
}







