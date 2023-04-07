package com.example.harrypotterapp.data

class Characters : ArrayList<CharactersItem>()

data class CharactersItem(
    val id: String,
    val image: String,
    val name: String,
    val house: String,
    val wand: Wand,
    val yearOfBirth: Int
)

data class Wand(
    val core: String,
    val length: Double,
    val wood: String
)

/*
In Kotlin, Sealed Classes can be termed as Enum classes on steroids.
Sealed classes allow us to create instances with different types.
Unlike Enums which restrict us to use the same type for all enum constants.
Also, I am using NetworkResult sealed class below to emit different statuses of the network call.
* */
sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading: Boolean) : NetworkResult<T>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
}

/*
On the repository, I am calling the API service and returning the response as flow to ViewModel.
Also, I am using NetworkResult sealed class to emit different statuses of the network call.
* */