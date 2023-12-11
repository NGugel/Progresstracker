package com.dhbw.progresstracker.repository.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database(
    entities = [Kategorie::class,
                Frage::class
               ],
    version = 2,
    exportSchema = false
)

abstract class LocalDatabase:RoomDatabase() {

   abstract val kategorieDao: KategorieDao
   abstract val frageDao: FrageDao

   companion object
   {
       @Volatile
       private var INSTANCE: LocalDatabase? = null

       fun createInstance(application: Application, scope: CoroutineScope): LocalDatabase
       {
           synchronized(this)
           {
               var instance = INSTANCE
               if(instance ==  null)
               {
                   instance = Room.databaseBuilder(
                    application.applicationContext,
                       LocalDatabase::class.java,
                       "local_database"
                   ).fallbackToDestructiveMigration()
                       .build()
                   INSTANCE = instance
               }
                return instance
           }
       }
   }
}

