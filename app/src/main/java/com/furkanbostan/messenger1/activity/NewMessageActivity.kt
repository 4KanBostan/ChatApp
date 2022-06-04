package com.furkanbostan.messenger1.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.messenger1.adapter.UserAdapter

import com.furkanbostan.messenger1.databinding.ActivityNewMessageActivityBinding
import com.furkanbostan.messenger1.model.User
import java.security.acl.Group

class NewMessageActivity : AppCompatActivity(){
    private lateinit var binding: ActivityNewMessageActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMessageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = "Select User"

        binding.recyclerviewNewMessage.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)
        var userlist = ArrayList<User>()

        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))
        userlist.add(User("4CZ9txDlJ0Z4onc9y6w7IeRpB3w2","Fako","https://firebasestorage.googleapis.com/v0/b/messenger1-3ccbd.appspot.com/o/images%2F779edadd-302e-4e88-affa-c2b31a6d9124?alt=media&token=b3447ee2-5d1e-4ec0-afab-5bd18e8e5e7e"))

        var userAdapter = UserAdapter(this,userlist)
        binding.recyclerviewNewMessage.adapter= userAdapter
    }

}



