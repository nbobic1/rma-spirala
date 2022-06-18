package ba.etf.rma22.projekat.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ba.etf.rma22.projekat.data.models.*
import ba.etf.rma22.projekat.data.models.Odgovor


@Database(entities = arrayOf(Anketa::class, Odgovor::class,Grupa::class,Istrazivanje::class,
    Pitanje::class,AnketaTaken::class,Account::class), version = 10)
@TypeConverters(Converter   ::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun anketaDao(): AnketaDao
    abstract fun grupaDao():GrupeDao
    abstract fun pitanjeDao():PitanjeDao
    abstract fun anketaTakenDao(): AnketaTakenDao
    abstract fun odgovorDao():OdgovorDao
    abstract fun accountDao():AccountDao
    abstract fun istrazivanjeDao():IstrazivanjeDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context!!.applicationContext,
                AppDatabase::class.java,
                "RMA22DB"
            ).fallbackToDestructiveMigration().build()
    }
}