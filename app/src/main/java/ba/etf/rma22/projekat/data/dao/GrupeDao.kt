package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Grupa

@Dao
interface GrupeDao {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg grupa: Grupa)
    @Transaction
    @Delete
    suspend fun deleteGrupe(grupa: Grupa)
    @Transaction
    @Query("SELECT * FROM grupa")
    suspend fun getGrupe():List<Grupa>

    @Transaction
    @Query("SELECT * FROM grupa where upisan=1;")
    suspend fun getGrupeUpisane():List<Grupa>
    @Transaction
    @Query("SELECT * FROM grupa where id=:id;")
    suspend fun getGrupeAnketa(id:Int):List<Grupa>
}