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
import org.warungikan.api.model.ChangePassword;
import org.warungikan.api.model.request.VLatLng;
import org.warungikan.db.model.AgentData;
import org.warungikan.db.model.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.util.Constant;
import com.warungikan.webapp.ws.request.BasicResponse;


public class UserManagerImpl  {


	public String login(String username, String password)throws UserSessionException,WarungIkanNetworkException{
		String jwt = null;
		Map<String, String> map = new HashMap<>();

		try {
			map.put("username", username);
			map.put("password", password);
			RestTemplate t = new RestTemplate();
			ResponseEntity<String> response = t.postForEntity(new URI(Constant.WS_LOGIN_URL), map, String.class);
			if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}
			return response.getHeaders().getFirst("Authorization");
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}

	public List<User> getAllUsers(String sessionId) throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
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
	
	public AgentData getAgentData(String sessionId) throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<AgentData> response = r.exchange(new URI(Constant.WS_GET_AGENT_DATA_URL),HttpMethod.GET, request, AgentData.class);
			AgentData body = response.getBody();
			return body;
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}

	public static void main(String[] args) throws TimeoutException {

	}
	public Boolean createUserCustomer(String sessionId,String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password) throws UserSessionException,WarungIkanNetworkException{
		try {
			User u = User.UserFactory(name, email, telNo, address, city, latitude, longitude, password);
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
//			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(u, headers);
			ResponseEntity<BasicResponse> response = r.postForEntity(new URI(Constant.WS_POST_REGISTER_USER_URL), request, BasicResponse.class);
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
	public String createUserAgent(String sessionId,String name, String email, String telNo, String address, String city, String latitude,
			String longitude, String password, String pricePerKm) throws UserSessionException,WarungIkanNetworkException{
		try {
			User u = User.UserFactory(name, email, telNo, address, city, latitude, longitude, password);
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(u, headers);
			ResponseEntity<BasicResponse> response = r.postForEntity(new URI(Constant.WS_CREATE_USER_AGENT_URL+"/"+pricePerKm), request, BasicResponse.class);
			return response.getBody().getInfo();
		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}
	public Integer updateAgentAsAdmin(String sessionId,String name, String email,String emailNew, String telNo, String address, String city, String latitude,
			String longitude) throws UserSessionException,WarungIkanNetworkException{
		try {
			User u = getSingleUserAsAdmin(sessionId, email);
			u.setName(name).setEmail(emailNew).setTelpNo(telNo).setAddress(address).
			setCity(city).setLatitude(Double.parseDouble(latitude)).setLongitude(Double.parseDouble(longitude));

			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(u, headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_UPDATE_USER_AGENT_URL+"/"+email),HttpMethod.PUT, request, BasicResponse.class);
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



	public User getSingleUserAsAdmin(String sessionId,String email) throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<User> response = r.exchange(new URI(Constant.WS_CHECK_USER_AS_ADMIN_URL+"/"+email),HttpMethod.GET, request, User.class);
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


	public Boolean deleteUser(String sessionId,String userId)  throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_CHECK_USER_AS_ADMIN_URL+"/"+userId),HttpMethod.DELETE, request, BasicResponse.class);
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

	// USER_ROLE SECTION
	public User getUser(String sessionId)  throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			ResponseEntity<User> response = r.exchange(new URI(Constant.WS_CHECK_USER_AS_USER_URL),HttpMethod.GET, request, User.class);
			if(response.getStatusCode() == HttpStatus.OK){
				return response.getBody();
			}else{
				return null;
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

	public Integer updateUserData(String sessionId, String name, String email, String telNo, String address, String city) throws UserSessionException,WarungIkanNetworkException{
		try {
			User u = User.UserFactory(name, email, telNo, address, city, null, null,null);

			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(u, headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_UPDATE_SELF_USER_URL),HttpMethod.PUT, request, BasicResponse.class);
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
	public Boolean changePassword(String sessionId,String newPassword, String oldPassword) throws UserSessionException,WarungIkanNetworkException{
		try {
			ChangePassword pwd = new ChangePassword();
			pwd.setNewPassword(newPassword);
			pwd.setPassword(oldPassword);
			
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(pwd, headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_UPDATE_CHANGE_PWD_URL),HttpMethod.PUT, request, BasicResponse.class);
			if(response.getBody().getCode().equals("SUCCESS")){
				return true;
			}else{
				return false;
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
	
	public Boolean updateCoordinate(String jwt, VLatLng latlong) throws UserSessionException, WarungIkanNetworkException{
		try {
			
			RestTemplate r = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(latlong, headers);
			ResponseEntity<BasicResponse> response = r.exchange(new URI(Constant.WS_PUT_USER_COORDINATE_URL),HttpMethod.PUT, request, BasicResponse.class);
			if(response.getStatusCodeValue() == 202){
				return true;
			}
			return false;

		} catch (Exception e) {
			if((e instanceof HttpClientErrorException) || (e instanceof HttpServerErrorException)){
				throw new UserSessionException("token is wrong");
			}else if(e instanceof ResourceAccessException){
				throw new WarungIkanNetworkException("Could not connect to server");
			}
		}
		return null;
	}
	
	public Boolean verify(String code)throws UserSessionException,WarungIkanNetworkException{
		try {
			RestTemplate r = new RestTemplate();

			ResponseEntity<String> response = r.getForEntity(new URI(Constant.WS_VERIFY_USER_URL+"?verification_id="+code), String.class);
			if(response.getStatusCodeValue() == 202){
				return true;
			}
			if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}
			return false;
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}



}
