package com.revature.controller;

import java.util.List;

import com.revature.service.AccountService;
import com.revature.dto.AddorEditAccountDTO;
import com.revature.model.Account;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller{

	private AccountService accountService;
	
	public AccountController() {
		this.accountService = new AccountService();
	}
	
	//private Handler addAccountToClient = (ctx) -> {
	//	Add
	//}
	
	private Handler getAccountFromClient = (ctx) -> {
		String clientId = ctx.pathParam("clientid");
		
		String lessThan = ctx.queryParam("amountLessThan");
		String greaterThan = ctx.queryParam("amountGreaterThan");
		
		List<Account> accountsFromClient = accountService.getAllAccountsFromClient(clientId, lessThan, greaterThan);
		ctx.json(accountsFromClient);
	};
	
	private Handler getAccountById = (ctx) -> {
		String clientId = ctx.pathParam("clientid");
		String accountId = ctx.pathParam("accountid");
		
		Account accountById = accountService.getAccountById(clientId, accountId);
		ctx.json(accountById);
	};
	
	private Handler addAccountToClient = (ctx) -> {
		AddorEditAccountDTO accountToAdd = ctx.bodyAsClass(AddorEditAccountDTO.class);
		
		Account addedAccount = accountService.addAccount(accountToAdd);
		ctx.json(addedAccount);
	};
	
	private Handler editAccount = (ctx) -> {
		AddorEditAccountDTO accountToEdit = ctx.bodyAsClass(AddorEditAccountDTO.class);
		
		String clientId = ctx.pathParam("clientid");
		
		String accountId = ctx.pathParam("accountid");
		
		Account editedAccount = accountService.editAccount(clientId, accountId, accountToEdit);
		ctx.json(editedAccount);
		
	};
	
	private Handler deleteAccount = (ctx) -> {
		String clientId = ctx.pathParam("clientid");
		
		String accountId = ctx.pathParam("accountid");
		
		accountService.deleteAccount(clientId, accountId);
		
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		// TODO Auto-generated method stub
		app.get("/clients/:clientid/accounts/:accountid", getAccountById);
		app.get("/clients/:clientid/accounts", getAccountFromClient);
		app.post("/clients/:clientid/accounts", addAccountToClient);
		app.put("/clients/:clientid/accounts/:accountid", editAccount);
		app.delete("/clients/:clientid/accounts/:accountid", deleteAccount);
	}

}
