package com.warungikan.webapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.ResourceAccessException;
import org.warungikan.api.model.request.VShopItem;
import org.warungikan.db.model.ShopItem;
import org.warungikan.db.model.ShopItemStock;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.manager.ShopItemManager;

public class ShopItemService implements IShopItemService{
	
	private ShopItemManager shopItemManager = new ShopItemManager();
	
	@Override
	public List<ShopItem> getAllShopItem(String jwt) {
		try {
			List<ShopItem> result = shopItemManager.getAllShopItem(jwt);
			return result;
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		}catch(ResourceAccessException e){
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return new ArrayList<>();
	}

	@Override
	public Boolean createShopItem(String jwt, String name, String description, String url, String price, String weight) {
		VShopItem s = new VShopItem(name, description, url, price, weight);
		try {
			Boolean isCreated = shopItemManager.createShopItem(jwt, s);
			return isCreated;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Boolean updateShopItem(String jwt,String id, String name, String description, String url, String price, String weight) {
		VShopItem s = new VShopItem(id, name, description, url, price, weight);
		try {
			Boolean result = shopItemManager.updateShopItem(jwt, s);
			return result;
		}  catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Boolean addStock(String jwt,String shopId, Integer amount) {
		try {
			Boolean result = shopItemManager.addStock(jwt, shopId, String.valueOf(amount));
			return result;
		}  catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public List<ShopItemStock> getStockByAgent(String jwt) {
		try {
			List<ShopItemStock> result = shopItemManager.getStockByAgent(jwt);
			return result;
		}  catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public List<ShopItemStock> getAllStocks(String jwt) {
		try {
			List<ShopItemStock> result = shopItemManager.getStockByAgent(jwt);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		
		return null;
	}
	public void logout(){
		Page.getCurrent().setLocation(VaadinServlet.getCurrent().getServletConfig().getServletContext().getContextPath());
		VaadinSession.getCurrent().close();
	}
}
