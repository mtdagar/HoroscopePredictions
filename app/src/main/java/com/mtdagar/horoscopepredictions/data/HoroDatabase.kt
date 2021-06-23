package com.mtdagar.horoscopepredictions.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mtdagar.horoscopepredictions.model.Horo

@Database(entities = [Horo::class], version = 1, exportSchema = false)
abstract class HoroDatabase: RoomDatabase() {

    abstract fun horoDao(): HoroDao

    companion object{
        @Volatile
        private var INSTANCE: HoroDatabase? = null

        //using singleton database instance to improve performance
        fun getDatabase(context: Context): HoroDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HoroDatabase::class.java,
                    "horo_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}