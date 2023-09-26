package com.mati.foodshop.MainScreen

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mati.foodshop.DataBase.Food
import com.mati.foodshop.DataBase.myDataBase
import com.mati.foodshop.MainScreen.Adapter.FoodAdapter
import com.mati.foodshop.databinding.ActivityMainBinding
import com.mati.foodshop.databinding.DialogAddNewItemBinding
import com.mati.foodshop.databinding.DialogDeleteBinding
import com.mati.foodshop.databinding.DialogEditItemBinding

class MainActivity : AppCompatActivity(), FoodAdapter.FoodEvents, MainContract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: FoodAdapter
    private lateinit var presenter: MainContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(myDataBase.getDataBase(this).foodDao)

        val sharedPreferences = getSharedPreferences("Mati", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("frist_run", true)) {
            presenter.fristRun()
            sharedPreferences.edit().putBoolean("frist_run", false).apply()
        }

        presenter.onAttach(this)

        binding.btnAddNewfood.setOnClickListener {
            addFood()
        }

        binding.searchBox.addTextChangedListener { editTextInput ->

            presenter.onSearchFood(editTextInput.toString())

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
    private fun addFood() {

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
                    txtSubject = txtName,
                    txtPrice = txtPrice,
                    txtDistance = txtDistance,
                    txtCity = txtCity,
                    urlImage = urlPic,
                    numberOfRating = txtRatingNumber,
                    rating = ratingBarStar
                )

                presenter.onAddNewFood(newFood)

                dialog.dismiss()


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
                presenter.onUpdateFood(newFood, position)
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
            presenter.onDeleteFood(food, position)
            dialog.dismiss()
        }

    }

    override fun showFoods(data: List<Food>) {

        myAdapter = FoodAdapter(ArrayList(data), this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

    }
    override fun refreshFood(data: List<Food>) {

        myAdapter.setData(ArrayList(data))

    }
    override fun addFood(newFood: Food) {

        myAdapter.addFood(newFood)

    }
    override fun deleteFood(oldFood: Food, pos: Int) {

        myAdapter.removeFood(oldFood, pos)

    }
    override fun updateFood(editingFood: Food, pos: Int) {

        myAdapter.updateFood(editingFood, pos)

    }
}