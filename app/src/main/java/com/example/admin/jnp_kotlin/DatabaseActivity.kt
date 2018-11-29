package com.example.admin.jnp_kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import com.example.admin.jnp_kotlin.activity.UpdateActivity
import com.example.admin.jnp_kotlin.adapter.DBAdapter
import com.example.admin.jnp_kotlin.db.DatabaseHandler
import com.example.admin.jnp_kotlin.utils.AppHelper
import com.example.admin.jnp_kotlin.utils.RVClickListener
import com.example.admin.jnp_kotlin.utils.RVGridSpacing
import kotlinx.android.synthetic.main.activity_database.*


class DatabaseActivity : Activity() {

    val mContext = this
    var adapter: DBAdapter? = null;
    var dbHandler: DatabaseHandler? = null
    var recordList: ArrayList<DatabaseHandler.DB_Data> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)
        setupViews();
        initActions();
        dbHandler = DatabaseHandler(mContext)


    }

    private fun initActions() {
        button_save.setOnClickListener {
            // checking input text should not be null
            val username = et_username.text.toString();
            val password = et_password.text.toString();

            if (validation(username, password)) {
                if (dbHandler!!.isExists(username)) {
                    AppHelper.toast(this, "Username Already Exist");
                } else {
                    val user: DatabaseHandler.DB_Data = DatabaseHandler.DB_Data()
                    user.username = username
                    user.password = password

                    var success: Boolean = false
                    success = dbHandler!!.insert(user)

                    if (success) {
                        AppHelper.toast(this, "Record inserted Successfully")

                        et_username.text = Editable.Factory.getInstance().newEditable("")
                        et_password.text = Editable.Factory.getInstance().newEditable("")
                        et_username.requestFocus();

                        recordList = dbHandler!!.getAll()
                        adapter!!.setData(recordList)
                    }
                }
            } else {
                AppHelper.toast(this, "Fill all details");
            }

        }

    }

    private fun setupViews() {
        rv_data.layoutManager = LinearLayoutManager(mContext)
        rv_data.addItemDecoration(RVGridSpacing(1, 5, true))
    }

    fun validation(username: String, password: String): Boolean {
        var validate = false
        if (!username.equals("") && !password.equals("")) {
            validate = true
        } else {
            validate = false
        }
        return validate
    }

    override fun onResume() {
        super.onResume()
        recordList = dbHandler!!.getAll();
        adapter = DBAdapter(mContext, recordList, object : RVClickListener {
            override fun onItemClick(id: String) {
                val intent = Intent(mContext, UpdateActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })
        rv_data.adapter = adapter
    }
}


