package com.furkanbostan.messenger1

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
        Log.d("RegisterAcitvity", "sa yfa açıldı")

        }
    private fun verifuUserLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid==null){
            val intent = Intent(this,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
    }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("RegisterAcitvity", "item seçildi")
        when (item?.itemId){
            R.id.new_message_menu->{

            }
            R.id.sign_out_menu->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Log.d("RegisterActivity","merabaaa")
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }
}