package com.furkanbostan.messenger1.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.messenger1.R
import com.furkanbostan.messenger1.adapter.UserAdapter
import com.furkanbostan.messenger1.databinding.ActivityUsersBinding
import com.furkanbostan.messenger1.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class UsersActivity : AppCompatActivity() {
    var userlist = ArrayList<User>()
    var user1 = User()
    private lateinit var binding: ActivityUsersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUsersBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)
        verifuUserLoggedIn()
        Log.d("RegisterAcitvity", "sayfa açıldı")
        binding.userRecyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        getUsersList()

        /*userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
*/


    }

    fun getUsersList(){

        val firebase : FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        val databaseReference :DatabaseReference= FirebaseDatabase.getInstance().getReference("users")

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                val currentUser = snapshot.getValue(User::class.java)
                if (currentUser!!.profileImageUrl==""){
                    currentUser.profileImageUrl= R.drawable.icons8_user_50px.toString()
                }
                for (dataSnapshot:DataSnapshot in snapshot.children){
                    var user = dataSnapshot.getValue(User::class.java)
                    Log.d("ActivityUsers","snapshot içinde")
                    if (user != null) {
                        userlist.add(user)
                    }
                    if (user!!.userUid == firebase.uid){
                        user1 = user
                        Log.d("ActivityUsers","if içinde")
                    }
                }
                val userAdapter = UserAdapter(this@UsersActivity,userlist)
                binding.userRecyclerview.adapter= userAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.message,Toast.LENGTH_LONG).show()
            }

         })

    }



    private fun verifuUserLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if (uid==null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
    }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("RegisterAcitvity", "item seçildi")
        when (item.itemId){
            R.id.new_message_menu ->{
                val intent = Intent(this, NewMessageActivity::class.java)
                startActivity(intent)

            }
            R.id.sign_out_menu ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                Log.d("RegisterActivity","merabaaa")
            }
            R.id.profil_menu->{
                val intent = Intent(this,UserProfilActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }
}