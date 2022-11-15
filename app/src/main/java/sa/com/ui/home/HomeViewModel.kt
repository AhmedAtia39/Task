package sa.com.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sa.com.data.MainRepo
import sa.com.data.local.LocalDataSource
import sa.com.data.local.articles.ArticleEntity
import sa.com.ui.core.DataState
import sa.com.ui.core.VMErrorHandler
import sa.com.ui.core.toErrorState
import javax.inject.Inject
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings.Global.getString
import sa.com.R
import sa.com.databinding.DialogMessageBinding
import sa.com.ui.custom.MessageDialog

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepo: MainRepo,
    private val errorHandler: VMErrorHandler,
    private val localDS: LocalDataSource,
) : ViewModel() {
    private val _articles: MutableLiveData<DataState<List<ArticleEntity>>> = MutableLiveData(DataState.Idle())
    val articles: LiveData<DataState<List<ArticleEntity>>> = _articles
    var networkStatus: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    private var articlesJob: Job? = null
    var networkIsConnected = false

    init {
        getArticles()
    }

    fun getArticles() {
        articlesJob = viewModelScope.launch {
            if (mainRepo.getLocalArticles().size>0){
                val  articles = mainRepo.getLocalArticles()
                _articles.value= DataState.Success(articles)
            }else if (!networkIsConnected){
                networkStatus.value = DataState.Success(false)
            } else{
                _articles.value = DataState.Loading()
                val result = mainRepo.getRemoteArticles()
                _articles.value = when (result) {
                    is ApiResult.Success -> {
                        val  children = result.value.data.children
                        val articles = ArrayList<ArticleEntity>()
                        for (child in children){
                            articles.add(ArticleEntity(
                                child.data.media?.oembed?.thumbnail_url?:"",
                                child.data.title,
                                child.data.selftext
                            ))
                        }
                        mainRepo.saveArticles(articles)
                        DataState.Success(articles)
                    }
                    is ApiResult.Failure -> {
                        val errorState = result.toErrorState()
                        if (errorState is DataState.Error) errorHandler.handleError(errorState)
                        errorState
                    }
                }
            }
        }
    }

    fun articlesStateConsumed() {
        val state = _articles.value!!
        state.consumed = true
        _articles.value = state
    }

    fun isNetworkAvailable(context: Context) {
        networkIsConnected =  (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            getNetworkCapabilities(activeNetwork)?.run {
                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
            } ?: false
        }
    }
}
