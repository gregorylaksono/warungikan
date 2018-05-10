package com.warungikan.webapp.service;

import java.util.List;
import java.util.Set;

import org.warungikan.api.model.response.AgentStock;
import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.TransactionState;

import com.warungikan.webapp.manager.TransactionManagerImpl.TrxState;

public class TransactionService implements ITransactionService {

	@Override
	public Boolean addBalanceUser(String sessionId, String userId, Long balance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction checkTransaction(String sessionId, String customer_id, String agent_id, Long total_km,
			Set<TransactionDetail> details) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction addTransaction(String sessionId, String agent_id, Long transport_prices, Long total_km,
			Set<TransactionDetail> details) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getTransactionCustomer(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> getTransactionAgent(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TransactionState> getTransactionState(String sessionId, String trx_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getBalanceCustomer(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getBalanceAgent(String sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long calculateTransportPrice(String sessionId, String agent_id, Long total_km) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean markTransaction(String sessionId, String trx_id, TrxState state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isCustomerLegitimateForTransaction(String sessionId, String customer_id, String agent_id,
			Long total_km, Set<TransactionDetail> details) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AgentStock> getAgentBasedCustomerLocation(String sessionId, Set<TransactionDetail> details) {
		// TODO Auto-generated method stub
		return null;
	}

}
