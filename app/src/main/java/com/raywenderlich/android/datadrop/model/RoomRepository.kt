package com.raywenderlich.android.datadrop.model

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.raywenderlich.android.datadrop.app.DataDropApplication

class RoomRepository : DropRepository {

    private val dropDao: DropDao = DataDropApplication.database.dropDao()
    private val allDrops: LiveData<List<Drop>>

    init {
        allDrops = dropDao.getAllDrops()
    }

    override fun addDrop(drop: Drop) {
        InsertAsyncTask(dropDao).execute(drop)
    }

    override fun getDrops(): LiveData<List<Drop>> {
        return allDrops
    }

    override fun clearDrop(drop: Drop) {

    }

    override fun clearAllDrops() {

    }

    // Insert off the main thread
    private class InsertAsyncTask internal constructor(private val dao: DropDao): AsyncTask<Drop, Void, Void>() {
        override fun doInBackground(vararg params: Drop): Void? {
            dao.insert(params[0])
            return null
        }

    }
}