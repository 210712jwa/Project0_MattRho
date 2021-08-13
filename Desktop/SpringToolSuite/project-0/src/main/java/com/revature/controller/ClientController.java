package com.revature.controller;

import java.util.List;

//import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.AddorEditClientDTO;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController implements Controller{
	private static Logger logger = LoggerFactory.getLogger(ClientController.class);
	
	private ClientService clientService;
	
	public ClientController() {
		this.clientService = new ClientService();
	}
	private Handler getAllClients = (ctx) -> {
		List<Client> clients = clientService.getAllClients();
		//logger.debug(getAllClients + "");
		ctx.status(200);
		ctx.json(clients);
	};
	
	private Handler getClientById = (ctx) -> {
		String clientId = ctx.pathParam("clientid");
		
		Client client = clientService.getClientById(clientId);
		ctx.json(client);
	};
	
	private Handler addClient = (ctx) -> {
		AddorEditClientDTO clientToAdd = ctx.bodyAsClass(AddorEditClientDTO.class);
		
		Client addedClient = clientService.addClient(clientToAdd);
		ctx.json(addedClient);
	};
	
	private Handler editClient = (ctx) -> {
		AddorEditClientDTO clientToEdit = ctx.bodyAsClass(AddorEditClientDTO.class);
		
		String clientId = ctx.pathParam("clientid");
		Client editedClient = clientService.editClient(clientId, clientToEdit);
		
		ctx.json(editedClient);
	};
	
	private Handler deleteClient = (ctx) -> {
		String clientId = ctx.pathParam("clientid");
		clientService.deleteClient(clientId);
		
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.get("/clients", getAllClients);
		app.get("/clients/:clientid", getClientById);
		app.post("/clients", addClient);
		app.put("/clients/:clientid", editClient);
		app.delete("/clients/:clientid", deleteClient);
	}
	
}
