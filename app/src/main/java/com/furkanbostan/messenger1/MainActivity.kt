package com.furkanbostan.messenger1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.furkanbostan.messenger1.databinding.ActivityMainBinding
import com.furkanbostan.messenger1.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity  : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}