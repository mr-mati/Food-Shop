package com.mati.foodshop.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, exportSchema = false, entities = [Food::class])
abstract class myDataBase : RoomDatabase() {

    abstract val foodDao: FoodDao

    companion object {
        val database: myDataBase? = null

        fun getDataBase(context: Context): myDataBase {
            var insert = database
            if (insert == null) {
                insert = Room.databaseBuilder(
                    context.applicationContext,
                    myDataBase::class.java,
                    "myDataBase.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return insert
        }
    }


}