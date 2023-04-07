package com.example.harrypotterapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.data.HarryPotterApiService
import com.example.harrypotterapp.data.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*
On the repository, I am calling the API service and returning the response as flow to ViewModel.
Also, I am using NetworkResult sealed class to emit different statuses of the network call.
* */
class HarryPotterRepository @Inject constructor(private val apiService: HarryPotterApiService) {
    suspend fun getCharacters() = flow{
        emit(NetworkResult.Loading(true))
        val response = apiService.getCharacters()
        emit(NetworkResult.Success(response.toList()))
    }.catch{ e ->
        emit(NetworkResult.Failure(e.message?:"Unknown Error"))

    }


}