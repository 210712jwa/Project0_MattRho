package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.AddorEditClientDTO;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.util.ConnectionUtility;

public class ClientDAOImpl implements ClientDAO {

	@Override
	public List<Client> getAllClients() throws SQLException {
		// TODO Auto-generated method stub

		List<Client> clients = new ArrayList<>();
		List<Account> accounts = new ArrayList<>();
		
		try (Connection con = ConnectionUtility.getConnection()) {
			Statement stmt = con.createStatement();
			String sql = "SELECT * FROM `project-0`.clients";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				//List<Account> accounts = rs.getArray(id)
				
				//Account a = new Account(id);
				//accounts.add(a);
				
				
				Client client = new Client(id, name, accounts);
				clients.add(client);
			}
		}

		return clients;
		//return accounts;
	}

	@Override
	public Client getClientById(int id) throws SQLException {
		// TODO Auto-generated method stub
		try (Connection con = ConnectionUtility.getConnection()) {
			String sql = "SELECT * FROM `project-0`.clients WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			List<Account> accounts = new ArrayList<>();
			
			if (rs.next()) {
				int client_id = rs.getInt("id");
				String name = rs.getString("name");
				//int age = rs.getInt("age");
				//Account a = new Account(id);
				

				Client client = new Client(client_id, name, accounts);

				return client;

			} else {
				return null;
			}
		}
	}

	@Override
	public Client addClient(AddorEditClientDTO client) throws SQLException {
		// TODO Auto-generated method stub
		try (Connection con = ConnectionUtility.getConnection()) {
			String sql = "INSERT INTO `project-0`.clients (id, name) VALUES (?, ?)";
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, client.getId());
			pstmt.setString(2, client.getName());

			int recordsUpdated = pstmt.executeUpdate();

			if (recordsUpdated != 1) {
				throw new SQLException("Could not insert a client record");
			}
			
			Client createdClient = new Client(client.getId(), client.getName(), client.getAccounts());
			
			return createdClient;
			//Ship createdShip = new Ship(0, ship.getName(), ship.getAge());
			
			//ResultSet generatedKeys = pstmt.getGeneratedKeys();
			//if (generatedKeys.next()) {
				//setId(generatedKeys.getInt(1));
			//	Client createdClient = new Client(generatedKeys.getInt(1), client.getName());
						//generatedKeys.getInt(1), ship.getName(), ship.getAge());
			//	return createdClient;
			//} else {
			//	throw new SQLException("id could not be auto-generated for Client");
			//}
			
			//return createdShip;
			//return null;
		}
	}

	@Override
	public Client editClient(int clientId, AddorEditClientDTO client) throws SQLException {
		// TODO Auto-generated method stub
		//return null;
		
		try (Connection con = ConnectionUtility.getConnection()) {
			String sql = "UPDATE `project-0`.clients SET name = ? WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, client.getName());
			//pstmt.setInt(2, client.getAge());
			pstmt.setInt(2, clientId);
			
			int recordsUpdated = pstmt.executeUpdate();
			
			if (recordsUpdated != 1) {
				throw new SQLException("Record was not able to be updated");
				
			}
			return new Client(clientId, client.getName(), client.getAccounts());//, ship.getAge());
		}
		//catch (SQLException e)
	}
	
	@Override
	public void deleteClient(int clientId) throws SQLException {
		
		try (Connection con = ConnectionUtility.getConnection()) {
			String sql = "DELETE FROM `project-0`.clients WHERE id = ?";
			PreparedStatement pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, clientId);
			
			int recordsDeleted = pstmt.executeUpdate();
			
			if (recordsDeleted != 1) {
				throw new SQLException("Record was not able to be deleted");
			}
		}
			
	}
//
//	@Override
//	public List<Account> getAllAccountsFromClient(int )
	

}
