package com.zahirar.challengechap7.network

import com.zahirar.challengechap7.model.DataFavoriteFilm
import com.zahirar.challengechap7.model.ResponseDataFavoriteItem
import retrofit2.Call
import retrofit2.http.*

interface APIInterfaceFavorite {

    @GET("favorite")
    fun getAllFilm() : Call<List<ResponseDataFavoriteItem>>

    @GET("favorite/{id}")
    fun getFilmById(@Path("id") id: Int) : Call<ResponseDataFavoriteItem>

    @POST("favorite")
    fun addDataFilm(@Body request : DataFavoriteFilm): Call<ResponseDataFavoriteItem>

    @PUT("favorite/{id}")
    fun updateDataFilm(@Path("id") id : Int, @Body reques : DataFavoriteFilm): Call<ResponseDataFavoriteItem>

    @DELETE("favorite/{id}")
    fun deleteFilm(@Path("id") id : Int): Call<ResponseDataFavoriteItem>

}