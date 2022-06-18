package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Odgovor

@Dao
interface OdgovorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg odgovor: Odgovor)
    @Transaction
    @Delete
    suspend fun deleteOdgovor(odgovor: List<Odgovor>)

    @Transaction
    @Query("SELECT * FROM odgovor where anketaid=:id")
    suspend fun getOdgovor(id:Int):List<Odgovor>
}