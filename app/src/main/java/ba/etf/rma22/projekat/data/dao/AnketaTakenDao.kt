package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken

@Dao
interface AnketaTakenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg anketaTaken: AnketaTaken)
    @Transaction
    @Delete
    suspend fun deleteAnketa(anketaTaken: AnketaTaken)
    @Query("SELECT *FROM anketataken;")
    suspend fun getAnkete():List<AnketaTaken>
}