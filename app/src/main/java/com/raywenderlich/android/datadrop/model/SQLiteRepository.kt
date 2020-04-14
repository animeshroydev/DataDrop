package com.raywenderlich.android.datadrop.model

import android.content.ContentValues
import android.util.Log
import com.raywenderlich.android.datadrop.app.DataDropApplication
import java.io.IOException

class SQLiteRepository : DropRepository {

    private val database = DropDbHelper(DataDropApplication.getAppContext()).writableDatabase

    override fun addDrop(drop: Drop) {
        val contentValues = getDropContentValues(drop)
        database.insert(DropDbSchema.DropTable.NAME, null, contentValues)
    }

    override fun getDrops(): List<Drop> {
        val drops = mutableListOf<Drop>()

        val cursor = queryDrops(null, null)

        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                drops.add(cursor.getDrop())
                cursor.moveToNext()
            }
        } catch (e: IOException) {
            Log.e("SQLiteRepository", "Error Reading Drops")
        } finally {
            cursor.close()
        }
        return drops
    }

    override fun clearDrop(drop: Drop) {
        database.delete(
                DropDbSchema.DropTable.NAME,
                DropDbSchema.DropTable.Columns.ID + " = ?",
                arrayOf(drop.id)
        )
    }

    override fun clearAllDrops() {
        database.delete(DropDbSchema.DropTable.NAME, null, null)
    }

    private fun getDropContentValues(drop: Drop): ContentValues {
        val contentValues = ContentValues()
        contentValues.put(DropDbSchema.DropTable.Columns.ID, drop.id)
        contentValues.put(DropDbSchema.DropTable.Columns.LATITUDE, drop.latLng.latitude)
        contentValues.put(DropDbSchema.DropTable.Columns.LONGITUDE, drop.latLng.longitude)
        contentValues.put(DropDbSchema.DropTable.Columns.DROP_MESSAGE, drop.dropMessage)
        contentValues.put(DropDbSchema.DropTable.Columns.MARKER_COLOR, drop.markerColor)
        return contentValues
    }

    private fun queryDrops(where: String?, whereArgs: Array<String>?): DropCursorWrapper {
        val cursor = database.query(
                DropDbSchema.DropTable.NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null
        )

        return DropCursorWrapper(cursor)
    }

}