package com.utez.edu.mx.activitysix
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class FirebaseStoreManager {
    companion object{
        const val  TAG = "firebase"
    }
    private val mStorageReference = FirebaseStorage.getInstance().reference
    private lateinit var mProcessDialog: ProgressDialog

    fun uploadImage(mContex: Context, imageURI: Uri, title:String) {
        mProcessDialog = ProgressDialog(mContex)
        mProcessDialog.setMessage("Subiendo Imagen...")
        mProcessDialog.show()
        val imageFileName = "icons/${System.currentTimeMillis()}.png"
        val uploadTask = mStorageReference.child(imageFileName).putFile(imageURI)
        uploadTask.addOnSuccessListener {
            // Success
            Log.e(TAG,"Imagen upload successfully")
            val downloadURLTask = mStorageReference.child(imageFileName).downloadUrl
            downloadURLTask.addOnSuccessListener {
                Log.e(TAG,"Imagen path url:${it}")
                saveCollection(title,it.toString())
                mProcessDialog.dismiss()
            }.addOnFailureListener {
                Log.e(TAG,"Imagen path error")
            }

        }.addOnFailureListener {
            // Error
            Log.e(TAG, "image upload failed --> ${it.printStackTrace()}")
            mProcessDialog.dismiss()
        }
    }
    fun saveCollection(title: String,imageURI: String){
        val db = Firebase.firestore
        // Save OBJECT
        val collection = hashMapOf(
            "collection" to title.lowercase(),
            "title" to title,
            "description" to imageURI
        )
        db.collection("options")
            .add(collection)
            .addOnSuccessListener { documentReference ->
                Log.d(MainActivityFormCollection.TAG,"DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(MainActivityFormCollection.TAG, "Error adding document", e)
            }
    }

}