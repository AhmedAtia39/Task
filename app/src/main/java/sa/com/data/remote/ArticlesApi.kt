package sa.com.data.remote

import com.slack.eithernet.ApiResult
import retrofit2.http.GET
import sa.com.data.remote.model.ApiError
import sa.com.data.remote.model.NewsObject

interface ArticlesApi {

    @GET("kotlin/.json")
    suspend fun getArticles(): ApiResult<NewsObject, ApiError>

}