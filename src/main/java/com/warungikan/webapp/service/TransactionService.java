package com.warungikan.webapp.service;

import java.util.List;
import java.util.Set;

import org.warungikan.api.model.response.AgentStock;
import org.warungikan.db.model.Transaction;
import org.warungikan.db.model.TransactionDetail;
import org.warungikan.db.model.TransactionState;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.warungikan.webapp.exception.UserSessionException;
import com.warungikan.webapp.exception.WarungIkanNetworkException;
import com.warungikan.webapp.manager.TransactionManagerImpl;
import com.warungikan.webapp.manager.TransactionManagerImpl.TrxState;

public class TransactionService implements ITransactionService {

	private TransactionManagerImpl trxManager = new TransactionManagerImpl();
	@Override
	public Boolean addBalanceUser(String sessionId, String userId, Long balance) {
		try {
			Boolean result = trxManager.addBalanceUser(sessionId, userId, balance);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Transaction checkTransaction(String sessionId, String customer_id, String agent_id, Long total_km,
			Set<TransactionDetail> details) {
		try {
			Transaction result = trxManager.checkTransaction(sessionId, customer_id, agent_id, total_km, details);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Transaction addTransaction(String sessionId, String agent_id, Long transport_prices, Long total_km,
			Set<TransactionDetail> details) {
		try {
			Transaction result = trxManager.addTransaction(sessionId, agent_id, transport_prices, total_km, details);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public List<Transaction> getTransactionCustomer(String sessionId) {
		List<Transaction> result;
		try {
			result = trxManager.getTransactionCustomer(sessionId);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public List<Transaction> getTransactionAgent(String sessionId) {
		try {
			List<Transaction> result = trxManager.getTransactionAgent(sessionId);
			return result;
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		
		return null;
	}

	@Override
	public List<TransactionState> getTransactionState(String sessionId, String trx_id) {
		try {
			List<TransactionState> result = trxManager.getTransactionState(sessionId, trx_id);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Long getBalanceCustomer(String sessionId) {
		try {
			Long result = trxManager.getBalanceCustomer(sessionId);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Long getBalanceAgent(String sessionId) {
		try {
			Long result = trxManager.getBalanceAgent(sessionId);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		
		return null;
	}

	@Override
	public Long calculateTransportPrice(String sessionId, String agent_id, Long total_km) {
		try {
			Long result = trxManager.calculateTransportPrice(sessionId, agent_id, total_km);
			return result;
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public Boolean markTransaction(String sessionId, String trx_id, TrxState state) {
		try {
			Boolean result = trxManager.markTransaction(sessionId, trx_id, state);
			return result;
		}catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		
		return null;
	}

	@Override
	public Boolean isCustomerLegitimateForTransaction(String sessionId, String customer_id, String agent_id,
			Long total_km, Set<TransactionDetail> details) {
		try {
			return trxManager.isCustomerLegitimateForTransaction(sessionId, customer_id, agent_id, total_km, details);
		} catch (UserSessionException e) {
			logout();
			Notification.show("Your login data has been altered", Type.TRAY_NOTIFICATION);
		} catch (WarungIkanNetworkException e) {
			Notification.show("Can not connect to server. Please contact your admin", Type.ERROR_MESSAGE);
		}
		return null;
	}

	@Override
	public List<AgentStock> getAgentBasedCustomerLocation(String sessionId, Set<TransactionDetail> details) {
		try {
			return trxManager.getAgentBasedCustomerLocation(sessionId, details);
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
