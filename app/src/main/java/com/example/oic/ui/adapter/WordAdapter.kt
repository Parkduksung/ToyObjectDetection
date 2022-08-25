package com.example.oic.ui.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oic.databinding.ItemWordBinding
import kotlinx.android.parcel.Parcelize

class WordAdapter : RecyclerView.Adapter<WordViewHolder>() {

    private val wordList = mutableListOf<WordItem>()

    private lateinit var onClick: (WordItem) -> Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WordViewHolder {
        val binding =
            ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(wordList[position], onClick)
    }

    override fun getItemCount(): Int =
        wordList.size

    fun addAll(list: List<WordItem>) {
        wordList.clear()
        wordList.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: (WordItem) -> Unit) {
        onClick = listener
    }
}


class WordViewHolder(private val binding: ItemWordBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WordItem, onClick: (WordItem) -> Unit) {
        binding.item = item

        itemView.setOnClickListener {
            onClick(item)
        }
    }
}

@Parcelize
data class WordItem(
    val word: String,
    val mean: String
) : Parcelable