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

@Database(entities = [UserEntity::class, WastraMotif::class], version = 5, exportSchema = false)
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
                        db.execSQL("INSERT OR REPLACE INTO users (email, name, password) VALUES ('admin@gmail.com', 'Admin Wastra', 'admin123')")
                        db.execSQL("INSERT OR REPLACE INTO users (email, name, password) VALUES ('user@gmail.com', 'User Dummy', 'user123')")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                // Pastikan user dummy tersedia setiap kali database dibuka
                                database.userDao().register(UserEntity("admin@gmail.com", "Admin Wastra", "admin123"))
                                database.userDao().register(UserEntity("user@gmail.com", "User Dummy", "user123"))

                                val initialMotifs = listOf(
                                    // 10 Batik Awal
                                    WastraMotif("1", "Parang Kusumo", "Solo", "Melambangkan perjuangan jiwa melawan hawa nafsu.", "Mencerminkan kekuasaan, kekuatan, serta bangsawan.", R.drawable.parang_kusumo, "Jawa Tengah", "Digunakan dalam upacara pelantikan raja dan bangsawan.", R.drawable.parangkusumo1, "Sering dipadukan dengan kemeja atau blazer untuk kesan formal modern.", R.drawable.parangkusumo2),
                                    WastraMotif("2", "Mega Mendung", "Cirebon", "Melambangkan dunia atas yang luas dan bebas.", "Maknanya adalah manusia harus mampu meredam amarah dalam situasi apa pun.", R.drawable.mega_mendung, "Jawa Barat", "Digunakan dalam upacara adat di daerah Cirebon.", R.drawable.megamendung1, "Sangat populer digunakan sebagai motif kemeja kasual atau dress.", R.drawable.megamendung2),
                                    WastraMotif("3", "Sido Mukti", "Solo", "Harapan mencapai kebahagiaan lahir batin.", "Melambangkan harapan untuk mencapai kehidupan yang makmur dan sejahtera.", R.drawable.sido_mukti, "Jawa Tengah", "Sering digunakan oleh pengantin dalam upacara pernikahan adat Jawa.", R.drawable.sidomukti1, "Bisa digunakan sebagai kain bawahan atau rok lilit untuk acara semi-formal.", R.drawable.sidomukti2),
                                    WastraMotif("4", "Sekar Jagad", "Yogyakarta", "Melambangkan keragaman suku bangsa di dunia.", "Melambangkan keanekaragaman dan keindahan seluruh dunia yang bersatu.", R.drawable.sekar_jagad, "Jawa Tengah", "Digunakan dalam acara perhelatan besar atau pesta rakyat.", R.drawable.sekarjagat1, "Cocok untuk motif tas, outer, atau aksesoris fashion etnik.", R.drawable.sekarjagat2),
                                    WastraMotif("5", "Kawung", "Yogyakarta", "Melambangkan keadilan dan keperkasaan.", "Melambangkan kesucian, kejujuran, keadilan, dan keperkasaan.", R.drawable.kawung, "Jawa Tengah", "Dahulu hanya digunakan oleh kalangan pejabat kerajaan.", R.drawable.kawung1, "Populer digunakan untuk seragam kantor atau pakaian kerja formal.", R.drawable.kawung2),
                                    WastraMotif("6", "Truntum", "Solo", "Melambangkan cinta yang tumbuh kembali.", "Melambangkan cinta yang tumbuh kembali atau bersemi kembali.", R.drawable.truntum, "Jawa Tengah", "Wajib dipakai oleh orang tua pengantin dalam pernikahan adat Jawa.", R.drawable.truntum1, "Bisa diaplikasikan pada motif selendang atau syal untuk gaya elegan.", R.drawable.truntum2),
                                    WastraMotif("7", "Sidoluhur", "Solo", "Melambangkan harapan untuk menjadi teladan.", "Harapan agar pemakainya menjadi orang yang berbudi luhur dan terhormat.", R.drawable.sidoluhur, "Jawa Tengah", "Dipakai dalam prosesi adat yang sakral dan penuh doa.", R.drawable.sidoluhur1, "Bagus untuk kemeja formal pria atau tunik wanita.", R.drawable.sidoluhur2),
                                    WastraMotif("8", "Slobog", "Solo", "Melambangkan keteguhan hati.", "Harapan agar arwah mendapat kelonggaran saat menghadap Tuhan.", R.drawable.slobog, "Jawa Tengah", "Biasanya digunakan dalam upacara pemakaman.", R.drawable.slobog1, "Kurang umum untuk harian, namun bisa untuk koleksi edukasi.", R.drawable.slobog2),
                                    WastraMotif("9", "Pring Sedapur", "Magetan", "Melambangkan persatuan dan kerukunan.", "Melambangkan kekuatan dan kerukunan antar sesama seperti bambu yang berumpun.", R.drawable.pring_sedapur, "Jawa Timur", "Digunakan dalam syukuran desa atau acara komunitas.", R.drawable.pringsedapur1, "Sangat manis untuk motif dress musim panas atau kemeja santai.", R.drawable.pringsedapur2),
                                    WastraMotif("10", "Sidomulyo", "Solo", "Melambangkan kemuliaan hidup.", "Harapan agar pemakainya mendapatkan kemuliaan dan hidup berkecukupan.", R.drawable.sidomulyo, "Jawa Tengah", "Digunakan dalam upacara perkawinan agar hidup mulia.", R.drawable.sidomulyo1, "Elegan sebagai atasan formal untuk acara pesta.", R.drawable.sidomulyo2),
                                    
                                    // 10 Batik Tambahan
                                    WastraMotif("11", "Tujuh Rupa", "Pekalongan", "Nuansa alam yang sangat kental.", "Melambangkan kelembutan dan perpaduan budaya lokal dengan etnis Cina.", R.drawable.tujuhrupa, "Jawa Tengah", "Digunakan dalam acara perayaan budaya pesisir.", R.drawable.tujuhrupa1, "Sangat modis untuk dijadikan blouse atau kemeja warna cerah.", R.drawable.tujuhrupa2),
                                    WastraMotif("12", "Lasem", "Rembang", "Warna merah khas yang dominan.", "Melambangkan akulturasi budaya Tionghoa dan Jawa yang harmonis.", R.drawable.lasem, "Jawa Tengah", "Dipakai dalam perayaan akulturasi budaya.", R.drawable.lasem1, "Warna merahnya cocok untuk outfit pesta atau acara imlek.", R.drawable.lasem2),
                                    WastraMotif("13", "Gentongan", "Madura", "Warna cerah dan kontras.", "Melambangkan kebebasan dan kreativitas masyarakat pesisir Madura.", R.drawable.gentongan, "Jawa Timur", "Digunakan dalam festival budaya Madura.", R.drawable.gentongan1, "Cocok untuk gaya berani dan artistik.", R.drawable.gentongan2),
                                    WastraMotif("14", "Simbut", "Banten", "Bentuk daun yang menyerupai daun talas.", "Berasal dari suku Baduy, melambangkan kesederhanaan dan kedekatan dengan alam.", R.drawable.simbut, "Banten", "Digunakan dalam ritual adat suku Baduy.", R.drawable.simbut1, "Kesan etnik yang kuat untuk outer atau jaket motif.", R.drawable.simbut2),
                                    WastraMotif("15", "Priyangan", "Tasikmalaya", "Corak yang rapi dan simetris.", "Mencerminkan kepribadian wanita Sunda yang kalem, cantik, dan rapi.", R.drawable.priyangan, "Jawa Barat", "Digunakan dalam acara formal di lingkungan Jawa Barat.", R.drawable.priyangan1, "Sangat rapi untuk seragam kantoran.", R.drawable.priyangan2),
                                    WastraMotif("16", "Tambal", "Yogyakarta", "Menambal atau memperbaiki hal yang rusak.", "Keyakinan bahwa kain ini dapat menyembuhkan orang yang sedang sakit.", R.drawable.tambal, "Jawa Tengah", "Digunakan sebagai selimut untuk orang sakit agar cepat sembuh.", R.drawable.tambal, "Unik sebagai aksen patchwork pada fashion modern.", R.drawable.tambal),
                                    WastraMotif("17", "Cendrawasih", "Papua", "Keindahan burung Cendrawasih.", "Melambangkan kekayaan alam dan kebanggaan masyarakat Papua.", R.drawable.cendrawasih, "Papua", "Digunakan dalam tarian adat Papua.", R.drawable.cendrawasih, "Menonjol sebagai motif kemeja atau dress pesta malam.", R.drawable.cendrawasih),
                                    WastraMotif("18", "Ulamsari Mas", "Bali", "Gambar ikan and udang.", "Melambangkan kesejahteraan dan kekayaan sumber daya laut di Bali.", R.drawable.ulamsari, "Bali", "Digunakan dalam upacara keagamaan di Bali.", R.drawable.ulamsari, "Segar sebagai motif baju pantai atau kemeja santai.", R.drawable.ulamsari),
                                    WastraMotif("19", "Singa Barong", "Cirebon", "Wujud mahluk mitologi.", "Melambangkan kekuatan, kebijaksanaan, dan perlindungan terhadap roh jahat.", R.drawable.singa_barong, "Jawa Barat", "Digunakan dalam acara keraton Cirebon.", R.drawable.singa_barong, "Kesan kuat dan berwibawa untuk pakaian formal.", R.drawable.singa_barong),
                                    WastraMotif("20", "Sogan", "Solo", "Warna dominan cokelat dan keemasan.", "Melambangkan kerendahhatian dan kedekatan manusia dengan tanah (bumi).", R.drawable.sogan, "Jawa Tengah", "Digunakan dalam berbagai upacara keraton Solo.", R.drawable.sogan, "Sangat klasik untuk kemeja batik pria standar formal.", R.drawable.sogan)
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
