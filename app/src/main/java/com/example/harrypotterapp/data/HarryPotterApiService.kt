package com.example.harrypotterapp.data

import retrofit2.http.GET

interface HarryPotterApiService {

    @GET("characters")
    suspend fun getCharacters(): List<CharactersItem>
}

