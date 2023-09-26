package com.mati.foodshop.MainScreen

import com.mati.foodshop.DataBase.Food

interface MainContract {


    interface Presenter {

        fun onAttach(view: MainContract.View)
        fun onDetach()

        fun onSearchFood(filter: String)
        fun onAddNewFood(food: Food)

        fun onFoodClick()
        fun onFoodLongClick()

    }


    interface View {

        fun showFoods(data: List<Food>)
        fun addFood(newFood: Food)
        fun deleteFood(oldFood: Food)
        fun editFood(editingFood: Food)

    }

}