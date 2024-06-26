package weerasinghe.deneth.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import java.util.concurrent.Executors

fun createDao(context: Context) =
    // function built with Java, so use class.java (Java metadata) instead of class (Kotlin metadata)
    Room.databaseBuilder(context, MovieDatabase::class.java, "MOVIES")
//        .setQueryCallback(
//            {
//                 sqlQuery, bindArgs ->
//                    Log.d("!!!SQL", "SQL Query: $sqlQuery SQL Args: $bindArgs")
//            }, Executors.newSingleThreadExecutor()
//        )
//  Uncomment above to see what SQL queries are run!
        .build()
        .dao

// creates database in memory rather than relying on files, easier for testing
fun createInMemoryDB(context: Context) =
    Room.inMemoryDatabaseBuilder(
        context,
        MovieDatabase::class.java
    ).build()