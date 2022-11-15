package sa.com.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import sa.com.R
import sa.com.data.local.articles.ArticleEntity
import sa.com.databinding.ArticleItemBinding

class ArticleAdapter(
    private val open: (ArticleEntity) -> Unit,
) : ListAdapter<ArticleEntity?, ViewHolder>(diff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemVH(ArticleItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder is ItemVH)
            holder.bind(getItem(position)!!, open)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ItemVH(private val mBinding: ArticleItemBinding) : ViewHolder(mBinding.root) {
        private val context = mBinding.root.context

        fun bind(item: ArticleEntity, open: (ArticleEntity) -> Unit, ) {
            mBinding.articleTitle.text = item.title

            val url = item.img
            when(url){
                "" -> mBinding.articleImage.visibility = View.GONE
                else -> {
                    mBinding.articleImage.visibility = View.VISIBLE
                    Glide.with(context).load(url)
                        .apply( RequestOptions().placeholder(R.drawable.ic_loader).error(R.drawable.ic_loader))
                        .into(mBinding.articleImage)
                }
            }

            mBinding.container.setOnClickListener {
                Navigation.findNavController(it).navigate(
                    R.id.open_article_details , bundleOf(
                        HomeFragment.ARTICLE to item
                )
                )
            }
        }
    }

    companion object {
        val diff by lazy {
            object : DiffUtil.ItemCallback<ArticleEntity?>() {
                override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
                    return newItem.id == oldItem.id
                }
                override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
}