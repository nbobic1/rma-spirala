package ba.etf.rma22.projekat.data.dao

import androidx.room.*
import ba.etf.rma22.projekat.data.models.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg account: Account)
    @Transaction
    @Delete
    suspend fun deleteAccount(account: Account)
    @Query("SELECT *FROM account;")
    suspend fun getAccount():List<Account>
    @Query("DELETE FROM Account;")
    suspend fun deleteAll():Unit

}