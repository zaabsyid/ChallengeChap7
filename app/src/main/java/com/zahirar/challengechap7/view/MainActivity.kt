package com.zahirar.challengechap7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.UserPrefs
import com.zahirar.challengechap7.databinding.ActivityMainBinding
import com.zahirar.challengechap7.viewmodel.ViewModelFavorite
import com.zahirar.challengechap7.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var userPrefs : UserPrefs
    lateinit var viewModel : ViewModelFilm
    lateinit var viewModelFavorite: ViewModelFavorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPrefs = UserPrefs(this)

        userPrefs.getName.asLiveData().observe(this, {
            binding.tvFullname.text = it
        })

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddFilmActivity::class.java)
            startActivity(intent)
        }

        binding.ivProfile.setOnClickListener {
            val intent = Intent(this, ProfileUserActivity::class.java)
            startActivity(intent)
        }

        binding.btnFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteFilmActivity::class.java)
            startActivity(intent)
        }

        showDataFilm()
    }

    override fun onRestart() {
        this.recreate()
        super.onRestart()
    }

    fun showDataFilm(){
        viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        viewModelFavorite = ViewModelProvider(this).get(ViewModelFavorite::class.java)

        viewModel.getLiveDataFilem().observe(this, Observer {
            if (it != null){
                binding.rvListFilm.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                val adapter = FilmAdapter(it)
                binding.rvListFilm.adapter = adapter
                adapter.onDeleteClick = {
                    deleteFilm(it.id.toInt())
                }
                adapter.notifyDataSetChanged()
                adapter.onFavoriteClick = {
                    viewModelFavorite.callPostApiFilm(it.name, it.image, it.director, it.description)
                    viewModelFavorite.postLiveDataFilm().observe(this,{
                        if(it != null){
                            Toast.makeText(this,"Tambah Favorit Berhasil", Toast.LENGTH_SHORT).show()
                        }
                    })
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
                showDataFilm()
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        })
    }
}