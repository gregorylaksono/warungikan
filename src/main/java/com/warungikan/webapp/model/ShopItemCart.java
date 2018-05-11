package com.warungikan.webapp.model;

import org.warungikan.db.model.ShopItem;

public class ShopItemCart {

	private ShopItem fish;
	private int count;
	public ShopItemCart(ShopItem fish, int count) {
		setFish(fish);
		setCount(count);
	}
	public ShopItem getFish() {
		return fish;
	}
	public void setFish(ShopItem fish) {
		this.fish = fish;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public ShopItemCart getInstance() {
		return this;
	}
	
	
}
