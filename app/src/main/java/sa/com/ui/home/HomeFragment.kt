package sa.com.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import sa.com.R
import sa.com.data.local.articles.ArticleEntity
import sa.com.databinding.FragmentHomeBinding
import sa.com.ui.core.*
import sa.com.ui.custom.MessageDialog
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
      const  val ARTICLE = "ARTICLE"
     fun newInstance() = HomeFragment()
    }

    @Inject
    lateinit var mErrorHandler: UIErrorHandler
    @Inject
    lateinit var mLoadingHandler: UILoadingHandler

    private val mViewModel: HomeViewModel by viewModels()
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mArticlesAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        mBinding.topAppBar.title = getString(R.string.kotlin_news)
        initNetwork()
        initArticles()

        mViewModel.isNetworkAvailable(requireContext())
        return mBinding.root
    }

    private fun initNetwork() {
        mViewModel.networkStatus.observe(viewLifecycleOwner){
            if (it.successOrNull()?.data == false){
                MessageDialog.newInstance(getString(R.string.connect_network_please), R.drawable.ic_info_msg)
                    .show(childFragmentManager, MessageDialog.TAG)
            }
        }
    }

    private fun initArticles() {
        mArticlesAdapter = ArticleAdapter {}
        mBinding.rvArticles.adapter = mArticlesAdapter
        mViewModel.articles.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> updateArticlesState(true, null, null, it)
                is DataState.Success -> updateArticlesState(false, null, it.data, it)
                is DataState.Empty -> updateArticlesState(
                    false,
                    getString(R.string.msg_no_result),
                    null,
                    it
                )
                is DataState.Error -> updateArticlesState(false, null, null, it)
                else -> updateArticlesState(false, null, null, it)
            }
        }
    }

    private fun updateArticlesState(
        loading: Boolean,
        msg:String?,
        data: List<ArticleEntity?>?,
        dataState: DataState<List<ArticleEntity>>
    ) {
        if (loading) {
            mBinding.pbArticles.show()
        } else {
            mBinding.pbArticles.hide()
        }
        mBinding.articlesMsg.text = msg?:""
        mBinding.articlesMsg.isVisible = msg !=null
        mArticlesAdapter.submitList(data?: emptyList())
        if (!dataState.consumed) {
            if (dataState is DataState.Error) {
                mErrorHandler.handleError(dataState)
            }
            mViewModel.articlesStateConsumed()
        }
    }
}