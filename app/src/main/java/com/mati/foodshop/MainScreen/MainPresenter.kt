package com.mati.foodshop.MainScreen

import com.mati.foodshop.DataBase.Food
import com.mati.foodshop.DataBase.FoodDao

class MainPresenter(

    private val foodDao: FoodDao,

    ) : MainContract.Presenter {

    var mainView: MainContract.View? = null
    override fun fristRun() {

        val foodList = listOf(
            Food(
                txtSubject = "pizza",
                txtPrice = "15",
                txtDistance = "3",
                txtCity = "Tehran",
                urlImage = "https://kadolin.ir/mag/wp-content/uploads/2022/04/Pizza-recipe.jpg",
                numberOfRating = 41,
                rating = 4f
            ),
            Food(
                txtSubject = "Kebab",
                txtPrice = "10",
                txtDistance = "3",
                txtCity = "Tehran",
                urlImage = "https://rahavardnews.com/wp-content/uploads/2022/02/vaziri3.jpg",
                numberOfRating = 23,
                rating = 5f
            ),
            Food(
                txtSubject = "Ash Kashk",
                txtPrice = "5",
                txtDistance = "3",
                txtCity = "Tehran",
                urlImage = "https://setare.com/files/1396/09/27/%D8%B7%D8%B1%D8%B2-%D8%AA%D9%87%DB%8C%D9%87-%D8%A2%D8%B4-%D8%B1%D8%B4%D8%AA%D9%87-%D8%AF%D9%88%D9%86%D9%81%D8%B1%D9%87.jpg",
                numberOfRating = 132,
                rating = 5f
            ),
            Food(
                txtSubject = "Ghormeh Sabzi",
                txtPrice = "8",
                txtDistance = "3",
                txtCity = "Tehran",
                urlImage = "https://media.tehrantimes.com/d/t/2022/05/22/4/4158248.jpg",
                numberOfRating = 150,
                rating = 5f
            ),
            Food(
                txtSubject = "Kofte Tabrizi",
                txtPrice = "19",
                txtDistance = "123",
                txtCity = "Tabriz",
                urlImage = "https://cookingcounty.com/wp-content/uploads/2021/12/koofteh-tabrizi.jpg",
                numberOfRating = 102,
                rating = 5f
            ),
            Food(
                txtSubject = "Gheymeh",
                txtPrice = "8",
                txtDistance = "3",
                txtCity = "Tehran",
                urlImage = "https://www.destinationiran.com/wp-content/uploads/2016/06/Khoresh-Gheimeh-Recipe-1024x665.jpg",
                numberOfRating = 72,
                rating = 3f
            )
        )

        foodDao.insertAllFood(foodList)

    }

    override fun onAttach(view: MainContract.View) {

        mainView = view


        val data = foodDao.getAllFoods()
        mainView!!.showFoods(data)


    }

    override fun onDetach() {

        mainView = null

    }

    override fun onSearchFood(filter: String) {

        if (filter.isNotEmpty()) {

            val data = foodDao.searchFood(filter)
            mainView!!.refreshFood(data)

        } else {

            val data = foodDao.getAllFoods()
            mainView!!.refreshFood(data)

        }


    }

    override fun onAddNewFood(food: Food) {

        foodDao.insertFood(food)
        mainView!!.addFood(food)

    }

    override fun onUpdateFood(food: Food, pos: Int) {

        foodDao.updateFood(food)
        mainView!!.updateFood(food, pos)

    }

    override fun onDeleteFood(food: Food, pos: Int) {

        foodDao.deleteFood(food)
        mainView!!.deleteFood(food, pos)

    }

}