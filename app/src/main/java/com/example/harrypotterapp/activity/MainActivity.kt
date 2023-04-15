package com.example.harrypotterapp.activity

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


    override fun onQueryTextSubmit(query: String?): Boolean {
//        charactersAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        charactersAdapter.filter.filter(newText)
        return false
    }

    // calling on create option menu
    // layout to inflate our menu file.
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // below line is to get our inflater
//        val inflater = menuInflater
//
//        // inside inflater we are inflating our menu file.
//        inflater.inflate(R.menu.search_menu, menu)
//
//        // below line is to get our menu item.
//        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
//
//        // getting search view of our item.
//        val searchView: SearchView = searchItem.actionView as SearchView
//
//        // below line is to call set on query text listener method.
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(msg: String): Boolean {
//                // inside on query text change method we are
//                // calling a method to filter our recycler view.
//                filterCharacters(msg)
//                return false
//            }
//        })
//        return true
//    }


//    private fun filterCharacters(text: String) {
//        // creating a new array list to filter our data.
//        val filteredCharacterList: ArrayList<CharactersItem> = ArrayList()
//
//        // running a for loop to compare elements.
//        for (character in characterList) {
//            // checking if the entered string matched with any item of our recycler view.
//            if (character.name.toLowerCase().contains(text.toLowerCase())) {
//                // if the item is matched we are
//                // adding it to our filtered list.
//                filteredCharacterList.add(character)
//            }
//        }
//        if (filteredCharacterList.isEmpty()){
//            // if no item is added in filtered list we are
//            // displaying a toast message as no data found.
//            Toast.makeText(this, "No Such Character was found...", Toast.LENGTH_SHORT).show()
//        }
//        else{
//            // at last we are passing that filtered
//            // list to our adapter class.
//            charactersAdapter.filterList(filteredCharacterList)
//        }
//
//    }



}