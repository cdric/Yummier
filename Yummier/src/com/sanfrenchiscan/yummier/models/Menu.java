package com.sanfrenchiscan.yummier.models;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	
	List<Dish> dishes = new ArrayList<Dish>();

	/**
	 * @return the dishes
	 */
	public List<Dish> getDishes() {
		return dishes;
	}

	/**
	 * @param dishes the dishes to set
	 */
	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}
	
	public void addDish(Dish dish) {
		dishes.add(dish);
	}
	
	

}
