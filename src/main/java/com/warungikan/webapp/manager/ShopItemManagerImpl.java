package com.warungikan.webapp.manager;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.warungikan.api.model.request.VShopItem;
import org.warungikan.db.model.ShopItem;
import org.warungikan.db.model.ShopItemStock;
import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.User;

import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.util.Constant;

public class ShopItemManagerImpl {

	
	public ShopItem createShopItem(String sessionId, String name,String description,String url, String price, String weight) throws WarungIkanNetworkException{
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			VShopItem s = new VShopItem(name, description, url, price, weight);
			HttpEntity request = new HttpEntity<>(s,headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<ShopItem> response = t.postForEntity(new URI(Constant.WS_POST_CREATE_SHOP_ITEM_URL), request, ShopItem.class);
			if(response.getStatusCodeValue() == 202){
				return response.getBody();
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
	
	public ShopItem editShopItem(String sessionId, String shopId, String name,String description,String url, String price, String weight) throws WarungIkanNetworkException{
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			VShopItem s = new VShopItem(shopId, name, description, url, price, weight);
			HttpEntity request = new HttpEntity<>(s, headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<ShopItem> response = t.exchange(new URI(Constant.WS_PUT_UPDATE_SHOP_ITEM_URL),HttpMethod.PUT, request, ShopItem.class);
			if(response.getStatusCodeValue() == 202){
				return response.getBody();
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
	
	public List<ShopItem> getShopItem(String sessionId) throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<List<ShopItem>> response = r.exchange(new URI(Constant.WS_GET_GET_SHOP_ITEM_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<ShopItem>>(){});
			if(response.getStatusCodeValue() == 202){
				List<ShopItem> body = response.getBody();
				return body;
			}else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return null;
			}
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return new ArrayList<>();
	}
	
	public List<ShopItemStock> getStockItem(String sessionId) throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<List<ShopItemStock>> response = r.exchange(new URI(Constant.WS_GET_STOCK_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<ShopItemStock>>(){});
			if(response.getStatusCodeValue() == 202){
				List<ShopItemStock> body = response.getBody();
				return body;
			}else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return null;
			}
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return new ArrayList<>();
	}
	
	public ShopItemStock addStockByAgent(String sessionId, String stockId, String user_id, Integer amount)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<ShopItemStock> response = t.postForEntity(new URI(Constant.WS_POST_ADD_STOCK_ITEM_AGENT_URL+"/"+stockId+"/"+user_id+"/"+String.valueOf(amount)), request, ShopItemStock.class);
			
			if(response.getStatusCodeValue() == 202){
				return response.getBody();
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
}
