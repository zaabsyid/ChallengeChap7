package com.zahirar.challengechap7.view

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zahirar.challengechap7.R
import com.zahirar.challengechap7.databinding.ActivityProfileUserBinding
import com.zahirar.challengechap7.viewmodel.BlurViewModel
import com.zahirar.challengechap7.viewmodel.BlurViewModelFactory
import com.zahirar.challengechap7.viewmodel.ViewModelUser
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProfileUserActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileUserBinding
    lateinit var viewModel : ViewModelUser
    private val blurViewModel : BlurViewModel by viewModels { BlurViewModelFactory(application) }
    var id by Delegates.notNull<Int>()
    lateinit var oldPassword : String
    private val REQUEST_CODE_PERMISSION = 100

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.imageProfil.setImageURI(result)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModelUser::class.java)

        getDataUser()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnLogout.setOnClickListener {
            logout()
        }
        binding.btnUpdate.setOnClickListener {
            blurViewModel.applyBlur(1)
            updateUser()
        }
        binding.imageProfil.setOnClickListener {
            checkingPermissions()
        }
    }

    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                97,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openGallery() {
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.imageProfil.setImageBitmap(bitmap)
    }

    fun getDataUser(){
        viewModel.getDataUser(this)
        viewModel.observerName().observe(this) {
            binding.edtFullname.editText?.setText(it)
        }
        viewModel.observerUsername().observe(this) {
            binding.edtUsername.editText?.setText(it)
        }
        viewModel.observerPassword().observe(this) {
            oldPassword = it
            binding.edtPassword.editText?.setText(it)
            binding.edtKonfirmasiPassword.editText?.setText(it)
        }
        viewModel.observerId().observe(this){
            id = it.toInt()
        }
    }

    fun logout(){
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure want to logout?")
            .setPositiveButton("Yes") { dialog, which ->
                viewModel.removeIsLoginStatus()
                startActivity(Intent(this, LoginActivity::class.java))
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .show()
    }

    fun updateUser(){
        val name = binding.edtFullname.editText?.text.toString()
        val username = binding.edtUsername.editText?.text.toString()
        val password = binding.edtPassword.editText?.text.toString()
        val passwordConfirm = binding.edtKonfirmasiPassword.editText?.text.toString()
        if(password == passwordConfirm){
            viewModel.updateApiUsers(id, name, username, password)
            viewModel.saveData(name, username, password)
            viewModel.getliveDataUpdateUserId().observe(this, Observer {
                if (it != null) {
                    if(!oldPassword.equals(password)){
                        logout()
                    }
                    Toast.makeText(this, resources.getString(R.string.ubah_data_berhasil), Toast.LENGTH_SHORT).show()
                }
            })
        } else{
            Toast.makeText(this, resources.getString(R.string.ubah_data_gagal), Toast.LENGTH_SHORT).show()
        }
    }
}