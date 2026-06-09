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

@Database(entities = [UserEntity::class, WastraMotif::class], version = 4, exportSchema = false)
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
                        db.execSQL("INSERT INTO users (email, name, password) VALUES ('admin@gmail.com', 'Admin Wastra', 'admin123')")
                        db.execSQL("INSERT INTO users (email, name, password) VALUES ('user@gmail.com', 'User Dummy', 'user123')")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val initialMotifs = listOf(
                                    // 10 Batik Awal
                                    WastraMotif("1", "Parang Kusumo", "Solo", "Melambangkan perjuangan jiwa melawan hawa nafsu.", "Mencerminkan kekuasaan, kekuatan, serta bangsawan.", R.drawable.parang_kusumo, "Jawa Tengah"),
                                    WastraMotif("2", "Mega Mendung", "Cirebon", "Melambangkan dunia atas yang luas dan bebas.", "Maknanya adalah manusia harus mampu meredam amarah dalam situasi apa pun.", R.drawable.mega_mendung, "Jawa Barat"),
                                    WastraMotif("3", "Sido Mukti", "Solo", "Harapan mencapai kebahagiaan lahir batin.", "Melambangkan harapan untuk mencapai kehidupan yang makmur dan sejahtera.", R.drawable.sido_mukti, "Jawa Tengah"),
                                    WastraMotif("4", "Sekar Jagad", "Yogyakarta", "Melambangkan keragaman suku bangsa di dunia.", "Melambangkan keanekaragaman dan keindahan seluruh dunia yang bersatu.", R.drawable.sekar_jagad, "Jawa Tengah"),
                                    WastraMotif("5", "Kawung", "Yogyakarta", "Melambangkan keadilan dan keperkasaan.", "Melambangkan kesucian, kejujuran, keadilan, dan keperkasaan.", R.drawable.kawung, "Jawa Tengah"),
                                    WastraMotif("6", "Truntum", "Solo", "Melambangkan cinta yang tumbuh kembali.", "Melambangkan cinta yang tumbuh kembali atau bersemi kembali.", R.drawable.truntum, "Jawa Tengah"),
                                    WastraMotif("7", "Sidoluhur", "Solo", "Melambangkan harapan untuk menjadi teladan.", "Harapan agar pemakainya menjadi orang yang berbudi luhur dan terhormat.", R.drawable.sidoluhur, "Jawa Tengah"),
                                    WastraMotif("8", "Slobog", "Solo", "Melambangkan keteguhan hati.", "Harapan agar arwah mendapat kelonggaran saat menghadap Tuhan.", R.drawable.slobog, "Jawa Tengah"),
                                    WastraMotif("9", "Pring Sedapur", "Magetan", "Melambangkan persatuan dan kerukunan.", "Melambangkan kekuatan dan kerukunan antar sesama seperti bambu yang berumpun.", R.drawable.pring_sedapur, "Jawa Timur"),
                                    WastraMotif("10", "Sidomulyo", "Solo", "Melambangkan kemuliaan hidup.", "Harapan agar pemakainya mendapatkan kemuliaan dan hidup berkecukupan.", R.drawable.sidomulyo, "Jawa Tengah"),
                                    
                                    // 10 Batik Tambahan (Baru)
                                    WastraMotif("11", "Tujuh Rupa", "Pekalongan", "Nuansa alam yang sangat kental.", "Melambangkan kelembutan dan perpaduan budaya lokal dengan etnis Cina.", null, "Jawa Tengah"),
                                    WastraMotif("12", "Lasem", "Rembang", "Warna merah khas yang dominan.", "Melambangkan akulturasi budaya Tionghoa dan Jawa yang harmonis.", null, "Jawa Tengah"),
                                    WastraMotif("13", "Gentongan", "Madura", "Warna cerah dan kontras.", "Melambangkan kebebasan dan kreativitas masyarakat pesisir Madura.", null, "Jawa Timur"),
                                    WastraMotif("14", "Simbut", "Banten", "Bentuk daun yang menyerupai daun talas.", "Berasal dari suku Baduy, melambangkan kesederhanaan dan kedekatan dengan alam.", null, "Banten"),
                                    WastraMotif("15", "Priyangan", "Tasikmalaya", "Corak yang rapi dan simetris.", "Mencerminkan kepribadian wanita Sunda yang kalem, cantik, dan rapi.", null, "Jawa Barat"),
                                    WastraMotif("16", "Tambal", "Yogyakarta", "Menambal atau memperbaiki hal yang rusak.", "Keyakinan bahwa kain ini dapat menyembuhkan orang yang sedang sakit.", null, "Jawa Tengah"),
                                    WastraMotif("17", "Cendrawasih", "Papua", "Keindahan burung Cendrawasih.", "Melambangkan kekayaan alam dan kebanggaan masyarakat Papua.", null, "Papua"),
                                    WastraMotif("18", "Ulamsari Mas", "Bali", "Gambar ikan dan udang.", "Melambangkan kesejahteraan dan kekayaan sumber daya laut di Bali.", null, "Bali"),
                                    WastraMotif("19", "Singa Barong", "Cirebon", "Wujud mahluk mitologi.", "Melambangkan kekuatan, kebijaksanaan, dan perlindungan terhadap roh jahat.", null, "Jawa Barat"),
                                    WastraMotif("20", "Sogan", "Solo", "Warna dominan cokelat dan keemasan.", "Melambangkan kerendahhatian dan kedekatan manusia dengan tanah (bumi).", null, "Jawa Tengah")
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
