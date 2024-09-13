package com.example.ktorprac

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ktorprac.databinding.ListviewBinding

class RvAdapter (private val userList: List<PostItem>) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListviewBinding) :
    RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.apply{
            tvId.text = "Id: ${currentItem.id}"
            tvUserId.text = "User Id: ${currentItem.id}"
            tvTitle.text = "Title: ${currentItem.name}"
            tvEmail.text = "Email: ${currentItem.email}"
            tvBody.text = "Body: ${currentItem.body}"
        }
    }
}