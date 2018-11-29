package com.example.admin.jnp_kotlin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.jnp_kotlin.R
import com.example.admin.jnp_kotlin.db.DatabaseHandler
import com.example.admin.jnp_kotlin.utils.RVClickListener
import kotlinx.android.synthetic.main.rv_record.view.*

class DBAdapter(
    val mContext: Context,
    var dbRecord: ArrayList<DatabaseHandler.DB_Data>,
    val rvClickListener: RVClickListener
) :
    RecyclerView.Adapter<DBAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id = itemView.tv_id
        val username = itemView.tv_username
        val password = itemView.tv_password
        val linear_container = itemView.linear_container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.rv_record, parent, false))
    }

    override fun getItemCount(): Int {
        return dbRecord.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dbRecord[position]
        holder.id.text = data.id.toString()
        holder.username.text = data.username
        holder.password.text = data.password
        holder.linear_container.setOnClickListener { rvClickListener.onItemClick(data.id.toString()) }
    }

    fun setData(dbRecord: ArrayList<DatabaseHandler.DB_Data>) {
        this.dbRecord = dbRecord;
        notifyDataSetChanged()

    }
}