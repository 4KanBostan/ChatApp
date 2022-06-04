package com.furkanbostan.messenger1.adapter

import android.content.Context
import android.content.Intent
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.furkanbostan.messenger1.R
import com.furkanbostan.messenger1.activity.ChatActivity
import com.furkanbostan.messenger1.model.Chat
import com.furkanbostan.messenger1.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ChatAdapter(private val context :Context, private val chatList:ArrayList<Chat>):
    RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    private val MESSAGE_TYPE_LEFT = 0
    private val MESSAGE_TYPE_RIGHT=1
    var firebaseUser:FirebaseUser?=null


    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val txtUSerNAme :TextView = view.findViewById(R.id.tvMessage)

        val imgUser :CircleImageView = view.findViewById(R.id.userImage)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType==MESSAGE_TYPE_RIGHT){
            var view = LayoutInflater.from(parent.context).inflate(R.layout.right_side,parent,false)
            return ViewHolder(view)
        }else{
            var view = LayoutInflater.from(parent.context).inflate(R.layout.left_side,parent,false)
            return ViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]
        holder.txtUSerNAme.text =chat.message

        //Picasso.get().load(user.profileImageUrl).into(holder.imgUser);
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        firebaseUser= FirebaseAuth.getInstance().currentUser
        if (chatList[position].senderId== firebaseUser!!.uid){
            return MESSAGE_TYPE_RIGHT
        }else
            return MESSAGE_TYPE_LEFT
    }
}