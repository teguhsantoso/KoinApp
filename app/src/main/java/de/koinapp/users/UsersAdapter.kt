package de.koinapp.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.koinapp.R
import de.koinapp.model.GithubUser
import kotlinx.android.synthetic.main.row_user.view.*


class UsersAdapter(var users: List<GithubUser>, private val clickListener: (GithubUser) -> Unit) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position], clickListener)
    }

    override fun onCreateViewHolder(view: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(view.context).inflate(R.layout.row_user, view, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun update(data: List<GithubUser>){
        users= data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: GithubUser, clickListener: (GithubUser) -> Unit) {
            itemView.text_username.text = user.login
            Glide.with(itemView.avatar.context).load(user.avatar_url).into(itemView.avatar)
            itemView.setOnClickListener { clickListener(user)}
        }
    }
}

