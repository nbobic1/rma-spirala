package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Anketa

@Dao
interface AnketaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg anketa: Anketa)
    @Transaction
    @Delete
    suspend fun deleteAnketa(anketa: Anketa)
    @Query("SELECT *FROM anketa;")
    suspend fun getAll():List<Anketa>
    @Query("SELECT *FROM anketa LIMIT 5 OFFSET :offset;")
    suspend fun getAllO(offset:Int):List<Anketa>

    @Query("SELECT *FROM anketa where id=:id;")
    suspend fun getById(id:Int):Anketa


    @Query("SELECT *FROM anketa where   upisan=1;")
    suspend fun getUpisane():List<Anketa>

    @Query("SELECT *FROM anketa where  grupaId=:id;")
    suspend fun getAnketeGrupe(id:Int):List<Anketa>

}