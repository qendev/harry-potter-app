package com.example.harrypotterapp.viewmodel

import android.app.appsearch.SearchResult
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.data.NetworkResult
import com.example.harrypotterapp.repository.HarryPotterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
ViewModel setup with Livedata
In the ViewModel collect the flow and set the response to livedata for UI changes.
* */
@HiltViewModel
class CharactersViewModel @Inject constructor(private val harryPotterRepository: HarryPotterRepository) : ViewModel() {

    private var _characterResponse = MutableLiveData<NetworkResult<List<CharactersItem>>>()

    val characterResponse: LiveData<NetworkResult<List<CharactersItem>>> = _characterResponse


    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            harryPotterRepository.getCharacters().collect() {
                _characterResponse.postValue(it)
                Log.e("THE LIST", "$it")
            }
        }
    }
}




