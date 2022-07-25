package com.utez.edu.mx.activitysix
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

class FirebaseStoreManager {
    companion object{
        const val  TAG = "firebase"
    }
    private val mStorageReference = FirebaseStorage.getInstance().reference
    private lateinit var mProcessDialog: ProgressDialog

    fun uploadImage(mContex: Context, imageURI: Uri): String {
        var url:String = ""
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
                url = it.toString()
                Log.e(TAG,"Imagen path url:${url}")
                mProcessDialog.dismiss()
            }.addOnFailureListener {
                Log.e(TAG,"Imagen path error")

            }

        }.addOnFailureListener {
            // Error
            Log.e(TAG, "image upload failed --> ${it.printStackTrace()}")
            mProcessDialog.dismiss()
        }
        return url
    }
}