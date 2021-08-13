package com.revature.dao;

import java.sql.SQLException;
import java.util.List;

import com.revature.dto.AddorEditClientDTO;
import com.revature.model.Client;

public interface ClientDAO {

	public abstract List<Client> getAllClients() throws SQLException;
	
	public abstract Client getClientById(int id) throws SQLException;
	
	public abstract Client addClient(AddorEditClientDTO client) throws SQLException;
	
	public abstract Client editClient(int clientId, AddorEditClientDTO client) throws SQLException;
	
	public abstract void deleteClient(int clientId) throws SQLException;
	
	
}
