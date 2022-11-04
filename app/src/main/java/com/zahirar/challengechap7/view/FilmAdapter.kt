package com.zahirar.challengechap7.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.databinding.ItemFilmBinding
import com.zahirar.challengechap7.model.ResponseDataFilmItem

class FilmAdapter(var listFilm : List<ResponseDataFilmItem>): RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    var onDeleteClick : ((ResponseDataFilmItem) -> Unit)? = null
    var onFavoriteClick : ((ResponseDataFilmItem)->Unit)? = null

    class ViewHolder(var binding: ItemFilmBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapter.ViewHolder {
        var view = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmAdapter.ViewHolder, position: Int) {
        holder.binding.txtJudul.text = listFilm[position].name
        holder.binding.txtDirector.text = listFilm[position].director
        holder.binding.txtDeskripsi.text = listFilm[position].description
        Glide.with(holder.itemView).load(listFilm[position].image).into(holder.binding.imgFilm)

        holder.binding.ivEdit.setOnClickListener{
            var edit = Intent(it.context, UpdateFilmActivity::class.java)
            edit.putExtra("id",listFilm[position].id)
            it.context.startActivity(edit)
        }

        holder.binding.cardView.setOnClickListener {
            var detail = Intent(it.context, DetailFilmActivity::class.java)
            detail.putExtra("id",listFilm[position].id)
            detail.putExtra("viewType", "homedetail")
            it.context.startActivity(detail)
        }

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClick?.invoke(listFilm[position])
        }

        holder.binding.ivFavorit.setOnClickListener {
            holder.binding.ivFavorit.setImageResource(R.drawable.ic_favorite_clicked)
            onFavoriteClick?.invoke(listFilm[position])
        }
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

}