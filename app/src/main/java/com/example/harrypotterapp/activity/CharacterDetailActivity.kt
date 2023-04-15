package com.example.harrypotterapp.activity

import android.annotation.SuppressLint
import android.net.http.SslCertificate.DName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.harrypotterapp.R
import com.example.harrypotterapp.data.CharactersItem
import com.example.harrypotterapp.databinding.ActivityCharacterDetailBinding
import com.example.harrypotterapp.databinding.CharacterItemBinding

//for displaying details about a certain character
class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCharacterDetailBinding



    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar:ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)


        var intent = intent
        val detailName = intent.getStringExtra("DetailName")
        val detailHouse = intent.getStringExtra("DetailHouse")
        val detailYOB = intent.getStringExtra("DetailYOB")
        val detailImage = intent.getStringExtra("DetailImage")


        actionBar.setTitle(detailName)
        binding.textViewName.text ="Name:$detailName"
        binding.textViewHouse.text ="House:$detailHouse"
        binding.textViewYearOfBirth.text="Year Of Birth:$detailYOB"

        //Insert the image using Glide
        Glide.with(binding.imageViewCharacter.context)
            .load(detailImage)
            .into(binding.imageViewCharacter)







    }
}