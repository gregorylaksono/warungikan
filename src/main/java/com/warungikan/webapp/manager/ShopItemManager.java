package com.warungikan.webapp.manager;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.warungikan.api.model.request.VShopItem;
import org.warungikan.db.model.ShopItem;
import org.warungikan.db.model.ShopItemStock;
import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.User;

import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.ws.request.BasicResponse;

public class ShopItemManager {

	public Boolean createShopItem(String jwt, VShopItem shopItem) throws UserSessionException, WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(shopItem, headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_POST_CREATE_SHOP_ITEM_URL),HttpMethod.POST, request,BasicResponse.class);
			BasicResponse body = response.getBody();
			if(response.getStatusCodeValue() == 202) return true;
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return false;
	}
	
	public Boolean updateShopItem(String jwt, VShopItem shopItem) throws UserSessionException, WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(shopItem, headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_POST_CREATE_SHOP_ITEM_URL),HttpMethod.PUT, request,BasicResponse.class);
			BasicResponse body = response.getBody();
			if(response.getStatusCodeValue() == 202) return true;
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return false;
	}
	
	public List<ShopItem> getAllShopItem(String jwt) throws WarungIkanNetworkException, UserSessionException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<List<ShopItem>> response = r.exchange(new URI(Constant.WS_POST_CREATE_SHOP_ITEM_URL),HttpMethod.GET, request,new ParameterizedTypeReference<List<ShopItem>>(){});
			List<ShopItem> body = response.getBody();
			if(response.getStatusCodeValue() == 202) return body;
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}
	
	public Boolean addStock(String jwt, String shop_id, String amount) throws UserSessionException, WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_POST_ADD_STOCK_URL+"/"+shop_id+"/"+amount),HttpMethod.POST, request,BasicResponse.class);
			if(response.getStatusCodeValue() == 202) return true;
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return false;
	}
	
	public List<ShopItemStock> getStockByAgent(String jwt) throws UserSessionException, WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<List<ShopItemStock>> response = r.exchange(new URI(Constant.WS_POST_ADD_STOCK_URL),HttpMethod.GET, request,new ParameterizedTypeReference<List<ShopItemStock>>(){});
			List<ShopItemStock> body = response.getBody();
			if(response.getStatusCodeValue() == 202) return body;
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}
}
