package com.furkanbostan.messenger1


import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import com.furkanbostan.messenger1.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.net.UnknownServiceException
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var myToolbar : Toolbar
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        auth= Firebase.auth
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        myToolbar = findViewById(R.id.actionbarLogin)
        myToolbar.title= "Register Activity"
        setSupportActionBar(myToolbar)

        binding.registerButton.setOnClickListener {
           performRegister()
        }
        binding.registerHaveAnAccountText.setOnClickListener{
            Log.d("MainActivity","Try to Login activity")
            val intent= Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

        binding.selectPhotoButtonRegister.setOnClickListener {
            Log.d("MainActivity","Try to select photo")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)
        }
    }
    var selectedPhotoUri : Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode== Activity.RESULT_OK && data != null){
         Log.d("RegisterActivity", "photo was selected")
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            binding.selectPhotoRegisterImageview.setImageBitmap(bitmap)
            binding.selectPhotoButtonRegister.alpha = 0f
            /*val bitmapDrawable = BitmapDrawable(bitmap)
            binding.selectPhotoButtonRegister.setBackgroundDrawable(bitmapDrawable)*/
        }
    }

    private fun performRegister(){
        val email = binding.registerEmailEditText.text.toString()
        val password = binding.registerPasswordEditText.text.toString()
        Log.d("MainActivity","Email : "+email)
        Log.d("MainActivity","Password : "+password)

        auth.createUserWithEmailAndPassword(binding.registerEmailEditText.text.toString(),binding.registerPasswordEditText.text.toString())
            .addOnCompleteListener {
            Log.d("MainActivity","Kullanıcı oluşturuldu")


        }.addOnFailureListener {
            Toast.makeText(applicationContext,it.localizedMessage, Toast.LENGTH_LONG).show()
        }
        upLadImageToFirebaseStroage()
    }

    private fun upLadImageToFirebaseStroage(){
        if (selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        println("aaaaaaaaaaaaaaaaaaaaaaaaa")
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .onSuccessTask () {
                println("foto seçildi")
                Log.d("MainActivity", "Sucsessfully upload image:")
                ref.downloadUrl.addOnSuccessListener {
                    it.toString()
                    Log.d("ActivityMain", "File Location $it")
                    saveUserToDatabase(it.toString())
                }.addOnFailureListener {
                    Log.d("ActivityMain","yüklnemedi")
                }
            }.addOnFailureListener {

            }

    }
    private fun saveUserToDatabase(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid, binding.registerUsernameEditText.toString(),profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActiivity","Finally saved the user to Firebase")
                val intent = Intent(this, LatestActicityMessengers::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }.addOnFailureListener {
                Log.d("RegisterActivity","saveSUerToDatabase hatalı")
            }
    }


    class User(val uid: String,val username: String, val profileImageUrl:String)
}


