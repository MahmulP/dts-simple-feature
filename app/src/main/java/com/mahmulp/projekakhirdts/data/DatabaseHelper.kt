package com.mahmulp.projekakhirdts.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, USER_DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $USER_TABLE_NAME (" +
                "$USER_COL_1 INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$USER_COL_2 TEXT, " +
                "$USER_COL_3 TEXT)"
        )

        db?.execSQL("CREATE TABLE $TABLE_NAME (" +
                "$COL_1 INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_2 TEXT, " +
                "$COL_3 INTEGER, " +
                "$COL_4 TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME)
        onCreate(db)

        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertUser(user: String, password: String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val contentValues = ContentValues().apply {
            put(USER_COL_2, user)
            put(USER_COL_3, password)
        }
        val result = db.insert(USER_TABLE_NAME, null, contentValues)
        return result != (-1).toLong()
    }

    fun insertData(name: String, age: Int, motto: String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_2, name)
            put(COL_3, age)
            put(COL_4, motto)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != (-1).toLong()
    }

    fun updateData(id: String, name: String, age: Int, motto: String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_1, id)
            put(COL_2, name)
            put(COL_3, age)
            put(COL_4, motto)
        }
        val result = db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    fun deleteData(id: String): Int {
        val db: SQLiteDatabase = writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    fun getAllData(): Cursor {
        val db: SQLiteDatabase = writableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    fun checkUser(user: String): Boolean {
        val db = writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $USER_TABLE_NAME WHERE $USER_COL_2 = ?", arrayOf(user))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun checkUserPassword(user: String, password: String): Boolean {
        val db = writableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $USER_TABLE_NAME WHERE $USER_COL_2 = ? AND $USER_COL_3 = ?", arrayOf(user, password))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    companion object {
        const val USER_DATABASE_NAME = "AppMHS"
        const val USER_TABLE_NAME = "tbluser"
        const val USER_COL_1 = "ID"
        const val USER_COL_2 = "USER"
        const val USER_COL_3 = "PASSWORD"
        const val DATABASE_VERSION = 2

        const val TABLE_NAME = "tbldata"
        const val COL_1 = "ID"
        const val COL_2 = "NAMA"
        const val COL_3 = "UMUR"
        const val COL_4 = "MOTTO"

    }
}
