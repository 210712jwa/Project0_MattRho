package com.revature.model;

//import java.util.List;
import java.util.Objects;
//import java.util.ArrayList;
import java.util.List;

public class Client {

	private int id;
	private String name;
	//private int age;
	List<Account> accounts;
	
	public Client() {
		super();
	}
	public Client(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Client(int id, String name, List<Account> accounts) {
		super();
		this.id = id;
		this.name = name;
		this.accounts = accounts;
		
	//this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
//	public List<Account> getAccounts() {
//		return accounts;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(accounts, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(accounts, other.accounts) && id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", accounts=" + accounts + "]";
	}
	
	

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Client other = (Client) obj;
//		return id == other.id && Objects.equals(name, other.name);
//	}

//	@Override
//	public String toString() {
//		return "Client [id=" + id + ", name=" + name +"]";
//	}
	

//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}

	
	
}
