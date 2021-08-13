package com.revature.service;

import java.sql.SQLException;
import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddorEditAccountDTO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.model.Account;

public class AccountService {

	private AccountDao accountDao;
	private ClientDAO clientDao;
	
	public AccountService() {
		this.accountDao = new AccountDaoImpl();
		this.clientDao = new ClientDAOImpl();
	}
	
	public AccountService(ClientDAO clientDao, AccountDao accountDao) {
		this.clientDao = clientDao;
		this.accountDao = accountDao;
	}
	public List<Account> getAllAccountsFromClient (String clientIdString, String lessThan, String greaterThan) throws BadParameterException, DatabaseException, ClientNotFoundException {
		boolean lessThanBool = false;
		boolean greaterThanBool = false;
		int greaterThanInt = 0;
		int lessThanInt = 0;
		List<Account> accounts = null;
		
		try {
			int clientId = Integer.parseInt(clientIdString);
			
			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
				}
			//List<Account> accounts = accountDao.getAllAccountsFromClient(clientId);
			
			try {
				
				if (lessThan != null) {
					lessThanInt = Integer.parseInt(lessThan);
					lessThanBool = true;
				}
			} catch (NumberFormatException e) {
				throw new BadParameterException(lessThan + " was passed in by the user as the less than value, " + "but it is not an int");
			}
			try {
				if (greaterThan != null) {
					greaterThanInt = Integer.parseInt(greaterThan);
					greaterThanBool = true;
				}
			} catch (NumberFormatException e) {
				throw new BadParameterException(greaterThan + " was passed in by the user as the greater than value, " + "but it is not an int");
			}
			
			if (lessThanBool) {
				accounts = accountDao.getAccountsLessThan(clientId, lessThanInt);
			}
			else if (greaterThanBool) {
				accounts = accountDao.getAccountsGreaterThan(clientId, greaterThanInt);			
			}
			else {
				accounts = accountDao.getAllAccountsFromClient(clientId);
			}
			
			if (accounts == null) {
				throw new ClientNotFoundException("No accounts found for client");
			}
			
			return accounts;
	} catch (SQLException e) {
		throw new DatabaseException(e.getMessage());
	} catch (NumberFormatException e) {
		throw new BadParameterException(clientIdString + " was passed in by the user as the id, " + "but it is not an int");
	}
}
	
public Account getAccountById(String clientIdString, String accountIdString) throws ClientNotFoundException, DatabaseException, BadParameterException {
	try {
		int clientId = Integer.parseInt(clientIdString);
		
		if (clientDao.getClientById(clientId) == null) {
			throw new ClientNotFoundException("Client with id " + clientId + " was not found");
		}
		
		try {
			int accountId = Integer.parseInt(accountIdString);
			
			if (accountDao.getAccountById(clientId, accountId) == null) {
				throw new ClientNotFoundException("Account with id " + accountId + " was not found");
			}
			
			Account targetAccount = accountDao.getAccountById(clientId, accountId);
			return targetAccount;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(accountIdString + " was passed in by the user as the id, " + "but it is not an int");
		}
	} catch (SQLException e) {
		throw new DatabaseException(e.getMessage());
	} catch (NumberFormatException e) {
		throw new BadParameterException(clientIdString + "was passed in by the user as the id, " + "but it is not an int");
	}
}
	public Account addAccount(AddorEditAccountDTO account) throws DatabaseException, BadParameterException {
		//if (account.getName().trim().equals("") && account.getBalance() < 0) {
		//	throw
		//}
		if (account.getBalance() < 0) {
			throw new BadParameterException("Account balance cannot be less than 0");
			
			
		}
		try {
			Account addedAccount = accountDao.addAccount(account);
			
			return addedAccount;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
			
		}
	}
	public Account editAccount(String clientIdString, String accountIdString, AddorEditAccountDTO accountToEdit) throws ClientNotFoundException, BadParameterException, DatabaseException {
		try {
			int clientId = Integer.parseInt(clientIdString);
			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
			}
			try {
				int accountId = Integer.parseInt(accountIdString);
				
				if (accountDao.getAccountById(clientId, accountId) == null) {
					throw new ClientNotFoundException("Account with id " + accountId + " was not found");
					
				}
				Account targetAccount = accountDao.editAccount(clientId, accountId, accountToEdit);
				
				return targetAccount;
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
				
			} catch (NumberFormatException e) {
				throw new BadParameterException(accountIdString + " was passed in by the user as the id, " + "but it is not an int"); 
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}catch (NumberFormatException e) {
			throw new BadParameterException(clientIdString + " was passed in by the user as the id, " + "but it is not an int");
		}
	}
	
	public void deleteAccount(String clientIdString, String accountIdString) throws ClientNotFoundException, DatabaseException, BadParameterException {
		try {
			int clientId = Integer.parseInt(clientIdString);
			
			if (clientDao.getClientById(clientId) == null) {
				throw new ClientNotFoundException("Client with id " + clientId + " was not found");
			}
			try {
				int accountId = Integer.parseInt(accountIdString);
				
				if (accountDao.getAccountById(clientId, accountId) == null) {
					throw new ClientNotFoundException("Account with id " + accountId + " was not found");
					
					
				} accountDao.deleteAccount(clientId, accountId);
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			} catch (NumberFormatException e) {
				throw new BadParameterException(accountIdString + " was passed in by the user as the id, " + "but it is not an int");
			}
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(clientIdString + "was passed in by the user as the id, " + "but it is not an int");
		}
	}
}
