package comp4097.comp.hkbu.edu.hk.couponredemption.data
import androidx.room.*

@Dao
interface MallDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mall: Mall)

    @Query("Select * from mall group by mall")
    suspend fun getAllMalls(): List<Mall>

    @Query("Select * from mall where mall = :mall group by mall")
    suspend fun getMallByName(mall: String): Mall

    @Delete
    suspend fun delete(vararg mall: Mall)
    @Update
    suspend fun update(mall: Mall)
}