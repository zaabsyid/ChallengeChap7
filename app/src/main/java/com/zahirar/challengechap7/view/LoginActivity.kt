package com.zahirar.challengechap7.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.UserPrefs
import com.zahirar.challengechap7.databinding.ActivityLoginBinding
import com.zahirar.challengechap7.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var userVM: ViewModelUser
    lateinit var userPrefs: UserPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPrefs = UserPrefs(this)
        userVM = ViewModelProvider(this).get(ViewModelUser::class.java)

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            loginProcess()
        }

        binding.btnIn.setOnClickListener {
            setLocale("id")
        }

        binding.btnEn.setOnClickListener {
            setLocale("en")
        }
    }

    fun gotoHome(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun loginProcess() {
        var username = binding.edtUsername.editText?.text.toString()
        var password = binding.edtPassword.editText?.text.toString()

        if(username.isEmpty() || password.isEmpty()) {
            binding.edtUsername.error = resources.getString(R.string.isi_masukan)
            binding.edtPassword.error = resources.getString(R.string.isi_masukan)
        }else{
            userVM.callGetAllUser()
            userVM.saveIsLoginStatus(true)
            userVM.observerLDGetUser().observe(this) {
                if (it != null) {
                    for (i in it){
                        if (i.username == username && i.password == password){
                            userVM.saveDataId(i.id)
                            Toast.makeText(this, resources.getString(R.string.login_berhasil), Toast.LENGTH_SHORT).show()
                            gotoHome()
                        }
                    }
                }
            }
        }
    }

    fun setLocale(lang: String) {
        val myLocale = Locale(lang)
        val res = resources
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, res.displayMetrics)
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}