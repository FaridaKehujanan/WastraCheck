package com.example.wastracheck.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wastracheck.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class, WastraMotif::class], version = 3, exportSchema = false)
abstract class WastraDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun motifDao(): MotifDao

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
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Pre-populate users
                        db.execSQL("INSERT INTO users (email, name, password) VALUES ('admin@gmail.com', 'Admin Wastra', 'admin123')")
                        db.execSQL("INSERT INTO users (email, name, password) VALUES ('user@gmail.com', 'User Dummy', 'user123')")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        // Pastikan data batik selalu ada jika tabel kosong
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val initialMotifs = listOf(
                                    WastraMotif("1", "Parang Kusumo", "Solo", "Melambangkan perjuangan jiwa melawan hawa nafsu.", "Mencerminkan kekuasaan, kekuatan, serta bangsawan. Memiliki makna bahwa hidup harus dilandasi oleh perjuangan untuk mencari keharuman lahir dan batin.", R.drawable.parang_kusumo, "Jawa Tengah"),
                                    WastraMotif("2", "Mega Mendung", "Cirebon", "Melambangkan dunia atas yang luas dan bebas.", "Bentuk awan merupakan gambaran dunia atas. Maknanya adalah setiap manusia harus mampu meredam amarah atau emosinya dalam situasi apa pun.", R.drawable.mega_mendung, "Jawa Barat"),
                                    WastraMotif("3", "Sido Mukti", "Solo", "Harapan agar pemakainya mencapai kebahagiaan lahir batin.", "Berasal dari kata 'sido' (menjadi) dan 'mukti' (mulia/sejahtera). Melambangkan harapan untuk mencapai kehidupan yang makmur dan sejahtera.", R.drawable.sido_mukti, "Jawa Tengah"),
                                    WastraMotif("4", "Sekar Jagad", "Yogyakarta", "Melambangkan keragaman suku bangsa di dunia.", "Berasal dari kata 'kar' (peta) dan 'jagad' (dunia). Melambangkan keanekaragaman dan keindahan seluruh dunia yang bersatu.", R.drawable.sekar_jagad, "Jawa Tengah"),
                                    WastraMotif("5", "Kawung", "Yogyakarta", "Melambangkan keadilan dan keperkasaan.", "Bentuk bulat lonjong terinspirasi dari buah kolang-kaling. Melambangkan kesucian, kejujuran, keadilan, dan keperkasaan.", R.drawable.kawung, "Jawa Tengah"),
                                    WastraMotif("6", "Truntum", "Solo", "Melambangkan cinta yang tumbuh kembali.", "Diciptakan oleh Ratu Kencana. Melambangkan cinta yang tumbuh kembali atau bersemi kembali (tumaruntum).", R.drawable.truntum, "Jawa Tengah"),
                                    WastraMotif("7", "Sidoluhur", "Solo", "Melambangkan harapan untuk menjadi teladan.", "Melambangkan harapan agar pemakainya menjadi orang yang berbudi pekuhur dan terhormat, serta menjadi teladan bagi sesama.", R.drawable.sidoluhur, "Jawa Tengah"),
                                    WastraMotif("8", "Slobog", "Solo", "Melambangkan keteguhan hati.", "Berasal dari kata 'lobok' (longgar). Melambangkan harapan agar arwah orang yang meninggal mendapat kemudahan dan kelonggaran saat menghadap Tuhan.", R.drawable.slobog, "Jawa Tengah"),
                                    WastraMotif("9", "Pring Sedapur", "Magetan", "Melambangkan persatuan dan kerukunan.", "Motif bambu (pring). Melambangkan persatuan, kekuatan, dan kerukunan antar sesama seperti bambu yang tumbuh berumpun.", R.drawable.pring_sedapur, "Jawa Timur"),
                                    WastraMotif("10", "Sidomulyo", "Solo", "Melambangkan kemuliaan dan hidup berkecukupan.", "Melambangkan harapan agar pemakainya mendapatkan kemuliaan dan hidup berkecukupan.", R.drawable.sidomulyo, "Jawa Tengah")
                                )
                                database.motifDao().insertMotifs(initialMotifs)
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
