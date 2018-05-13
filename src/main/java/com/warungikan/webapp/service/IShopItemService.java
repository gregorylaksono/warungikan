package com.warungikan.webapp.service;

import java.util.List;

import org.warungikan.db.model.ShopItem;
import org.warungikan.db.model.ShopItemStock;

public interface IShopItemService {

	public List<ShopItem> getAllShopItem(String jwt);
	public Boolean createShopItem(String jwt, String name, String description, String url, String price, String weight);
	public Boolean updateShopItem(String jwt, String id, String name, String description, String url, String price, String weight);
	public Boolean addStock(String jwt, String shopId, Integer amount );
	public List<ShopItemStock> getStockByAgent(String jwt);
	public List<ShopItemStock> getAllStocks(String jwt);
}
