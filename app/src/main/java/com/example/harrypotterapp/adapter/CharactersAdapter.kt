package com.example.harrypotterapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.harrypotterapp.activity.CharacterDetailActivity
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.databinding.CharacterItemBinding
import java.util.*
import kotlin.collections.ArrayList

class CharactersAdapter(private var characters:ArrayList<CharactersItem>) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>(),
    Filterable {


    private var clickInterface: ClickInterface<CharactersItem>? = null

    var characterListFiltered:ArrayList<CharactersItem> = ArrayList()
    //decalred the contect
     private lateinit var context: Context


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

        //initialized the context
        context = parent.context

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

            val model = characterListFiltered.get(position)
            var dName:String = model.name
            var dHouse:String = model.house
            var dYOB:String = model.yearOfBirth.toString()
            var dImage:String = model.image



            val intent = Intent(context,CharacterDetailActivity::class.java)
            intent.putExtra("DetailName",dName)
            intent.putExtra("DetailHouse",dHouse)
            intent.putExtra("DetailYOB",dYOB)
            intent.putExtra("DetailImage",dImage)
            context.startActivity(intent)
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


    class CharactersViewHolder(val view: CharacterItemBinding) : RecyclerView.ViewHolder(view.root) {
        private val textViewName: TextView = view.textViewName
        private val textViewHouse: TextView = view.textViewHouse
        private val textViewYearOfBirth: TextView = view.textViewYearOfBirth
        private val textViewWandWood: TextView = view.textViewWandWood
        private val textViewWandCore: TextView = view.textViewWandCore
        private val textViewWandLength: TextView = view.textViewWandLength
        private val imageViewCharacter: ImageView = view.imageViewCharacter

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







