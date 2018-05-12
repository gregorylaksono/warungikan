package com.warungikan.webapp.service;

import java.util.List;
import java.util.Set;

import org.warungikan.api.model.response.AgentStock;
import org.warungikan.db.model.TopupWalletHistory;
import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.TransactionState;

import com.warungikan.webapp.manager.TransactionManagerImpl;


public interface ITransactionService {

	public Boolean addBalanceUser(String sessionId, String userId, String balance, String topup_date, String ref_bank_no);
	public Transaction checkTransaction(String sessionId, String customer_id, String agent_id, Long total_km, Set<TransactionDetail> details);
	public Transaction addTransaction(String sessionId, String agent_id, Long transport_prices, Long total_km,Set<TransactionDetail> details);
	public List<Transaction> getTransactionCustomer(String sessionId);
	public List<Transaction> getTransactionAgent(String sessionId);
	public List<TransactionState> getTransactionState(String sessionId, String trx_id);
	public Long getBalanceCustomer(String sessionId);
	public Long getBalanceAgent(String sessionId);
	public Long calculateTransportPrice(String sessionId, String agent_id, Long total_km);
	public Boolean markTransaction(String sessionId, String trx_id, TransactionManagerImpl.TrxState state);
	public Boolean isCustomerLegitimateForTransaction(String sessionId, String customer_id, String agent_id, Long total_km,Set<TransactionDetail> details);
	public List<AgentStock> getAgentBasedCustomerLocation(String sessionId, Set<TransactionDetail> details);
	public List<Transaction> getAllTransaction(String jwt);
	public List<TopupWalletHistory> getAllTopupHistory(String jwt);
	public List<TopupWalletHistory> getTopupHistorySingleUser(String jwt);
}
