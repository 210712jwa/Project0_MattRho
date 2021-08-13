package com.revature.dao;

//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.dto.AddorEditAccountDTO;
import com.revature.model.Account;
//import com.revature.util.ConnectionUtility;
//import com.sun.tools.javac.util.List;

import java.util.List;

public interface AccountDao {

	List<Account> getAllAccountsFromClient(int clientId) throws SQLException; //{
		
	List<Account> getAccountsBetween(int clientId, int lessThan, int greaterThan) throws SQLException;
	
	List<Account> getAccountsLessThan(int clientId, int lessThan) throws SQLException;
	
	List<Account> getAccountsGreaterThan(int clientId, int greaterThan) throws SQLException;
	
	public abstract Account getAccountById(int clientId, int accountId) throws SQLException;
	
	public abstract Account addAccount(AddorEditAccountDTO account) throws SQLException;
	
	public abstract Account editAccount(int clientId, int accountId, AddorEditAccountDTO account) throws SQLException;
	
	public abstract void deleteAccount(int clientId, int accountId) throws SQLException;
	
	//public abstract Account addAccount(AddorEd)
	//}
	
}
