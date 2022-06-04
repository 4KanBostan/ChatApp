package com.furkanbostan.messenger1.activity


import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.furkanbostan.messenger1.R

import com.furkanbostan.messenger1.databinding.ActivityRegisterBinding
import com.furkanbostan.messenger1.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream
import java.net.URL
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var url: URL


    override fun onCreate(savedInstanceState: Bundle?) {
        auth= Firebase.auth
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        binding.registerButton.setOnClickListener {
           performRegister()
        }
        binding.registerHaveAnAccountText.setOnClickListener{
            Log.d("MainActivity","Try to Login activity")
            val intent= Intent(this, LoginActivity::class.java)
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
        var stance= "Hello, I'm using messenger"
        if (profileImageUrl==""){
            val profileImageUrl1="https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F1277dc79-b6a0-46d6-ad55-4f7ec08d1673?alt=media&token=fcad00e0-e733-4bf7-bd21-75509e006d4c"
            val user = User(uid, binding.registerUsernameEditText.text.toString(),profileImageUrl1,stance)
            ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("RegisterActiivity","Finally saved the user to Firebase")
                    val intent = Intent(this, UsersActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }.addOnFailureListener {
                    Log.d("RegisterActivity","saveSUerToDatabase hatalı")
                }
        }else{
            val user = User(uid, binding.registerUsernameEditText.text.toString(),profileImageUrl,stance)
            ref.setValue(user)
                .addOnSuccessListener {
                    Log.d("RegisterActiivity","Finally saved the user to Firebase")
                    val intent = Intent(this, UsersActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }.addOnFailureListener {
                    Log.d("RegisterActivity","saveSUerToDatabase hatalı")
                }
        }

    }



}


