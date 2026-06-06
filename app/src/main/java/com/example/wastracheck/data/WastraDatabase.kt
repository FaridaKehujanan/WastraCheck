package com.example.wastracheck.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class WastraDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: WastraDatabase? = null

        fun getDatabase(context: Context): WastraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WastraDatabase::class.java,
                    "wastra_database"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Menyisipkan data dummy saat database pertama kali dibuat
                        db.execSQL("INSERT INTO users (email, name, password) VALUES ('admin@gmail.com', 'Admin Wastra', 'admin123')")
                        db.execSQL("INSERT INTO users (email, name, password) VALUES ('user@gmail.com', 'User Dummy', 'user123')")
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
