package com.zahirar.challengechap7.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zahirar.challengechap7.model.DataFilm
import com.zahirar.challengechap7.model.ResponseDataFilmItem
import com.zahirar.challengechap7.network.APIInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelFilm @Inject constructor(var api : APIInterface) : ViewModel() {

    lateinit var liveDataFilm : MutableLiveData<List<ResponseDataFilmItem>>
    lateinit var LDFilmById : MutableLiveData<ResponseDataFilmItem>
    lateinit var postLDFilm : MutableLiveData<ResponseDataFilmItem>
    lateinit var updateFilm : MutableLiveData<ResponseDataFilmItem>
    lateinit var deleteFilm : MutableLiveData<ResponseDataFilmItem>

    init {
        liveDataFilm = MutableLiveData()
        LDFilmById = MutableLiveData()
        postLDFilm = MutableLiveData()
        updateFilm = MutableLiveData()
        deleteFilm = MutableLiveData()
    }

    fun getLiveDataFilem() : MutableLiveData<List<ResponseDataFilmItem>> {
        return liveDataFilm
    }

    fun getFilmById(id: Int): MutableLiveData<ResponseDataFilmItem> {
        return LDFilmById
    }

    fun postLiveDataFilm(): MutableLiveData<ResponseDataFilmItem> {
        return postLDFilm
    }

    fun updatLiveDataFilm() : MutableLiveData<ResponseDataFilmItem> {
        return updateFilm
    }

    fun getLiveDataDelFilm() : MutableLiveData<ResponseDataFilmItem> {
        return deleteFilm
    }

    fun callPostApiFilm(name : String, image : String, director : String, desc : String){
        api.addDataFilm(DataFilm(name,image,director,desc))
            .enqueue(object : Callback<ResponseDataFilmItem> {
                override fun onResponse(
                    call: Call<ResponseDataFilmItem>,
                    response: Response<ResponseDataFilmItem>
                ) {
                    if (response.isSuccessful){
                        postLDFilm.postValue(response.body())
                    }else{
                        postLDFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFilmItem>, t: Throwable) {
                    postLDFilm.postValue(null)
                }

            })
    }

    fun callGetFilmById(id : Int){
        api.getFilmById(id)
            .enqueue(object : Callback<ResponseDataFilmItem> {
                override fun onResponse(
                    call: Call<ResponseDataFilmItem>,
                    response: Response<ResponseDataFilmItem>
                ) {
                    if (response.isSuccessful){
                        LDFilmById.postValue(response.body())
                    }else{
                        LDFilmById.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFilmItem>, t: Throwable) {
                    LDFilmById.postValue(null)
                }

            })
    }

    fun callApiFilm(){
        api.getAllFilm()
            .enqueue(object : Callback<List<ResponseDataFilmItem>> {
                override fun onResponse(
                    call: Call<List<ResponseDataFilmItem>>,
                    response: Response<List<ResponseDataFilmItem>>
                ) {
                    if (response.isSuccessful){
                        liveDataFilm.postValue(response.body())
                    } else{
                        liveDataFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<List<ResponseDataFilmItem>>, t: Throwable) {
                    liveDataFilm.postValue(null)
                }

            })
    }

    fun updateApiFilm(id : Int, name : String, image : String , director: String, description : String){
        api.updateDataFilm(id, DataFilm(name,image,director,description))
            .enqueue(object : Callback<ResponseDataFilmItem> {
                override fun onResponse(
                    call: Call<ResponseDataFilmItem>,
                    response: Response<ResponseDataFilmItem>
                ) {
                    if (response.isSuccessful){
                        updateFilm.postValue(response.body())
                    }else{
                        updateFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFilmItem>, t: Throwable) {
                    updateFilm.postValue(null)
                }

            })
    }

    fun callDeleteFilm(id: Int) {
        api.deleteFilm(id)
            .enqueue(object : Callback<ResponseDataFilmItem> {
                override fun onResponse(
                    call: Call<ResponseDataFilmItem>,
                    response: Response<ResponseDataFilmItem>
                ) {
                    if (response.isSuccessful){
                        deleteFilm.postValue(response.body())
                    }else{
                        deleteFilm.postValue(null)
                    }
                }

                override fun onFailure(call: Call<ResponseDataFilmItem>, t: Throwable) {
                    deleteFilm.postValue(null)
                }

            })
    }

}