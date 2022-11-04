package com.zahirar.challengechap7.network

import com.zahirar.challengechap7.model.*
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @GET("film")
    fun getAllFilm() : Call<List<ResponseDataFilmItem>>

    @GET("film/{id}")
    fun getFilmById(@Path("id") id: Int) : Call<ResponseDataFilmItem>

    @POST("film")
    fun addDataFilm(@Body request : DataFilm): Call<ResponseDataFilmItem>

    @PUT("film/{id}")
    fun updateDataFilm(@Path("id") id : Int, @Body reques : DataFilm): Call<ResponseDataFilmItem>

    @DELETE("film/{id}")
    fun deleteFilm(@Path("id") id : Int): Call<ResponseDataFilmItem>

    @GET("users")
    fun getAllUser(): Call<List<ResponseDataUserItem>>

    @POST("users")
    fun registerUser(@Body request : DataUser): Call<ResponseDataUserItem>

    @GET("users/{id}")
    fun getUsersById(@Path("id") id: Int) : Call<ResponseDataUserItem>

    @PUT("users/{id}")
    fun updateDataUsers(@Path("id") id : Int, @Body reques : DataUser): Call<ResponseDataUserItem>

}