package com.revature.model;

//import java.util.List;
import java.util.Objects;

public class Account {

	private int id;
	private int balance;
	private int clientId;
	
	//List<Client> clients;
	
	public Account() {
		super();
	}
	
	//public List<Client> getClients() {
	//	return clients;
	//}

	//public void setClients(List<Client> clients) {
	//	this.clients = clients;
	//}
	public Account(int id, int balance, int clientId) {
		this.id = id;
		this.balance = balance;
		this.clientId = clientId;
	}

	public int getAccountId() {
		return id;
	}
	
	public void setAccountId(int id) {
		this.id = id;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, clientId, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return balance == other.balance && clientId == other.clientId && id == other.id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + ", clientId=" + clientId + "]";
	}
	
	


	
}
