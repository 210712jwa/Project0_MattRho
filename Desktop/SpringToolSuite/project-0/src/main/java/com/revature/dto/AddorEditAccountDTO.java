package com.revature.dto;

import java.util.Objects;

public class AddorEditAccountDTO {

	private int balance;
	private int clientId;
	private int accountId;
	
	public AddorEditAccountDTO() {
		super();
	}

	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
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
		return Objects.hash(balance, clientId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddorEditAccountDTO other = (AddorEditAccountDTO) obj;
		return balance == other.balance && clientId == other.clientId;
	}
	
	
	
}
