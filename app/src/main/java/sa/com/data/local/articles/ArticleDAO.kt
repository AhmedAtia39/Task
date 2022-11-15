package sa.com.data.local.articles

import androidx.room.*

@Dao
interface ArticleDAO {

    @Query("SELECT * FROM `articles`")
    suspend fun getAll(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)
}