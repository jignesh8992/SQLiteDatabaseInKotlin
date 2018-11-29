package com.example.admin.jnp_kotlin.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSIOM) {

    val TAG = "DB_HELPER_"

    // Create table query
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$ID Integer PRIMARY KEY, " +
            "$USERNAME TEXT," +
            " $PASSWORD TEXT)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Called when the database needs to be upgraded
    }


    /**
     * ToDo.. Check if record already exist in table
     *
     * @param username The username to be check in database
     * @return true if record exist, or false
     */
    fun isExists(username: String): Boolean {
        val db = this.writableDatabase
        val columns = arrayOf<String>(USERNAME)
        val whereClause = USERNAME + " =? "
        val whereArgs = arrayOf(username)

        var cursor: Cursor? = null
        try {
            cursor = db.query(true, TABLE_NAME, columns, whereClause, whereArgs, null, null, null, null)
            return if (cursor!!.getCount() > 0) {
                true
            } else {
                false
            }
        } finally {
            if (cursor != null)
                cursor!!.close()
        }

    }


    /**
     * ToDo.. Get all records of table
     *
     * @return ArrayList<DB_Data> if table has records, or NULL
    </DB_Data> */
    fun getAll(): ArrayList<DB_Data> {

        val recordList = ArrayList<DB_Data>()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    var password = cursor.getString(cursor.getColumnIndex(PASSWORD))

                    var data = DB_Data(id, username, password)
                    recordList.add(data)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return recordList
    }

    /**
     * ToDo.. Insert record in table
     *
     * @param data Object of data
     * @return -1 if insert record failed, or 0
     */
    fun insert(data: DB_Data): Boolean {
        //Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(USERNAME, data.username)
        values.put(PASSWORD, data.password)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v(TAG + "InsertedID", "$success")
        return (Integer.parseInt("$success") != -1)
    }


    /**
     * ToDo.. Update record
     *
     * @param id       The id of record to update new value of that record
     * @param newValue The new value that relace with new value
     * @return -1 if update record failed, or 0
     */

    fun update(data: DB_Data): Boolean {
        val db = this.writableDatabase
        val whereClause = USERNAME + " =? "
        val whereArgs = arrayOf(data.username)
        val values = ContentValues()
        values.put(USERNAME, data.username)
        values.put(PASSWORD, data.password)
        val success = db.update(TABLE_NAME, values, whereClause, whereArgs);
        return (Integer.parseInt("$success") != -1)

    }

    //get all users
    fun getAllString(): String {
        var allUser: String = "";
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID))
                    var username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    var password = cursor.getString(cursor.getColumnIndex(PASSWORD))

                    allUser = "$allUser\n$id $username $password"
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return allUser
    }


    /**
     * ToDo.. Get records by single argument
     *
     * @return DB_Data if table has matching record, or NULL
    </Data> */

    fun getRecord(argument: String): DB_Data {
        val data = DB_Data()
        val db = readableDatabase
        val columns = arrayOf<String>(USERNAME, PASSWORD)
        val whereClause = ID + " =? "
        val whereArgs = arrayOf(argument)

        val cursor = db.query(
            TABLE_NAME, columns, whereClause,
            whereArgs, null, null, null
        )

        while (cursor!!.moveToNext()) {
            val username = cursor!!.getString(cursor!!.getColumnIndex(USERNAME))
            val password = cursor!!.getString(cursor!!.getColumnIndex(PASSWORD))
            data.username = username
            data.password = password
        }
        if (cursor != null) cursor!!.close()
        return data
    }


    /**
     * ToDo.. Delete record
     *
     * @param id The id of record to be deleted
     * @return -1 if update record failed, or 0
     */

    fun delete(id: String): Boolean {
        val db = this.writableDatabase
        val whereClause = ID + " =? "
        val whereArgs = arrayOf(id)
        val success = db.delete(TABLE_NAME, whereClause, whereArgs)
        return (Integer.parseInt("$success") != -1)
    }

    companion object {
        private val DB_NAME = "JNP_DB"    // Name of Database
        private val DB_VERSIOM = 1;  // Current Version of Database
        private val TABLE_NAME = "jnp_table" // Table username  of Database

        // Table fields of Database
        private val ID = "id"
        private val USERNAME = "UserName"
        private val PASSWORD = "Password"


    }


    class DB_Data {
        var id: String = ""
        var username: String? = null
        var password: String? = null

        constructor() {}

        constructor(id: String, username: String, password: String) {
            this.id = id
            this.username = username
            this.password = password
        }
    }


}