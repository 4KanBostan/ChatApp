package com.furkanbostan.messenger1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.furkanbostan.messenger1.R
import com.furkanbostan.messenger1.databinding.ActivityUserProfilBinding
import com.furkanbostan.messenger1.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class UserProfilActivity : AppCompatActivity() {
    private lateinit var databaseReferance: DatabaseReference
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var binding: ActivityUserProfilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityUserProfilBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        firebaseUser= FirebaseAuth.getInstance().currentUser!!
        databaseReferance= FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid)
        databaseReferance.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                binding.userNameEtProfil.setText(user!!.username)
                binding.stanceEtProfil.setText(user!!.stance)

                if (user.username == ""){
                    binding.userImage.setImageResource(R.drawable.icons8_user_50px)
                }else{
                    Picasso.get().load(user.profileImageUrl).into(binding.userImage);
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_SHORT).show()
            }

        })


        binding.saveBtnProfil.setOnClickListener{

            val userName = binding.userNameEtProfil.text.toString()
            val stance = binding.stanceEtProfil.text.toString()

            updateDataProfil(userName,stance)
        }

        }

    private fun updateDataProfil(userName: String, stance: String) {
        val user = mapOf<String,String>(
            "username" to userName,
            "stance" to stance,
        )
        databaseReferance.updateChildren(user).addOnSuccessListener {
            Toast.makeText(this,"Başarıyla Güncellendi",Toast.LENGTH_SHORT).show()

        }
    }
}
