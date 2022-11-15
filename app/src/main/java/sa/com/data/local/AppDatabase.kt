package sa.com.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sa.com.data.local.articles.ArticleDAO
import sa.com.data.local.articles.ArticleEntity

@Database(entities = [ ArticleEntity::class ], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDAO(): ArticleDAO
}