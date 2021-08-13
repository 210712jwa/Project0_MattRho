package com.revature.dto;

import java.util.List;
import java.util.Objects;

import com.revature.model.Account;

//import java.util.Objects;

public class AddorEditClientDTO {

//	public AddShipDTO() {
		// TODO Auto-generated constructor stub
		private String name;
		//private int age;
		
		private int id;
		
		List<Account> accounts;
		
		public AddorEditClientDTO() {
			super();
		}
		
		//public AddorEditClientDTO(int id, String name) {
		//	super();
		//	this.id = id;
		//	this.name = name;
		//}
		
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
			AddorEditClientDTO other = (AddorEditClientDTO) obj;
			return Objects.equals(accounts, other.accounts) && id == other.id && Objects.equals(name, other.name);
		}

//		@Override
//		public String toString() {
//			return "AddorEditClientDTO [name=" + name + ", id=" + id + ", accounts=" + accounts + "]";
//		}
//		
//		
		

//		@Override
//		public int hashCode() {
//			//return Objects.hash(id, name);
//			final int prime = 31;
//			int result = 1;
//			result = prime * result + id;
//			result = prime * result + ((name == null) ? 0 : name.hashCode());
//			return result;
//		}

//		@Override
//		public boolean equals(Object obj) {
//			if (this == obj)
//				return true;
//			if (obj == null)
//				return false;
//			if (getClass() != obj.getClass())
//				return false;
//			AddorEditClientDTO other = (AddorEditClientDTO) obj;
//			
//			if (id != other.id) {
//				return false;
//			}
//			if (name == null) {
//				if (other.name != null) {
//					return false;
//				}
//			} else if (!name.equals(other.name)) {
//				return false;
//			}
//			return true;
//			//return id == other.id && Objects.equals(name, other.name);
//		}

//		public int getAge() {
//			return age;
//		}

//		public void setAge(int age) {
//			this.age = age;
//		}
}
