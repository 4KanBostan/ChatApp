package com.furkanbostan.messenger1.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.furkanbostan.messenger1.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity:  AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= Firebase.auth
        binding= ActivityLoginBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)



        binding.loginBackToRegister.setOnClickListener {
            finish()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
        binding.loginLoginButton.setOnClickListener {

            auth.signInWithEmailAndPassword(binding.loginEmailEditText.text.toString(),binding.loginPasswordLogin.text.toString()).addOnSuccessListener {

                val intent = Intent(this, UsersActivity::class.java)
                Log.d("MainActivity","Kullanıcı girişi yapıldı")
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(applicationContext,it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

}