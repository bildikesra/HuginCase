package com.esrabildik.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.esrabildik.data.local.entity.CardItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {

    @Query("Select * from card_item")
    fun getAllCardItem() : Flow<List<CardItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCardItem(item : CardItemEntity)

    @Update
    suspend fun updateCardItem(item : CardItemEntity)

    @Delete
    suspend fun deleteCardItem(item : CardItemEntity)

    @Query("DELETE FROM card_item")
    suspend fun clearCard()

}