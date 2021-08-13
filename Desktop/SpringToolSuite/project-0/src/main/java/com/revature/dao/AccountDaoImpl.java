package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddorEditAccountDTO;
import com.revature.model.Account;
import com.revature.util.ConnectionUtility;

public class AccountDaoImpl implements AccountDao{
	
@Override
public List<Account> getAllAccountsFromClient(int clientId) throws SQLException {
	try (Connection con = ConnectionUtility.getConnection()) {
		List<Account> accounts = new ArrayList<>();
		
		String sql = "SELECT * FROM `project-0`.accounts a WHERE a.client_id = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1,  clientId);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int balance = rs.getInt("balance");
			
			
			Account a = new Account(id, balance, clientId);
			accounts.add(a);
			
		}
		return accounts;
	}
}

@Override
public Account getAccountById(int clientId, int accountId) throws SQLException {
	try(Connection con = ConnectionUtility.getConnection()) {
		
		String sql = "SELECT * FROM `project-0`.accounts a WHERE a.client_id = ? AND a.id = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1,  clientId);
		pstmt.setInt(2,  accountId);
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			int id = rs.getInt("id");
			int balance = rs.getInt("balance");
			int client_id = rs.getInt("client_id");
			
			Account account = new Account(id, balance, client_id);
			
			return account;
		} else {
			return null;
		}
	}
}

@Override
public List<Account> getAccountsBetween(int clientId, int lessThan, int greaterThan) throws SQLException {
	// TODO Auto-generated method stub
	try (Connection con = ConnectionUtility.getConnection()) {
		List<Account> accounts = new ArrayList<>();
		
		String sql = "SELECT * FROM `project-0`.accounts a WHERE a.client_id = ? AND a.balance <= ? AND a.balance >= ?";
	
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, clientId);
		pstmt.setInt(2,  lessThan);
		pstmt.setInt(3, greaterThan);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			//String name = rs.getString("name");
			int balance = rs.getInt("balance");
			
			Account a = new Account(id, balance, clientId);
			accounts.add(a);
			
			
		}
		return accounts;
	}
	//return null;
}



@Override
public List<Account> getAccountsLessThan(int clientId, int lessThan) throws SQLException {
	// TODO Auto-generated method stub
	try (Connection con = ConnectionUtility.getConnection()) {
		List<Account> accounts = new ArrayList<>();
		
		String sql = "SELECT * FROM `project-0`.accounts a WHERE a.client_id = ? AND a.balance <= ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, clientId);
		pstmt.setInt(2, lessThan);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int balance = rs.getInt("balance");
			
			Account a = new Account(id, balance, clientId);
			accounts.add(a);
		}
		return accounts;
	}
}

@Override
public List<Account> getAccountsGreaterThan(int clientId, int greaterThan) throws SQLException {
	// TODO Auto-generated method stub
	try (Connection con = ConnectionUtility.getConnection()) {
		List<Account> accounts = new ArrayList<>();
		
		String sql = "SELECT * FROM `project-0`.accounts a WHERE a.client_id = ? AND a.balance >= ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, clientId);
		pstmt.setInt(2, greaterThan);
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int id = rs.getInt("id");
			int balance = rs.getInt("balance");
			
			Account a = new Account(id, balance, clientId);
			accounts.add(a);
		}
		return accounts;
	}
}

@Override
public Account addAccount(AddorEditAccountDTO account) throws SQLException {
	// TODO Auto-generated method stub
	try (Connection con = ConnectionUtility.getConnection()) {
		String sql = "INSERT INTO `project-0`.accounts (id, balance, client_id) VALUES (?, ?, ?)";
		PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		//pstmt.setString(1,  account.getName());
		pstmt.setInt(1,  account.getAccountId());
		pstmt.setInt(2, account.getBalance());
		pstmt.setInt(3, account.getClientId());
		
		int recordsUpdated = pstmt.executeUpdate();
		
		if (recordsUpdated != 1) {
			throw new SQLException("Could not insert an account record");
			
		}
		
		Account createdAccount = new Account(account.getAccountId(), account.getBalance(), account.getClientId());
		return createdAccount;
		//ResultSet generatedKeys = pstmt.getGeneratedKeys();
		
		//if (generatedKeys.next()) {
		//	Account createdAccount = new Account(generatedKeys.getInt(1), account.getBalance(), account.getClientId());
		//	return createdAccount;
		//} else {
		//	throw new SQLException("Autogenerated id could not be obtained for account");
		//}
	}
}

@Override
public Account editAccount(int clientId, int accountId, AddorEditAccountDTO account) throws SQLException {
	// TODO Auto-generated method stub
	try (Connection con = ConnectionUtility.getConnection()) {
		String sql = "UPDATE `project-0`.accounts a SET balance = ? WHERE a.client_id = ? AND a.id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, account.getBalance());
		pstmt.setInt(2, clientId);
		pstmt.setInt(3, accountId);
		
		int recordsUpdated = pstmt.executeUpdate();
		if (recordsUpdated != 1) {
			throw new SQLException("Record was not able to be updated");
			
		}
		
		return new Account(accountId, account.getBalance(), clientId);
	}
}

@Override
public void deleteAccount(int clientId, int accountId) throws SQLException {
	// TODO Auto-generated method stub
	try (Connection con = ConnectionUtility.getConnection()) {
		String sql = "DELETE FROM `project-0`.accounts WHERE client_id = ? AND id = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, clientId);
		pstmt.setInt(2,  accountId);
		
		int recordsDeleted = pstmt.executeUpdate();
		
		if (recordsDeleted != 1) {
			throw new SQLException("Record was not able to be deleted");
		}
	}
}

}
