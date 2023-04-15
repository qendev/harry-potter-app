package com.example.harrypotterapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.databinding.CharacterItemBinding
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class CharactersAdapter(private var characters:ArrayList<CharactersItem>) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>(),
    Filterable {


    private var clickInterface: ClickInterface<CharactersItem>? = null

    var characterListFiltered:ArrayList<CharactersItem> = ArrayList()

    init {
        characterListFiltered = characters
    }

    fun updateCharactersList(harryPotterCharacters: ArrayList<CharactersItem>) {
        this.characterListFiltered = harryPotterCharacters.toMutableList() as ArrayList<CharactersItem>
        notifyItemRangeInserted(0, harryPotterCharacters.size)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CharactersAdapter.CharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CharacterItemBinding.inflate(inflater, parent, false)
        return CharactersViewHolder(binding)


    }

    override fun onBindViewHolder(holder: CharactersAdapter.CharactersViewHolder, position: Int) {
        val character = characterListFiltered[position]
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
        return characterListFiltered.size
    }

    fun setItemClick(clickInterface: ClickInterface<CharactersItem>) {
        this.clickInterface = clickInterface
    }

    interface ClickInterface<T> {
        fun onClick(data: T)
    }


    class CharactersViewHolder(val view: CharacterItemBinding) : RecyclerView.ViewHolder(view.root)



        //implementing filtering of items in the recyclerview
    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()){
                    characterListFiltered = characters
                }
                else{
                    val resultList = ArrayList<CharactersItem>()
                    for (charac in characterListFiltered){
                        if (charac.name.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))){
                            resultList.add(charac)
                        }

                    }

                    characterListFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values =characterListFiltered
                return filterResults



            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                characterListFiltered = results?.values as ArrayList<CharactersItem>
                notifyDataSetChanged()






            }

        }
    }



}







