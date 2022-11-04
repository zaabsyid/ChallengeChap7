package com.zahirar.challengechap7.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.databinding.ActivityFavoriteFilmBinding
import com.zahirar.challengechap7.viewmodel.ViewModelFavorite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFilmActivity : AppCompatActivity() {

    lateinit var binding : ActivityFavoriteFilmBinding
    lateinit var viewModel : ViewModelFavorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDataFavcriteFilm()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    fun showDataFavcriteFilm(){
        viewModel = ViewModelProvider(this).get(ViewModelFavorite::class.java)

        viewModel.getLiveDataFilem().observe(this, Observer {
            if (it != null){
                binding.rvListFavoriteFilm.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                val adapter = FavoritFilmAdapter(it)
                binding.rvListFavoriteFilm.adapter = adapter
                adapter.onUnfavoriteClick = {
                    deleteFilm(it.id.toInt())
                }
                adapter.notifyDataSetChanged()
            }
        })
        viewModel.callApiFilm()
    }

    fun deleteFilm(id: Int) {
        viewModel.callDeleteFilm(id)
        viewModel.getLiveDataDelFilm().observe(this, Observer {
            if (it != null){
                showDataFavcriteFilm()
                Toast.makeText(this, "Favorit berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        })
    }
}