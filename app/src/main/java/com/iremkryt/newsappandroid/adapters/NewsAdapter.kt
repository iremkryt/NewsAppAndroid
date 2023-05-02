package com.iremkryt.newsappandroid.adapters

//import com.iremkryt.newsappandroid.R

import android.bluetooth.BluetoothAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iremkryt.newsappandroid.R
import com.iremkryt.newsappandroid.models.Article


class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>(){
    val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    //Inner class, boş classın yani miras aldığı classın her şeyine erişirler
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val ivArticleImage: ImageView = holder.itemView.findViewById(R.id.ivArticleImage)
        val tvSource: TextView = holder.itemView.findViewById(R.id.tvSource)
        val tvPublishedAt: TextView = holder.itemView.findViewById(R.id.tvPublishedAt)
        val tvTitle: TextView = holder.itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = holder.itemView.findViewById(R.id.tvDescription)

        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            tvSource.text = article.source.name
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvPublishedAt.text = article.publishedAt
            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}