package com.ikhsanulkarim.mynote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 1)
abstract class NotDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        var INSTACE: NotDatabase? = null
        fun getinstance(context: Context):NotDatabase? {
            if (INSTACE == null) {
                synchronized(NotDatabase::class) {
                    INSTACE = Room.databaseBuilder(
                        context.applicationContext,
                        NotDatabase::class.java,
                        "NoteDatabase.db"
                    ).build()

                }
            }
            return INSTACE

        }
    }
}