package com.mati.foodshop.MainScreen

import com.mati.foodshop.DataBase.Food

interface MainContract {


    interface Presenter {

        fun fristRun()

        fun onAttach(view: MainContract.View)
        fun onDetach()

        fun onSearchFood(filter: String)
        fun onAddNewFood(food: Food)

        fun onUpdateFood(food: Food, pos: Int)
        fun onDeleteFood(food: Food, pos: Int)

    }


    interface View {

        fun showFoods(data: List<Food>)
        fun refreshFood(data: List<Food>)
        fun addFood(newFood: Food)
        fun deleteFood(oldFood: Food, pos: Int)
        fun updateFood(editingFood: Food, pos: Int)

    }

}