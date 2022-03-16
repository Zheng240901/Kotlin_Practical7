package com.example.roomdemo1

import android.app.Application
import com.example.roomdemo1.data.DB
import com.example.roomdemo1.data.Fruit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {

    companion object{
        lateinit var db: DB
    }

    override fun onCreate() {
        super.onCreate()
        db = DB.getInstance(this)

        GlobalScope.launch {
            db.fruitDao.insert(Fruit(1, "Apple" , 1.00))
            db.fruitDao.insert(Fruit(2, "Banana" , 2.00))
            db.fruitDao.insert(Fruit(3, "Cherry" , 3.00))
        }
    }

}