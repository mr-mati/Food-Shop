package com.mati.foodshop.DataBase

import androidx.room.*

@Dao
interface FoodDao {

    @Insert
    fun insertFood (food : Food)

    @Insert
    fun insertAllFood(data: List<Food>)

    @Update
    fun updateFood (food : Food)

    @Delete
    fun deleteFood (food : Food)

    @Query("SELECT * FROM table_food")
    fun getAllFoods() :List<Food>

    @Query("SELECT * FROM table_food WHERE txtSubject LIKE '%' || :searching || '%' ")
    fun searchFood(searching :String) : List<Food>

}