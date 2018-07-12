package br.ufg.inf.level6.bibliomovel.util

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by erick on 11/07/18.
 */
class FirebaseConn {

    private var mDataBase: FirebaseDatabase? = null

    fun getDataBase(): FirebaseDatabase? {
        if (mDataBase == null) {
            mDataBase = FirebaseDatabase.getInstance()
            mDataBase!!.setPersistenceEnabled(true)
        }

        return mDataBase
    }

}