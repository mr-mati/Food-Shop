package com.mati.foodshop

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mati.foodshop.Adapter.FoodAdapter
import com.mati.foodshop.DataBase.Food
import com.mati.foodshop.DataBase.FoodDao
import com.mati.foodshop.DataBase.myDataBase
import com.mati.foodshop.databinding.ActivityMainBinding
import com.mati.foodshop.databinding.DialogAddNewItemBinding
import com.mati.foodshop.databinding.DialogDeleteBinding
import com.mati.foodshop.databinding.DialogEditItemBinding

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: FoodAdapter
    lateinit var foodDao: FoodDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodDao = myDataBase.getDataBase(this).foodDao

        val sharedPreferences = getSharedPreferences("Mati", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("frist_run", true)) {
            fristRun()

            sharedPreferences.edit().putBoolean("frist_run", false).apply()
        }

        showFoodAll()

        binding.btnAddNewfood.setOnClickListener {
            addFood()
        }

        binding.searchBox.addTextChangedListener { editTextInput ->

            searchFood(editTextInput.toString())


        }



    }

    private fun searchFood(editTextInput: String) {

        if(editTextInput!!.isNotEmpty()){

            val searchData = foodDao.searchFood(editTextInput)
            myAdapter.setData(ArrayList(searchData))

        }else {
            val data = foodDao.getAllFoods()
            myAdapter.setData(ArrayList(data))
        }

    }

    private fun fristRun() {

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

    private fun showFoodAll() {

        val foodData = foodDao.getAllFoods()
        myAdapter = FoodAdapter(ArrayList(foodData), this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }

    private fun addFood(){

        val dialog = AlertDialog.Builder(this).create()

        val dialogBinding = DialogAddNewItemBinding.inflate(layoutInflater)
        dialog.setView(dialogBinding.root)
        dialog.setCancelable(true)
        dialog.show()

        dialogBinding.DialogBtnDone.setOnClickListener {

            if (
                dialogBinding.dialogEdtNamefood.length() > 0 &&
                dialogBinding.dialogEdtPricefood.length() > 0 &&
                dialogBinding.dialogEdtDistance.length() > 0 &&
                dialogBinding.dialogEdtCityfood.length() > 0 &&
                dialogBinding.dialogEdtImageurl.length() > 0
            ) {
                val txtName = dialogBinding.dialogEdtNamefood.text.toString()
                val txtPrice = dialogBinding.dialogEdtPricefood.text.toString()
                val txtDistance = dialogBinding.dialogEdtDistance.text.toString()
                val txtCity = dialogBinding.dialogEdtCityfood.text.toString()
                val urlPic = dialogBinding.dialogEdtImageurl.text.toString()
                val txtRatingNumber: Int = (1..150).random()
                val ratingBarStar: Float = (1..5).random().toFloat()

                val newFood = Food(
                    txtSubject =  txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    urlImage = urlPic,
                    numberOfRating = txtRatingNumber,
                    rating = ratingBarStar
                )
                myAdapter.addFood(newFood)
                foodDao.insertFood(newFood)
                dialog.dismiss()
                binding.recyclerMain.scrollToPosition(0)


            } else {
                Toast.makeText(this, "تمامی فیلد ها باید پر شوند", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onFoodClicked(food: Food, position: Int) {

        val dialog = AlertDialog.Builder(this).create()
        val dialogEditItemBinding = DialogEditItemBinding.inflate(layoutInflater)
        dialog.setView(dialogEditItemBinding.root)
        dialog.setCancelable(false)
        dialog.show()

        dialogEditItemBinding.dialogEdtNamefood.setText(food.txtSubject)
        dialogEditItemBinding.dialogEdtCityfood.setText(food.txtCity)
        dialogEditItemBinding.dialogEdtPricefood.setText(food.txtPrice)
        dialogEditItemBinding.dialogEdtDistance.setText(food.txtDistance)
        dialogEditItemBinding.dialogEdtImageurl.setText(food.urlImage)


        dialogEditItemBinding.dialogUpdateBtnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogEditItemBinding.dialogUpdateBtnDone.setOnClickListener {
            if (
                dialogEditItemBinding.dialogEdtNamefood.length() > 0 &&
                dialogEditItemBinding.dialogEdtPricefood.length() > 0 &&
                dialogEditItemBinding.dialogEdtDistance.length() > 0 &&
                dialogEditItemBinding.dialogEdtCityfood.length() > 0 &&
                dialogEditItemBinding.dialogEdtImageurl.length() > 0
            ) {
                val txtName = dialogEditItemBinding.dialogEdtNamefood.text.toString()
                val txtPrice = dialogEditItemBinding.dialogEdtPricefood.text.toString()
                val txtDistance = dialogEditItemBinding.dialogEdtDistance.text.toString()
                val txtCity = dialogEditItemBinding.dialogEdtCityfood.text.toString()
                val urlPic = dialogEditItemBinding.dialogEdtImageurl.text.toString()

                val newFood = Food(
                    id = food.id,
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    urlImage = urlPic,
                    numberOfRating = food.numberOfRating,
                    rating = food.rating
                )

                myAdapter.updateFood(newFood, position)
                foodDao.updateFood(newFood)
                dialog.dismiss()

            } else {
                Toast.makeText(this, "تمامی فیلد ها باید پر شوند", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onFoodLongClicked(food: Food, position: Int) {


        val dialog = AlertDialog.Builder(this).create()
        val dialogDeleteBinding = DialogDeleteBinding.inflate(layoutInflater)
        dialog.setView(dialogDeleteBinding.root)
        dialog.setCancelable(false)
        dialog.show()

        dialogDeleteBinding.dialogBtnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogDeleteBinding.dialogBtnDelete.setOnClickListener {
            dialog.dismiss()
            myAdapter.removeFood(food, position)
            foodDao.deleteFood( food )
        }

    }
}