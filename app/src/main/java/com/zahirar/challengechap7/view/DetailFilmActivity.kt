package com.zahirar.challengechap7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.databinding.ActivityDetailFilmBinding
import com.zahirar.challengechap7.viewmodel.ViewModelFavorite
import com.zahirar.challengechap7.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFilmActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailFilmBinding
    lateinit var viewModel : ViewModelFilm
    lateinit var viewModelFavorite : ViewModelFavorite
    var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModelFavorite = ViewModelProvider(this).get(ViewModelFavorite::class.java)
        id = intent.getStringExtra("id")!!.toInt()
        checkDataAction(intent)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun checkDataAction(intent : Intent){
        when (intent.getStringExtra("viewType")) {
            "homedetail" -> {
                setDataFromHomeDetail()
            }
            "favoritedetail" -> {
                setDataFromFavoriteDetail()
            }
        }

    }

    fun setDataFromHomeDetail(){
        viewModel.callGetFilmById(id)
        viewModel.getFilmById(id).observe(this, Observer {
            binding.edtNamaFilm.editText?.setText(it.name)
            binding.edtSutradara.editText?.setText(it.director)
            binding.edtDeskripsi.editText?.setText(it.description)
            binding.edtLinkGambar.editText?.setText(it.image)
            Glide.with(this).load(it.image).into(binding.ivImgFilm)
        })
    }

    fun setDataFromFavoriteDetail(){
        viewModelFavorite.callGetFilmById(id)
        viewModelFavorite.getFilmById(id).observe(this, Observer {
            binding.edtNamaFilm.editText?.setText(it.name)
            binding.edtSutradara.editText?.setText(it.director)
            binding.edtDeskripsi.editText?.setText(it.description)
            binding.edtLinkGambar.editText?.setText(it.image)
            Glide.with(this).load(it.image).into(binding.ivImgFilm)
        })
    }
}