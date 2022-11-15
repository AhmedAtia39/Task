package sa.com.data.remote

import com.slack.eithernet.ApiResult
import sa.com.data.remote.model.ApiError
import sa.com.data.remote.model.NewsObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val api: ArticlesApi,
) {
    suspend fun getArticles(): ApiResult<NewsObject, ApiError> {
        return api.getArticles(
        )
    }
}