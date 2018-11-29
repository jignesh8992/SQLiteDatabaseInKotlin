package com.example.admin.jnp_kotlin.activity

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import com.example.admin.jnp_kotlin.R
import com.example.admin.jnp_kotlin.db.DatabaseHandler
import com.example.admin.jnp_kotlin.utils.AppHelper
import kotlinx.android.synthetic.main.activity_update.*


class UpdateActivity : Activity() {

    val mContext = this
    var dbHandler: DatabaseHandler? = null
    var id = "";
    var username = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        initActions();
        dbHandler = DatabaseHandler(mContext)
        initData();
    }

    private fun initData() {
        id = intent.getStringExtra("id")
        var data = dbHandler!!.getRecord(id);
        username = data.username.toString();
        tv_username.append(username)
        et_password.text = Editable.Factory.getInstance().newEditable(data.password)

    }

    private fun initActions() {
        button_update.setOnClickListener {
            // checking input text should not be null
            val username = tv_username.text.toString();
            val password = et_password.text.toString();

            if (validation(username, password)) {
                val user: DatabaseHandler.DB_Data = DatabaseHandler.DB_Data()
                user.id = id
                user.username = username
                user.password = password
                var success: Boolean = false
                success = dbHandler!!.update(user)
                if (success) {
                    AppHelper.toast(this, "Record Updated Successfully")
                    onBackPressed()

                } else {
                    AppHelper.toast(this, "Record not Updated")
                    onBackPressed()
                }
            } else {
                AppHelper.toast(this, "Fill all details");
            }

        }

        button_delete.setOnClickListener {
            var success: Boolean = false
            success = dbHandler!!.delete(id)
            if (success) {
                AppHelper.toast(this, "Record Deleted Successfully")
                onBackPressed()

            } else {
                AppHelper.toast(this, "Record not Deleted")
                onBackPressed()
            }
        }

        iv_back.setOnClickListener { onBackPressed() }

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
}
