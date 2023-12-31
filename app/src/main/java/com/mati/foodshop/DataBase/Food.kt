package com.mati.foodshop.DataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_food")
data class Food(

    @PrimaryKey(autoGenerate = true)
    val id :Int? = null ,

    val txtSubject: String,
    val txtPrice: String,
    val txtDistance: String,
    val txtCity: String,
    val urlImage: String,
    val numberOfRating: Int,
    val rating: Float


)