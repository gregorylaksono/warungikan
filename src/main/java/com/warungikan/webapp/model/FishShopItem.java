package com.warungikan.webapp.model;

public class FishShopItem {

	private String imgUrl;
	private String name;
	private String weight;
	private String price;
	private String description;
	
	public FishShopItem(String imgUrl, String name, String weight, String price, String description) {
		setImgUrl(imgUrl);
		setName(name);
		setWeight(weight);
		setPrice(price);
		setDescription(description);
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
