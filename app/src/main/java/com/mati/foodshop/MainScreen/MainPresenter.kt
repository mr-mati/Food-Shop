package com.mati.foodshop.MainScreen

import com.mati.foodshop.DataBase.Food
import com.mati.foodshop.DataBase.FoodDao

class MainPresenter(

    private val foodDao: FoodDao,

    ) : MainContract.Presenter {

    var mainView: MainContract.View? = null

    override fun onAttach(view: MainContract.View) {

        mainView = view


        val data = foodDao.getAllFoods()
        mainView!!.showFoods(data)


    }

    override fun onDetach() {

        mainView = null

    }

    override fun onSearchFood(filter: String) {

    }

    override fun onAddNewFood(food: Food) {

    }

    override fun onFoodClick() {

    }

    override fun onFoodLongClick() {

    }


}