package com.furkanbostan.messenger1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.furkanbostan.messenger1.databinding.ActivityLatestActicityMessengersBinding
import com.google.firebase.auth.FirebaseAuth

class LatestActicityMessengers : AppCompatActivity() {
    private lateinit var binding: ActivityLatestActicityMessengersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLatestActicityMessengersBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        verifuUserLoggedIn()

        }
    private fun verifuUserLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid==null){
            val intent = Intent(this,RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
    }
    }
}