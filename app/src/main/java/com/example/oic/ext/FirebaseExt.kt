package com.example.oic.ext

import com.example.oic.data.model.BookmarkWord
import com.example.oic.data.model.toBookmarkWord
import com.example.oic.data.repo.FirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


fun FirebaseRepository.loginUser(
    email: String,
    password: String,
    callback: (isSuccess: Boolean) -> Unit
) {
    getFirebaseAuth().signInWithEmailAndPassword(email, password)
        .addOnCompleteListener {
            callback(it.isSuccessful)
        }
}


fun FirebaseRepository.getWordList(callback: (humanList: List<BookmarkWord>?) -> Unit) {
    getFirebaseAuth().currentUser?.email?.let { userId ->
        getFirebaseFireStore().collection(userId).document("word").get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.exists()) {
                        val getResult: ArrayList<HashMap<String, String>>? =
                            it.result.get("list") as ArrayList<HashMap<String, String>>?
                        val toResultList = getResult?.map { it.toBookmarkWord() }
                        if (!toResultList.isNullOrEmpty()) {
                            callback(toResultList)
                        } else {
                            callback(null)
                        }
                    } else {
                        createWordDB { isCreate ->
                            if (isCreate) {
                                callback(emptyList())
                            } else {
                                callback(null)
                            }
                        }
                    }
                } else {
                    callback(null)
                }
            }
    }
}

fun FirebaseRepository.createWordDB(callback: (isSuccess: Boolean) -> Unit) {
    getFirebaseAuth().currentUser?.email?.let { userId ->
        CoroutineScope(Dispatchers.IO).launch {
            createWordDB(userId).addOnCompleteListener { dbResult ->
                callback(dbResult.isSuccessful)
            }
        }
    }
}

fun FirebaseRepository.addWord(word: BookmarkWord, callback: (isSuccess: Boolean) -> Unit) {
    getFirebaseAuth().currentUser?.email?.let { userId ->
        CoroutineScope(Dispatchers.IO).launch {
            addWordItem(id = userId, word).addOnCompleteListener { dbResult ->
                callback(dbResult.isSuccessful)
            }
        }
    }
}

fun FirebaseRepository.deleteWord(word: BookmarkWord, callback: (isSuccess: Boolean) -> Unit) {
    getFirebaseAuth().currentUser?.email?.let { userId ->
        CoroutineScope(Dispatchers.IO).launch {
            deleteWordItem(userId, word).addOnCompleteListener {
                callback(it.isSuccessful)
            }
        }
    }
}
