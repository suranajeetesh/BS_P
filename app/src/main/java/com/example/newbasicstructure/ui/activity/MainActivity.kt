package com.example.newbasicstructure.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newbasicstructure.core.uI.BaseActivity
import com.example.newbasicstructure.databinding.ActivityMainBinding
import com.example.newbasicstructure.util.RequestCodeUtil
import com.example.newbasicstructure.util.extensionFunction.*
import com.example.newbasicstructure.util.helper.MarshMellowHelper
import com.example.newbasicstructure.viewmodel.DemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val homeViewModel: DemoViewModel by viewModels()
    private var marshMellowHelper: MarshMellowHelper? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        lifecycleScope.launch {
            homeViewModel.getData()
            binding.let {
                it.btnCamera.setOnClickListener {
                    if (!checkPermission(this@MainActivity, CheckPermission.CAMERA)) {
                        initCameraRequestPermission()
                    } else {
                        openCamera()
                    }
                }
                it.btnAudio.setOnClickListener {
                    if (!checkPermission(this@MainActivity, CheckPermission.AUDIO)) {
                        initAudioRequestPermission()
                    } else {
                        openAudio()
                    }
                }
                it.btnGallery.setOnClickListener {
                    if (!checkPermission(this@MainActivity, CheckPermission.STORAGE)) {
                        initStorageRequestPermission()
                    } else {
                        openGallery()
                    }
                }
            }
        }
    }

    private fun openGallery() {
        Toast.makeText(this, "open gallery", Toast.LENGTH_SHORT).show()
    }

    private fun openAudio() {
        Toast.makeText(this, "open audio", Toast.LENGTH_SHORT).show()
    }

    private fun openCamera() {
        Toast.makeText(this, "open camera", Toast.LENGTH_SHORT).show()
    }

    private fun initCameraRequestPermission() {
        marshMellowHelper = MarshMellowHelper(
            this, cameraWithStoragePermission,
            RequestCodeUtil.PERMISSIONS_AUDIO_RECORDING_REQUEST_CODE
        )
        marshMellowHelper!!.request(object : MarshMellowHelper.PermissionCallback {
            override fun onPermissionGranted() {
                openCamera()
            }

            override fun onPermissionDenied(permissionDeniedError: String) {
                Toast.makeText(this@MainActivity, permissionDeniedError, Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDeniedBySystem(permissionDeniedBySystem: String) {
                Toast.makeText(this@MainActivity, permissionDeniedBySystem, Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initAudioRequestPermission() {
        marshMellowHelper = MarshMellowHelper(
            this, audioCameraStoragePermission,
            RequestCodeUtil.PERMISSIONS_AUDIO_CAMERA_GALLERY_REQUEST_CODE
        )
        marshMellowHelper!!.request(object : MarshMellowHelper.PermissionCallback {
            override fun onPermissionGranted() {
                openAudio()
            }

            override fun onPermissionDenied(permissionDeniedError: String) {
                Toast.makeText(this@MainActivity, permissionDeniedError, Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDeniedBySystem(permissionDeniedBySystem: String) {
                Toast.makeText(this@MainActivity, permissionDeniedBySystem, Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun initStorageRequestPermission() {
        marshMellowHelper = MarshMellowHelper(
            this, storagePermission,
            RequestCodeUtil.PERMISSIONS_STORAGE_REQUEST_CODE
        )
        marshMellowHelper!!.request(object : MarshMellowHelper.PermissionCallback {
            override fun onPermissionGranted() {
                openGallery()
            }

            override fun onPermissionDenied(permissionDeniedError: String) {
                Toast.makeText(this@MainActivity, permissionDeniedError, Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDeniedBySystem(permissionDeniedBySystem: String) {
                Toast.makeText(this@MainActivity, permissionDeniedBySystem, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        marshMellowHelper?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}