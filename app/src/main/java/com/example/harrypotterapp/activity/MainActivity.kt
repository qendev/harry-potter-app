package com.example.harrypotterapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.harrypotterapp.adapter.CharactersAdapter
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.data.NetworkResult
import com.example.harrypotterapp.databinding.ActivityMainBinding
import com.example.harrypotterapp.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding
    private val charactersViewModel: CharactersViewModel by viewModels()

    @Inject
    lateinit var charactersAdapter: CharactersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.recyclerViewCharacters.adapter = charactersAdapter


        charactersAdapter.setItemClick(object : CharactersAdapter.ClickInterface<CharactersItem>{
            override fun onClick(data: CharactersItem) {

                Toast.makeText(this@MainActivity, data.name, Toast.LENGTH_SHORT).show()
            }

        })


        charactersViewModel.characterResponse.observe(this){
            when(it){
                is NetworkResult.Loading->{
                    binding.progressbar.isVisible =it.isLoading

                }

                is NetworkResult.Failure->{
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                    binding.progressbar.isVisible = false
                }

                is NetworkResult.Success->{
                    charactersAdapter.updateCharactersList(it.data as ArrayList<CharactersItem>)
                    binding.progressbar.isVisible = false
                    Log.e("HARRY POTTER","${it.data}")
                }

            }
        }
    }


}