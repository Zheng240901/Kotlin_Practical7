package com.example.roomdemo1.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class Fruit (
    @PrimaryKey(autoGenerate = true)
    var id   : Int = 0,
    var name : String,
    var price: Double,
)

@Dao
interface FruitDao{
    @Query("SELECT * FROM fruit")
    fun getAll(): LiveData<List<Fruit>>

    @Query("SELECT * FROM fruit WHERE id = :id")
    fun get(id: Int):LiveData<Fruit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(f: Fruit)

    @Update
    suspend fun update(f: Fruit)

    @Delete
    suspend fun delete(f: Fruit)

    @Query("DELETE FROM fruit")
    suspend fun deleteAll()
}

@Database(
    entities = [Fruit::class],
    version = 1,
    exportSchema = false
)


abstract class DB : RoomDatabase() {
    abstract val fruitDao: FruitDao

    companion object{
        @Volatile
        private  var instance: DB? = null

        @Synchronized
        fun getInstance(context: Context): DB {
            instance = instance ?: Room
                .databaseBuilder(context, DB::class.java, "database.db")
                .fallbackToDestructiveMigration()
                .build()
            return instance!!
        }
    }
}