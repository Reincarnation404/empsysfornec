package com.example.empsysfornec

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.empsysfornec.model.Emp
import java.util.*

class SQLiteHelper(context : Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object{
        private val DB_NAME = "emp"
        private val DB_VERSION = 1
        private val TABLE_NAME = "emplist"
        private val ID = "id"
        private val NAME = "name"
        private val EXNUM = "exNum"
        private val DEPT = "dept"
        private val JOBTITLE = "jobTitle"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT, $EXNUM TEXT, $DEPT TEXT, $JOBTITLE INTEGER);"
        p0?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME;"
        p0?.execSQL(DROP_TABLE)
        onCreate(p0)
    }

    fun getAllData(): MutableList<Emp>{
        val emplist = ArrayList<Emp>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME;"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    val emp = Emp()
                    emp.id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(ID)))
                    emp.name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                    emp.exNum = cursor.getString(cursor.getColumnIndexOrThrow(EXNUM))
                    emp.dept = cursor.getString(cursor.getColumnIndexOrThrow(DEPT))
                    emp.jobTitle = cursor.getInt(cursor.getColumnIndexOrThrow(JOBTITLE))
                    emplist.add(emp)
                }while (cursor.moveToNext())
            }
        }
        cursor.close()
        return emplist
    }

    fun insertData(emps: Emp){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, emps.name)
        values.put(EXNUM, emps.exNum)
        values.put(DEPT, emps.dept)
        values.put(JOBTITLE, emps.jobTitle)
        db.insert(TABLE_NAME,null,values)
        db.close()
    }









}