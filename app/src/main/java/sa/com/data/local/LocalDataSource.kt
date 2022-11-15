package sa.com.data.local

import sa.com.data.local.articles.ArticleEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val appDB: AppDatabase
) {
    suspend fun saveArticles(articles: List<ArticleEntity>) {
        articles.forEach { appDB.articleDAO().insert(it) }
    }

    suspend fun getArticles(): List<ArticleEntity> {
        return appDB.articleDAO().getAll().map { it }
    }

}