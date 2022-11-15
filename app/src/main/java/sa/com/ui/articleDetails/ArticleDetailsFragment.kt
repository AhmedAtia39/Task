package sa.com.ui.articleDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import sa.com.R
import sa.com.data.local.articles.ArticleEntity
import sa.com.databinding.FragmentArticleDetailsBinding
import sa.com.ui.home.HomeFragment.Companion.ARTICLE

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    companion object {
     fun newInstance() = ArticleDetailsFragment()
    }

    private lateinit var mBinding: FragmentArticleDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentArticleDetailsBinding.inflate(inflater, container, false)

        mBinding.topAppBar.title = getString(R.string.kotlin_news)
        initData()
        return mBinding.root
    }

    private fun initData() {

       val article = arguments?.get(ARTICLE) as ArticleEntity

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        mBinding.topAppBar.setupWithNavController(navController, appBarConfiguration)
        mBinding.title.text = article.title

        val url = article.img
        when(url){
            "" -> mBinding.articleImage.visibility = View.GONE
            else -> {
                mBinding.articleImage.visibility = View.VISIBLE
                Glide.with(this).load(url)
                    .apply( RequestOptions().placeholder(R.drawable.ic_loader).error(R.drawable.ic_loader))
                    .into(mBinding.articleImage)
            }
        }

        mBinding.articleBody.text = article.body

        mBinding.txtNoResult.visibility = when(article.body){
            "" -> View.VISIBLE
            else -> View.GONE
        }
    }

}