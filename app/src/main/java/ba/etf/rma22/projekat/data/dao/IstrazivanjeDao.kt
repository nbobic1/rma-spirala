package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Istrazivanje

@Dao
interface IstrazivanjeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg istrazivanje: Istrazivanje)
    @Transaction
    @Delete
    suspend fun deleteIstrazivanje(istrazivanje: Istrazivanje)

    @Transaction
    @Query("SELECT * FROM istrazivanje")
    suspend fun getIstrazivanje():List<Istrazivanje>

    @Transaction
    @Query("SELECT * FROM istrazivanje LIMIT 5 OFFSET :id")
    suspend fun getIstrazivanje(id:Int):List<Istrazivanje>

    @Transaction
    @Query("SELECT * FROM istrazivanje where id=:id")
    suspend fun getIstrazivanjeZaGrupu(id:Int):Istrazivanje
}