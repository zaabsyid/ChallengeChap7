package com.zahirar.challengechap7

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPrefs(private val context: Context) {
    val ID = stringPreferencesKey(USER_ID)
    val USERNAME = stringPreferencesKey(USER_USERNAME)
    val NAME = stringPreferencesKey(USER_NAME)
    val PASSWORD = stringPreferencesKey(USER_PASSWORD)
    val DATA_ISLOGIN = booleanPreferencesKey(IS_LOGIN)

    suspend fun saveData(paramUsername : String, paramName : String, paramPassword : String){
        context.dataStore.edit{
            it[USERNAME] = paramUsername
            it[NAME] = paramName
            it[PASSWORD] = paramPassword
        }
    }

    suspend fun saveDataId(paramId : String){
        context.dataStore.edit{
            it[ID] = paramId
        }
    }

    val getId : Flow<String> = context.dataStore.data.map {
        it[ID] ?: ""
    }

    val getUsername : Flow<String> = context.dataStore.data.map {
        it[USERNAME] ?: ""
    }

    val getName : Flow<String> = context.dataStore.data.map {
        it[NAME] ?: ""
    }

    val getPassword : Flow<String> = context.dataStore.data.map {
        it[PASSWORD] ?: ""
    }

    val getIsLogin : Flow<Boolean> = context.dataStore.data.map {
        it[DATA_ISLOGIN] ?: false
    }

    suspend fun saveIsLoginStatus(paramIsLogin : Boolean){
        context.dataStore.edit {
            it[DATA_ISLOGIN] = paramIsLogin
        }
    }

    suspend fun removeIsLoginStatus(){
        context.dataStore.edit {
            it.remove(DATA_ISLOGIN)
        }
    }

    companion object {
        const val USER_ID = "id"
        const val USER_USERNAME = "username"
        const val USER_NAME = "email"
        const val USER_PASSWORD = "password"
        const val IS_LOGIN = "isLogin"
        val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "dataUser")
    }
}