package com.warungikan.webapp.manager;

import java.io.IOException;
import java.io.Serializable;
import java.net.NoRouteToHostException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.warungikan.db.model.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.vaadin.ui.UI;
import com.warungikan.webapp.MyUI;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.servie.IUserService;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.ws.request.BasicResponse;
import com.warungikan.webapp.ws.request.Login;
import elemental.json.JsonObject;

public class UserManagerImpl  {


	public String login(String username, String password)throws UserSessionException,WarungIkanNetworkException{
		String jwt = null;
		try {
			RestTemplate t = new RestTemplate();
			ResponseEntity<String> response = t.postForEntity(new URI(Constant.WS_LOGIN_URL), new Login(username, password), String.class);
			if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}
			return response.getHeaders().getFirst("Authorization");
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}

	public List<User> getAllUsers() throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", ((MyUI)UI.getCurrent()).getJwt());
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<List<User>> response = r.exchange(new URI(Constant.WS_GET_ALL_USER_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<User>>(){});
			List<User> body = response.getBody();
			return body;
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return new ArrayList<>();
	}

	public static void main(String[] args) throws TimeoutException {
	
	}

	public Integer createUser(String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password) throws UserSessionException,WarungIkanNetworkException{
		try {
		User u = User.UserFactory(name, email, telNo, address, city, latitude, longitude, password);
		RestTemplate r = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", ((MyUI)UI.getCurrent()).getJwt());
		HttpEntity request = new HttpEntity<>(u, headers);
		ResponseEntity<BasicResponse> response = r.postForEntity(new URI(Constant.WS_CREATE_USER_URL), request, BasicResponse.class);
		return response.getStatusCodeValue();
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}
	public Integer updateUser(Long id, String name, String email, String telNo, String address, String city, String latitude,
			String longitude) throws UserSessionException,WarungIkanNetworkException{
		try {
		User u = getSingleUser(email);
		u.setName(name).setEmail(email).setTelpNo(telNo).setAddress(address).
		setCity(city).setLatitude(Double.parseDouble(latitude)).setLongitude(Double.parseDouble(longitude));
		
		RestTemplate r = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", ((MyUI)UI.getCurrent()).getJwt());
		HttpEntity request = new HttpEntity<>(u, headers);
		ResponseEntity<BasicResponse> response = r.postForEntity(new URI(Constant.WS_CREATE_USER_URL), request, BasicResponse.class);
		return response.getStatusCodeValue();
		
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}
	
	public User getSingleUser(String email) throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", ((MyUI)UI.getCurrent()).getJwt());
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<User> response = r.exchange(new URI(Constant.WS_CHECK_USER_URL+email),HttpMethod.GET, request, User.class);
			if(response.getStatusCode() == HttpStatus.OK){
				return response.getBody();
			}
			
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}
	
	public Boolean checkUserIdExist(String userId)  throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", ((MyUI)UI.getCurrent()).getJwt());
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_CHECK_USER_URL+userId),HttpMethod.GET, request, BasicResponse.class);
			if(response.getBody().getInfo() != null){
				return true;
			}
			
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return false;
	}





}
