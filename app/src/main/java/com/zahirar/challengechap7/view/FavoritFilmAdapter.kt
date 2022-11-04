package com.zahirar.challengechap7.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zahirar.challengechap7.databinding.ItemFilmFavoritBinding
import com.zahirar.challengechap7.model.ResponseDataFavoriteItem

class FavoritFilmAdapter(var listFilm : List<ResponseDataFavoriteItem>): RecyclerView.Adapter<FavoritFilmAdapter.ViewHolder>() {

    var onUnfavoriteClick : ((ResponseDataFavoriteItem)->Unit)? = null

    class ViewHolder(var binding: ItemFilmFavoritBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritFilmAdapter.ViewHolder {
        var view = ItemFilmFavoritBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritFilmAdapter.ViewHolder, position: Int) {
        holder.binding.txtJudul.text = listFilm[position].name
        holder.binding.txtDirector.text = listFilm[position].director
        holder.binding.txtDeskripsi.text = listFilm[position].description
        Glide.with(holder.itemView).load(listFilm[position].image).into(holder.binding.imgFilm)

        holder.binding.cardView.setOnClickListener {
            var detail = Intent(it.context, DetailFilmActivity::class.java)
            detail.putExtra("id",listFilm[position].id)
            detail.putExtra("viewType", "favoritedetail")
            it.context.startActivity(detail)
        }

        holder.binding.ivFavorit.setOnClickListener {
            onUnfavoriteClick?.invoke(listFilm[position])
        }
    }

    override fun getItemCount(): Int {
        return listFilm.size
    }

}