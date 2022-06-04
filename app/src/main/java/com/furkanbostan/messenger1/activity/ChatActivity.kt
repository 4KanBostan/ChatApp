package com.furkanbostan.messenger1.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.messenger1.R
import com.furkanbostan.messenger1.adapter.ChatAdapter
import com.furkanbostan.messenger1.adapter.UserAdapter
import com.furkanbostan.messenger1.databinding.ActivityChatBinding
import com.furkanbostan.messenger1.model.Chat
import com.furkanbostan.messenger1.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class ChatActivity : AppCompatActivity() {
    var firebaseUser:FirebaseUser?=null
    var databasereferance : DatabaseReference?=null
    var chatList= ArrayList<Chat>()


    private lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityChatBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        binding.chatRecyclerview.layoutManager= LinearLayoutManager(this,RecyclerView.VERTICAL,false)

        var intent = getIntent()
        var userId = intent.getStringExtra("userId")
        firebaseUser=FirebaseAuth.getInstance().currentUser
        databasereferance = FirebaseDatabase.getInstance().getReference("users").child(userId!!)

        databasereferance!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                supportActionBar?.title = user!!.username
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        binding.sendImgBtn.setOnClickListener{
            var message:String = binding.etMessage.text.toString()
            Log.d("Activity",message)
            if(message.isEmpty()){
                Toast.makeText(applicationContext,"message is empty",Toast.LENGTH_SHORT).show()
            }else{
                sendMessage(firebaseUser!!.uid,userId,message)
                binding.etMessage.text.clear()
            }

            readMessage(firebaseUser!!.uid,userId )
        }
    }

    private fun sendMessage(senderId :String, receiverId:String,message: String){
        var referance :DatabaseReference?= FirebaseDatabase.getInstance().getReference()
        var hashMap:HashMap<String,String> = HashMap()
        hashMap.put("senderId",senderId)
        hashMap.put("receiverId",receiverId)
        hashMap.put("message",message)
        referance!!.child("Chat").push().setValue(hashMap)

    }

    fun readMessage(senderId: String,receiverId: String){

        val databaseReference :DatabaseReference= FirebaseDatabase.getInstance().getReference("Chat")

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                chatList.clear()
                for (dataSnapshot:DataSnapshot in snapshot.children){
                    var chat = dataSnapshot.getValue(Chat::class.java)
                    Log.d("ActivityUsers","snapshot içinde")
                    if (chat!!.senderId.equals(senderId)&& chat!!.receiverId.equals(receiverId)||
                        chat!!.senderId.equals(receiverId)&& chat!!.receiverId.equals(senderId)){
                        chatList.add(chat)

                    }
                }
                val chatAdapter = ChatAdapter(this@ChatActivity,chatList)
                binding.chatRecyclerview.adapter= chatAdapter

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}