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
import com.furkanbostan.messenger1.model.User
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val context :Context, private val userList:ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val txtUSerNAme :TextView = view.findViewById(R.id.userName)
        val txtTemp :TextView = view.findViewById(R.id.user_temp)
        val imgUser :CircleImageView = view.findViewById(R.id.userImage)
        val layoutUser: LinearLayout= view.findViewById(R.id.layoutUser)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.txtUSerNAme.text = user.username
        holder.txtTemp.text=user.stance
        Picasso.get().load(user.profileImageUrl).into(holder.imgUser);
        holder.layoutUser.setOnClickListener{
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("userId",user.userUid)
            Log.d("Activity",user.userUid)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}