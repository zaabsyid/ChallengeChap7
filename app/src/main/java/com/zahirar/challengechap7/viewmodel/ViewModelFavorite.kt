package com.zahirar.challengechap7.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zahirar.challengechap7.model.DataFavoriteFilm
import com.zahirar.challengechap7.model.ResponseDataFavoriteItem
import com.zahirar.challengechap7.network.APIInterfaceFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelFavorite @Inject constructor(var api : APIInterfaceFavorite): ViewModel() {
    lateinit var liveDataFilm : MutableLiveData<List<ResponseDataFavoriteItem>>
    lateinit var LDFilmById : MutableLiveData<ResponseDataFavoriteItem>
    lateinit var postLDFilm : MutableLiveData<ResponseDataFavoriteItem>
    lateinit var updateFilm : MutableLiveData<ResponseDataFavoriteItem>
    lateinit var deleteFilm : MutableLiveData<ResponseDataFavoriteItem>

    init {
        liveDataFilm = MutableLiveData()
        LDFilmById = MutableLiveData()
        postLDFilm = MutableLiveData()
        updateFilm = MutableLiveData()
        deleteFilm = MutableLiveData()
    }

    fun getLiveDataFilem() : MutableLiveData<List<ResponseDataFavoriteItem>> {
        return liveDataFilm
    }

    fun getFilmById(id: Int): MutableLiveData<ResponseDataFavoriteItem> {
        return LDFilmById
    }

    fun postLiveDataFilm(): MutableLiveData<ResponseDataFavoriteItem> {
        return postLDFilm
    }

    fun updatLiveDataFilm() : MutableLiveData<ResponseDataFavoriteItem> {
        return updateFilm
    }

    fun getLiveDataDelFilm() : MutableLiveData<ResponseDataFavoriteItem> {
        return deleteFilm
    }

    fun callPostApiFilm(name : String, image : String, director : String, desc : String){
        api.addDataFilm(DataFavoriteFilm(name,image,director,desc))
            .enqueue(object : Callback<ResponseDataFavoriteItem> {
                override fun onResponse(
                    call: Call<ResponseDataFavoriteItem>,
                    response: Response<ResponseDataFavoriteItem>
                ) {
                    if (response.isSuccessful){
                        postLDFilm.postValue(response.body())
                    }else{
                        postLDFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFavoriteItem>, t: Throwable) {
                    postLDFilm.postValue(null)
                }

            })
    }

    fun callGetFilmById(id : Int){
        api.getFilmById(id)
            .enqueue(object : Callback<ResponseDataFavoriteItem> {
                override fun onResponse(
                    call: Call<ResponseDataFavoriteItem>,
                    response: Response<ResponseDataFavoriteItem>
                ) {
                    if (response.isSuccessful){
                        LDFilmById.postValue(response.body())
                    }else{
                        LDFilmById.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFavoriteItem>, t: Throwable) {
                    LDFilmById.postValue(null)
                }

            })
    }

    fun callApiFilm(){
        api.getAllFilm()
            .enqueue(object : Callback<List<ResponseDataFavoriteItem>> {
                override fun onResponse(
                    call: Call<List<ResponseDataFavoriteItem>>,
                    response: Response<List<ResponseDataFavoriteItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    } else{
                        liveDataFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataFavoriteItem>>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }

            })
    }

    fun updateApiFilm(id : Int, name : String, image : String , director: String, description : String){
        api.updateDataFilm(id, DataFavoriteFilm(name,image,director,description))
            .enqueue(object : Callback<ResponseDataFavoriteItem> {
                override fun onResponse(
                    call: Call<ResponseDataFavoriteItem>,
                    response: Response<ResponseDataFavoriteItem>
                ) {
                    if (response.isSuccessful){
                        updateFilm.postValue(response.body())
                    }else{
                        updateFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFavoriteItem>, t: Throwable) {
                    updateFilm.postValue(null)
                }

            })
    }

    fun callDeleteFilm(id: Int) {
        api.deleteFilm(id)
            .enqueue(object : Callback<ResponseDataFavoriteItem> {
                override fun onResponse(
                    call: Call<ResponseDataFavoriteItem>,
                    response: Response<ResponseDataFavoriteItem>
                ) {
                    if (response.isSuccessful){
                        deleteFilm.postValue(response.body())
                    }else{
                        deleteFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFavoriteItem>, t: Throwable) {
                    deleteFilm.postValue(null)
                }

            })
    }
}