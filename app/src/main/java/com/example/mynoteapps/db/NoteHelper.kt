package com.example.mynoteapps.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.mynoteapps.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.example.mynoteapps.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException
import kotlin.jvm.Throws

class NoteHelper(context: Context) {

    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var dataBase: SQLiteDatabase


    //buat 1 metode yang nantinya akan digunakan untuk menginisiasi database.
    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: NoteHelper(context)
            }
    }

    //metode untuk membuka dan menutup koneksi ke database-nya.
    @Throws(SQLException::class)
    fun open(){
        dataBase = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (dataBase.isOpen)
            dataBase.close()
    }

    //metode untuk melakukan proses CRUD-nya

    //1. untuk mengambil data
    fun quaryAll(): Cursor{
        return dataBase.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    //2. untuk mengambil data dengan id tertentu
    fun queryById(id: String): Cursor{
        return dataBase.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
            null
        )
    }

    //3. untuk menyimpan data
    fun insert(values: ContentValues?): Long{
        return dataBase.insert(DATABASE_TABLE, null, values)
    }

    //4. untuk memperbarui data
    fun update(id: String, values: ContentValues?): Int{
        return dataBase.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    //5. untuk menghapus data
    fun deleteById(id: String): Int{
        return dataBase.delete(DATABASE_TABLE, "$_ID = '$id'", null)
    }
}