package com.revature.app;

//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;

//import org.mariadb.jdbc.internal.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
//import com.revature.controller.TestController;
//import com.revature.dto.AddorEditClientDTO;
//import com.revature.exceptions.DatabaseException;
//import com.revature.exceptions.BadParameterException;
//import com.revature.exceptions.ClientNotFoundException;
//import com.revature.model.Account;
//import com.revature.model.Client;
//import com.revature.service.ClientService;

import io.javalin.Javalin;
//import com.revature.util.ConnectionUtility;

public class Application {

//	public static void main(String[] args) throws BadParameterException {
//		// TODO Auto-generated method stub
		//System.out.println(System.getenv("db_url"));
		//System.out.println(System.getenv("db_user"));
		//System.out.println(System.getenv("db_password"));

		//try {
//			Connection connection = ConnectionUtility.getConnection();
//		System.out.println(connection);
//	/} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	ClientService service = new ClientService();
//	try {
//		List<Client> clients = service.getAllClients();
//		System.out.println(clients);
//		Client client = service.getClientById("3");
//		System.out.println(client);
//		
//		Client client1 = service.getClientById("4");
//		System.out.println(client1);
//	} catch (DatabaseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		System.out.println("Something went wrong when interacting with the database.");
//	} catch (ClientNotFoundException c) {
//		System.out.println(c.getMessage());
//	}
//	
//	try {
//		List<Account> account = new ArrayList<>();
//		Client clientToInsert = new Client(5, "Hi", account);
//		AddorEditClientDTO clientDto = new AddorEditClientDTO();//8, "Hello");
//		clientDto.setId(8);
//		clientDto.setName("Hello");
//		
//		Client createdClient = service.addClient(clientDto);
//		System.out.println("Client added: " + createdClient);
//		
//		AddorEditClientDTO clientDto1 = new AddorEditClientDTO();
//		
//		clientDto1.setId(10);
//		clientDto1.setName("Kitty");
//		
//		Client createdClient1 = service.addClient(clientDto1);
//		System.out.println("Client added: " + createdClient1);
//	}
//	catch (DatabaseException e) {
//		System.out.println("Something went wrong");
//		e.printStackTrace();
//	}
//	
//	try {
//	
//		AddorEditClientDTO clientDto = new AddorEditClientDTO();
//		clientDto.setId(1000);
//		clientDto.setName("test");
//	//	clientDto.setAccounts()
//		Client client = service.editClient("1", clientDto);
//		System.out.println(client);
//	} catch (ClientNotFoundException e) {
//		System.out.println(e.getMessage());
//	} catch (DatabaseException e) {
//		System.out.println("Something went wrong when interacting with the database");
//	
//	}
	
//	try {
//		Client clientToEdit = new Client()
//	}
//	try {
//		//Ship shipToInsert = new Ship(0, "Jolly Roger", 23);
//		AddorEditShipDTO shipDto = new AddorEditShipDTO();
//		shipDto.setName("Fancy");
//		shipDto.setAge(6);
		
//		Ship createdShip = service.addShip(shipDto);
//		System.out.println("Ship added: " + createdShip);
//	} catch (DatabaseException e) {
//		System.out.println("WA");
//	}
//	}
//		
		private static Javalin app;
		private static Logger logger = LoggerFactory.getLogger(Application.class);
		
		public static void main(String[] args) {
			app = Javalin.create();
			mapControllers(new ClientController(), new AccountController(), new ExceptionController());
			app.before((ctx) -> {
				logger.info(ctx.method() + " request received to the " + ctx.path() + " endpoint");
			});
			app.start(7001);
			
		}
		
		public static void mapControllers(Controller... controllers) {
			for (Controller c : controllers) {
				c.mapEndpoints(Application.app);
			}
		}
		

}
//}	

