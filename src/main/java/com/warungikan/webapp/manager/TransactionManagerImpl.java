package com.warungikan.webapp.manager;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.warungikan.api.model.response.AgentStock;
import org.warungikan.db.model.TopupWalletHistory;
import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.TransactionState;
import org.warungikan.db.model.User;

import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.util.Constant;


public class TransactionManagerImpl {
	
	public enum TrxState {
		PAID, PROCESSING, DELIVERING, RECEIVING, CANCEL
	}
	
	public Boolean addBalanceUser(String sessionId, String userId, String balance, String topup_date, String ref_bank_no)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<String> response = t.postForEntity(new URI(Constant.WS_POST_ADD_BALANCE_URL+"?user_id="+userId+"&balance="+String.valueOf(balance))+"&topup_date="+topup_date+"&ref_bank_no="+ref_bank_no, request, String.class);
			if(response.getStatusCodeValue() == 202){
				return true;
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return false;
			}
			
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
	
	public Transaction checkTransaction(String sessionId, String customer_id, String agent_id, Long total_km, Set<TransactionDetail> details)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(details,headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<Transaction> response = t.postForEntity(new URI(Constant.WS_POST_CHECK_TRANSCTION_URL+"/"+customer_id+"/"+agent_id+"/"+String.valueOf(total_km)), request, Transaction.class);
			
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
	
	public Transaction addTransaction(String sessionId, String agent_id, Long transport_prices, Long total_km,Set<TransactionDetail> details)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(details,headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<Transaction> response = t.postForEntity(new URI(Constant.WS_POST_ADD_TRANSCTION_URL+"/"+agent_id+"/"+String.valueOf(transport_prices)+"/"+String.valueOf(transport_prices)), request, Transaction.class);
			
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
	
	public List<Transaction> getTransactionCustomer(String sessionId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<List<Transaction>> response = t.exchange(new URI(Constant.WS_GET_TRANSCTION_CUSTOMER_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<Transaction>>(){});
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
	
	public List<Transaction> getTransactionAgent(String sessionId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<List<Transaction>> response = t.exchange(new URI(Constant.WS_GET_TRANSCTION_AGENT_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<Transaction>>(){});
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
	
	public List<TransactionState> getTransactionState(String sessionId, String trx_id)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<List<TransactionState>> response = t.exchange(new URI(Constant.WS_GET_TRANSCTION_STATE_URL+"?state_id="+trx_id),HttpMethod.GET, request, new ParameterizedTypeReference<List<TransactionState>>(){});
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
	
	public Long getBalanceCustomer(String sessionId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<Map> response = t.exchange(new URI(Constant.WS_GET_TRANSCTION_BALANCE_CUSTOMER_URL),HttpMethod.GET, request, Map.class);
			if(response.getStatusCodeValue() == 202){
				Map bodyResponse = response.getBody();
				Long balance = Long.parseLong(String.valueOf(bodyResponse.get("balance")));
				return balance;
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
	
	public Long getBalanceAgent(String sessionId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<Map> response = t.exchange(new URI(Constant.WS_GET_TRANSCTION_BALANCE_AGENT_URL),HttpMethod.GET, request, Map.class);
			if(response.getStatusCodeValue() == 202){
				Map bodyResponse = response.getBody();
				Long balance = Long.parseLong(String.valueOf(bodyResponse.get("balance")));
				return balance;
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
	
	public Long calculateTransportPrice(String sessionId, String agent_id, Long total_km)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<Map> response = t.exchange(new URI(Constant.WS_GET_TRANSACTION_CALC_TRANSPORT_URL+"?agent_id="+agent_id+"&total_km="+String.valueOf(total_km)),HttpMethod.GET, request, Map.class);
			
			if(response.getStatusCodeValue() == 202){
				Map bodyResponse = response.getBody();
				Long price = Long.parseLong(String.valueOf(bodyResponse.get("transport_price")));
				return price;
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
	
	public Boolean markTransaction(String sessionId, String trx_id, TrxState state)throws UserSessionException,WarungIkanNetworkException{
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			String url = null;
			if(state.equals(TrxState.PROCESSING)) {
				url = Constant.WS_POST_TRANSCTION_MARK_PROCESSING_URL;
			}
			else if(state.equals(TrxState.PAID)) {
				url = Constant.WS_POST_TRANSCTION_MARK_PAID_URL;
			}
			else if(state.equals(TrxState.RECEIVING)) {
				url = Constant.WS_POST_TRANSCTION_MARK_RECEIVING_URL;
			}
			else if(state.equals(TrxState.DELIVERING)) {
				url = Constant.WS_POST_TRANSCTION_MARK_DELIVERING_URL;
				
			}else if(state.equals(TrxState.CANCEL)){
				url = Constant.WS_POST_TRANSCTION_MARK_CANCEL_URL;
			}
			
			ResponseEntity<String> response = t.exchange(new URI(url+"/"+trx_id), HttpMethod.PUT, request, String.class);
			
			if(response.getStatusCodeValue() == 202){
				return true;
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return false;
			}
			
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
	
	public Boolean isCustomerLegitimateForTransaction(String sessionId, String customer_id, String agent_id, Long total_km,Set<TransactionDetail> details)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(details,headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<Map> response = t.postForEntity(new URI(Constant.WS_POST_TRANSCTION_IS_LEGIT_URL+"?customer_id="+customer_id+"&agent="+agent_id+"&total_km="+String.valueOf(total_km)), request, Map.class);
			
			if(response.getStatusCodeValue() == 202){
				Map bodyResponse = response.getBody();
				Boolean is_legit = Boolean.parseBoolean(String.valueOf(bodyResponse.get("is_legit")));
				return is_legit;
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return false;
			}
			
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
	
	public List<AgentStock> getAgentBasedCustomerLocation(String sessionId, Set<TransactionDetail> details)throws UserSessionException,WarungIkanNetworkException{
		try{
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(details,headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<List<AgentStock>> response = t.exchange(new URI(Constant.WS_POST_CALCULATE_AGENT_PRE),HttpMethod.POST, request, new ParameterizedTypeReference<List<AgentStock>>(){});
			if(response.getStatusCodeValue() == 202){
				return response.getBody();
			}else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return null;
			}
			
		}catch(Exception e){
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
	
	public List<Transaction> getAllTransaction(String sessionId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<List<Transaction>> response = t.exchange(new URI(Constant.WS_GET_ALL_TRANSCTION_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<Transaction>>(){});
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
	
	public List<TopupWalletHistory> getAllTopups(String sessionId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<List<TopupWalletHistory>> response = t.exchange(new URI(Constant.WS_GET_TOPUP_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<TopupWalletHistory>>(){});
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
	
	public List<TopupWalletHistory> getTopupsByUser(String sessionId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<List<TopupWalletHistory>> response = t.exchange(new URI(Constant.WS_GET_TOPUP_USER_URL),HttpMethod.GET, request, new ParameterizedTypeReference<List<TopupWalletHistory>>(){});
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
	public List<TransactionDetail> getTransactionDetail(String sessionId, String trxId)throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", sessionId);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			
			ResponseEntity<List<TransactionDetail>> response = t.exchange(new URI(Constant.WS_GET_TRX_DETAIL_URL+"/"+trxId),HttpMethod.GET, request, new ParameterizedTypeReference<List<TransactionDetail>>(){});
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

	public Boolean releaseTopup(String jwt, String topupId) throws UserSessionException,WarungIkanNetworkException{
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<String> response = t.exchange(new URI(Constant.WS_PUT_TOP_UP_URL+"/"+topupId),HttpMethod.PUT, request, String.class);
			if(response.getStatusCodeValue() == 202){
				return true;
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return false;
			}
			
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}	}

	public Boolean doTopup(String jwt, TopupWalletHistory h)  throws UserSessionException,WarungIkanNetworkException{

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", jwt);
			HttpEntity request = new HttpEntity<>(h,headers);
			RestTemplate t = new RestTemplate();
			ResponseEntity<String> response = t.postForEntity(new URI(Constant.WS_POST_TOP_UP_URL), request, String.class);
			if(response.getStatusCodeValue() == 202){
				return true;
			}
			else if(response.getStatusCodeValue() == 401){
				throw new UserSessionException("Could not identified user");
			}else {
				return false;
			}
		} catch (Exception e) {
			throw new WarungIkanNetworkException("Could not connect to server");
		}
	}
}
