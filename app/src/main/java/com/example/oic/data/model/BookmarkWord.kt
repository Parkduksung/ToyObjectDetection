package com.example.oic.data.model

import android.os.Parcelable
import com.example.oic.ui.adapter.WordItem
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.HashMap

@Parcelize
data class BookmarkWord(
    val word: String,
    val mean: String,
    var year: String = Calendar.getInstance().get(Calendar.YEAR).toString(),
    var month: String = (Calendar.getInstance().get(Calendar.MONTH) + 1).toString(),
    var day: String = (Calendar.getInstance().get(Calendar.DATE)).toString()
) : Parcelable {

    fun foWordItem(): WordItem =
        WordItem(word, mean)

}


fun HashMap<String, String>.toBookmarkWord(): BookmarkWord =
    BookmarkWord(
        word = getValue("word"),
        mean = getValue("mean"),
        year = getValue("year"),
        month = getValue("month"),
        day = getValue("day")
    )
