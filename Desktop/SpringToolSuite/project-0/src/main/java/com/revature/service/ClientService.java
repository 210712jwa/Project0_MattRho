package com.revature.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.dao.AccountDao;
import com.revature.dao.AccountDaoImpl;
import com.revature.dao.ClientDAO;
import com.revature.dao.ClientDAOImpl;
import com.revature.dto.AddorEditClientDTO;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;

public class ClientService {

	private ClientDAO clientDao;
	private AccountDao accountDao;
	
	public ClientService () {
		this.clientDao = new ClientDAOImpl();
		this.accountDao = new AccountDaoImpl();
	}
	
	public ClientService(ClientDAO mockedClientDaoObject, AccountDao mockedAccountDaoObject) {
		this.clientDao = mockedClientDaoObject;
		this.accountDao = mockedAccountDaoObject;
		
	}
	
	public List<Client> getAllClients() throws DatabaseException{
		try {
		List<Client> clients = clientDao.getAllClients();
		//return clients;
		
			for (Client client : clients) {
				List<Account> accounts = accountDao.getAllAccountsFromClient(client.getId());
				client.setAccounts(accounts);
			}
		return clients;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException("Something went wrong with our DAO operations");
		}
	}
	public Client getClientById(String id) throws DatabaseException, ClientNotFoundException, BadParameterException{
		try {
			int int_id = Integer.parseInt(id);
			Client client = clientDao.getClientById(int_id);
			if (client == null) {
				throw new ClientNotFoundException("Client with id " + int_id + "was not found");
			}
			
			List<Account> accounts = accountDao.getAllAccountsFromClient(int_id);
			client.setAccounts(accounts);
			
			return client;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DatabaseException("Something went wrong with our DAO operations");
		} catch (NumberFormatException e) {
			throw new BadParameterException(id + "was passed in by the user as the id, " + "but it was not an int");
		}
	}
	public Client addClient(AddorEditClientDTO client) throws DatabaseException, BadParameterException {
		if (client.getName().trim().equals("") && client.getId() < 0) {
			throw new BadParameterException("Client name cannot be blank and age cannot be less than 0");
		}
		if (client.getName().trim().equals("")) {
			throw new BadParameterException("Client name cannot be blank");
		}
		
		if (client.getId() < 0) {
			throw new BadParameterException("Client id cannot be less than 0");
		}
		try {
			Client addedClient = clientDao.addClient(client);
			addedClient.setAccounts(new ArrayList<>());
			return addedClient;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}
	}
	public Client editClient(String clientId, AddorEditClientDTO client) throws DatabaseException, ClientNotFoundException, BadParameterException{
		
		
		try {
			
			int int_clientId = Integer.parseInt(clientId);
			
			if (clientDao.getClientById(int_clientId) == null) {
				throw new ClientNotFoundException("Client with id" + int_clientId + " was not found");
			}
			Client editedClient = clientDao.editClient(int_clientId, client);
			List<Account> accounts = accountDao.getAllAccountsFromClient(int_clientId);
			editedClient.setAccounts(accounts);
			
			
			return editedClient;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
			throw new DatabaseException(e.getMessage());
		} catch (NumberFormatException e) {
			throw new BadParameterException(clientId + " was passed in by the user as the id, " + "but it was not an int");
		}
		//return null;
	}
	
	public void deleteClient(String clientId) throws DatabaseException, ClientNotFoundException, BadParameterException{
		try {
			
			int int_clientId = Integer.parseInt(clientId);
			
			if (clientDao.getClientById(int_clientId) == null) {
				throw new ClientNotFoundException("Client with id" + int_clientId + " was not found");
			}
			//Client deletedClient = clientDao.deleteClient(int_clientId);
			clientDao.deleteClient(int_clientId);
			//return deletedClient;
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
			
		} catch (NumberFormatException e) {
			throw new BadParameterException(clientId + " was passed in by the user as the id, " + "but it was not an int");
		}
	}
}
