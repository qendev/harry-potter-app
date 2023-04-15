package com.example.harrypotterapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.example.harrypotterapp.R
import com.example.harrypotterapp.adapter.CharactersAdapter
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.data.NetworkResult
import com.example.harrypotterapp.databinding.ActivityMainBinding
import com.example.harrypotterapp.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), android.widget.SearchView.OnQueryTextListener {




    private lateinit var binding: ActivityMainBinding
    private val charactersViewModel: CharactersViewModel by viewModels()

//    @Inject
    lateinit var charactersAdapter: CharactersAdapter

//    lateinit var characterList: ArrayList<CharactersItem>

    lateinit var characterList:ArrayList<CharactersItem>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        characterList = ArrayList()

        // on below line we are initializing our adapter
        charactersAdapter = CharactersAdapter(characterList)



        binding.recyclerViewCharacters.adapter = charactersAdapter


        charactersAdapter.setItemClick(object : CharactersAdapter.ClickInterface<CharactersItem> {
            override fun onClick(data: CharactersItem) {



                Toast.makeText(this@MainActivity, data.name, Toast.LENGTH_SHORT).show()





            }

        })



        charactersViewModel.characterResponse.observe(this) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.progressbar.isVisible = it.isLoading

                }

                is NetworkResult.Failure -> {
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                }

                is NetworkResult.Success -> {
                    charactersAdapter.updateCharactersList(it.data as ArrayList<CharactersItem>)
                    binding.progressbar.isVisible = false
//                    displayCharacters(it.data)
                    Log.e("HARRY POTTER", "${it.data}")
                }

            }
        }

        binding.searchView.setOnQueryTextListener(this)

    }

    private fun displayCharacters(data: ArrayList<CharactersItem>) {
//        charactersAdapter.addData(data)
        charactersAdapter.notifyDataSetChanged()

    }

    companion object{
        val NEXT_SCREEN="details_screen"
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
//        charactersAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        charactersAdapter.filter.filter(newText)
        return false
    }








}