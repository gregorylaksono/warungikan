package com.warungikan.webapp.model;

public class ShopItem {

	private FishShopItem fish;
	private int count;
	public ShopItem(FishShopItem fish, int count) {
		setFish(fish);
		setCount(count);
		
	}
	public FishShopItem getFish() {
		return fish;
	}
	public void setFish(FishShopItem fish) {
		this.fish = fish;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public ShopItem getInstance() {
		return this;
	}
	
	
}
