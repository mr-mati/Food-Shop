package com.mati.foodshop.MainScreen.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mati.foodshop.DataBase.Food
import com.mati.foodshop.R
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class FoodAdapter(private val data: ArrayList<Food>, private val foodEvents: FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    inner class FoodViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        val imgMain = itemView.findViewById<ImageView>(R.id.item_imgMain)
        val txtSubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
        val txtCity = itemView.findViewById<TextView>(R.id.item_txt_city)
        val txtPrice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtDistance = itemView.findViewById<TextView>(R.id.item_txt_distans)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.item_rating_Main)
        val txtrating = itemView.findViewById<TextView>(R.id.item_txt_rating)

        @SuppressLint("SetTextI18n")
        fun bindData(position: Int) {

            txtSubject.text = data[position].txtSubject
            txtCity.text = data[position].txtCity
            txtPrice.text = "$" + data[position].txtPrice + "Vip"
            txtDistance.text = data[position].txtDistance + "Miles From You"
            ratingBar.rating = data[position].rating
            txtrating.text = "(" + data[position].numberOfRating.toString() + "Ratings)"

            Glide
                .with(context)
                .load(data[position].urlImage)
                .transform(RoundedCornersTransformation(16, 4))
                .into(imgMain)

            itemView.setOnClickListener {
                foodEvents.onFoodClicked(data[position], adapterPosition)
            }
            itemView.setOnLongClickListener {
                foodEvents.onFoodLongClicked(data[adapterPosition], adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food, parent, false)
        return FoodViewHolder(view, parent.context)

    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.bindData(position)


    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addFood(newFood: Food) {
        data.add(0, newFood)
        notifyItemInserted(0)
    }

    fun removeFood(oldFood: Food, oldPosition: Int) {
        data.remove(oldFood)
        notifyItemRemoved(oldPosition)
    }

    fun updateFood(newFood: Food, position: Int) {
        data[position] = newFood
        notifyItemChanged(position)
    }

    fun setData(newList : ArrayList<Food>){
        data.clear()
        data.addAll(newList)
        notifyDataSetChanged()
    }

    interface FoodEvents {

        fun onFoodClicked(food: Food, position: Int)
        fun onFoodLongClicked(food: Food, position: Int)
    }

}