package com.example.oic.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.oic.data.model.BookmarkWord
import com.example.oic.databinding.ItemWordBinding

class BookmarkAdapter : RecyclerView.Adapter<BookmarkViewHolder>() {

    private val bookmarkList = mutableListOf<BookmarkWord>()

    private lateinit var onClick: (BookmarkWord) -> Unit

    private lateinit var onDelete: (BookmarkWord) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookmarkViewHolder {
        val binding =
            ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(bookmarkList[position], onClick, onDelete)
    }

    override fun getItemCount(): Int =
        bookmarkList.size

    fun addAll(list: List<BookmarkWord>) {
        bookmarkList.clear()
        bookmarkList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (BookmarkWord) -> Unit) {
        onClick = listener
    }

    fun delete(item : BookmarkWord){
        if(bookmarkList.contains(item)){
            val index = bookmarkList.indexOf(item)
            bookmarkList.removeAt(index)
            notifyItemChanged(index)
        }

    }

    fun add(item : BookmarkWord){
        bookmarkList.add(item)
        notifyItemChanged(bookmarkList.lastIndex)
    }

    fun itemDelete(listener: (BookmarkWord) -> Unit) {
        onDelete = listener
    }
}

class BookmarkViewHolder(private val binding: ItemWordBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: BookmarkWord,
        onClick: (BookmarkWord) -> Unit,
        onDelete: (BookmarkWord) -> Unit
    ) {
        binding.item = item.foWordItem()
        binding.deleteBookmark.isVisible = true

        binding.deleteBookmark.setOnClickListener {
            onDelete(item)
        }
        itemView.setOnClickListener {
            onClick(item)
        }
    }
}