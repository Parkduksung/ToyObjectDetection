package com.example.oic.data.source.remote

import com.example.oic.data.model.BookmarkWord
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FirebaseRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
) :
    FirebaseRemoteDataSource {


    override suspend fun login(id: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.signInWithEmailAndPassword(id, password)
        }

    override suspend fun logout(): Boolean =
        withContext(Dispatchers.IO) {
            return@withContext try {
                firebaseAuth.signOut()
                firebaseAuth.currentUser == null
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun register(id: String, password: String): Task<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.createUserWithEmailAndPassword(
                id,
                password
            )
        }


    override suspend fun delete(): Task<Void>? = withContext(Dispatchers.IO) {
        return@withContext firebaseAuth.currentUser?.delete()
    }

    override suspend fun resetPass(resetPassToId: String): Task<Void> =
        withContext(Dispatchers.IO) {
            return@withContext firebaseAuth.sendPasswordResetEmail(resetPassToId)
        }


    override suspend fun createWordDB(id: String): Task<Void> {
        return fireStore.collection(id).document("word")
            .set(emptyMap<String, BookmarkWord>())
    }

    override suspend fun addWordItem(id: String, wordItem: BookmarkWord): Task<Void> {
        return fireStore.collection(id).document("word")
            .update("list", FieldValue.arrayUnion(wordItem))
    }

    override suspend fun deleteWordItem(id: String, wordItem: BookmarkWord): Task<Void> {
        return fireStore.collection(id).document("word")
            .update("list", FieldValue.arrayRemove(wordItem))
    }

    override fun getFirebaseAuth(): FirebaseAuth =
        firebaseAuth

    override fun getFirebaseFireStore(): FirebaseFirestore =
        fireStore

}