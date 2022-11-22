package com.victormartin.archmvvm.di

import android.content.Context
import androidx.room.Room
import com.victormartin.archmvvm.data.database.PlayersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "archmvvm_room_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PlayersDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun providePlayersDao(db: PlayersDatabase) = db.getPlayersDao()
}