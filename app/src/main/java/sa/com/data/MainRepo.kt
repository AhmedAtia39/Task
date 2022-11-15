package sa.com.data

import com.slack.eithernet.ApiResult
import sa.com.data.local.LocalDataSource
import sa.com.data.local.articles.ArticleEntity
import sa.com.data.remote.RemoteDataSource
import sa.com.data.remote.model.ApiError
import sa.com.data.remote.model.NewsObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepo @Inject constructor(
    private val remoteDS: RemoteDataSource,
    private val localDS: LocalDataSource,
) {

    suspend fun getLocalArticles(): List<ArticleEntity> {
        val list = localDS.getArticles()
       return  list
    }

    suspend fun saveArticles(articles: List<ArticleEntity>) {
        localDS.saveArticles(articles)
    }

    suspend fun getRemoteArticles(): ApiResult<NewsObject, ApiError> {
        return  remoteDS.getArticles()
    }

}