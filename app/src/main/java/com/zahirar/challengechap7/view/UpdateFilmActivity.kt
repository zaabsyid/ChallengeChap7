package com.zahirar.challengechap7.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.databinding.ActivityUpdateFilmBinding
import com.zahirar.challengechap7.viewmodel.ViewModelFilm
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class UpdateFilmActivity : AppCompatActivity() {
    lateinit var binding : ActivityUpdateFilmBinding
    lateinit var viewModel : ViewModelFilm
    var id by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateFilmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModelFilm::class.java)
        id = intent.getStringExtra("id")!!.toInt()
        setDataToInput()

        binding.btnPerbaruiFilm.setOnClickListener {
            updateDataFilm()
            finish()
        }
    }

    fun setDataToInput(){
        viewModel.callGetFilmById(id)
        viewModel.getFilmById(id).observe(this, Observer {
            binding.edtNamaFilm.editText?.setText(it.name)
            binding.edtSutradara.editText?.setText(it.director)
            binding.edtDeskripsi.editText?.setText(it.description)
            binding.edtLinkGambar.editText?.setText(it.image)
            Glide.with(this).load(it.image).into(binding.ivImgFilm)
        })
    }

    fun updateDataFilm(){
        var name = binding.edtNamaFilm.editText?.text.toString()
        var director = binding.edtSutradara.editText?.text.toString()
        var desc = binding.edtDeskripsi.editText?.text.toString()
        var img  = binding.edtLinkGambar.editText?.text.toString()

        viewModel.updateApiFilm(id,name,img,director,desc)
        viewModel.updatLiveDataFilm().observe(this, Observer {
            if (it != null){
                Toast.makeText(this,"Update Data Success", Toast.LENGTH_SHORT).show()
            }
        })
    }
}