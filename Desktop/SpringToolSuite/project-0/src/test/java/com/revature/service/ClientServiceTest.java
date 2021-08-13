//package com.revature.service;
//
//import com.revature.dao.ClientDAO;
//import com.revature.exceptions.DatabaseException;
////import com.sun.tools.javac.util.List;
//
//import static org.mockito.Mockito.mock;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import org.eclipse.jetty.io.ssl.ALPNProcessor.Client;
//
//public class ClientServiceTest {
//
//	private ClientService clientService;
//	private ClientDAO clientDao;
//	
//	@Before
//	public void setUp() throws Exception {
//		this.clientDao = mock(ClientDAO.class);
//		this.clientService = new ClientService()
//	}
//	
//	@Test
//	public void test_getAllClients_positive() throws DatabaseException, SQLException {
//		List<Client> mockReturnValues = new ArrayList<>();
//		mockReturnValues.add(new Client(1, "hello"));
//		mockReturnValues.add(new Client(2, "world"));
//		when(clientDao.getAllClients()).thenReturn(mockReturnValues);
//		
//		
//		
//	}
//}


package com.revature.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;
// You may need to type this import manually to make use of 
// the argument matchers for Mockito, such as eq() or any()
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.revature.dao.ClientDAO;
import com.revature.dto.AddorEditClientDTO;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.DatabaseException;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.dao.AccountDao;
//import org.junit.Before;
//import org.junit.Test;

//import com.revature.dao.AccountDAO;
//import com.revature.dao.ClientDAO;
//import com.revature.dto.AddOrEditClientDTO;
//import com.revature.exception.BadParameterException;
//import com.revature.exception.DatabaseException;
//import com.revature.exception.ClientNotFoundException;
//import com.revature.model.Account;
//import com.revature.model.Client;

public class ClientServiceTest {
	
	private ClientService clientService;
	private ClientDAO clientDao;
	private AccountDao accountDao;
	
	@Before
	public void setUp() throws Exception {
		this.clientDao = mock(ClientDAO.class); 
		this.accountDao = mock(AccountDao.class);
		
		this.clientService = new ClientService(clientDao, accountDao);
	}

	
	@Test
	public void test_getAllClients_positive() throws DatabaseException, SQLException {
		// Because we're not using a real ClientDAO object and instead a mocked ClientDAO, 
		// we need to actually specify what we want the mocked ClientDAO to return whenever we invoke the clientDao.getAllClients() method
		List<Client> mockReturnValues = new ArrayList<>();
		mockReturnValues.add(new Client(1, "Black Pearlssss"));
		mockReturnValues.add(new Client(2, "Royal Fortune"));
		when(clientDao.getAllClients()).thenReturn(mockReturnValues);
		
		List<Client> actual = clientService.getAllClients();
		
		List<Client> expected = new ArrayList<>();
		expected.add(new Client(1, "Black Pearlssss"));
		expected.add(new Client(2, "Royal Fortune"));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void Test_all_clients_no_client() throws DatabaseException, ClientNotFoundException {
		clientService.getAllClients();
		fail();
	}
	
	@Test(expected = DatabaseException.class)
	public void Test_all_clients_databaseException()
		throws SQLException, DatabaseException, ClientNotFoundException {
		when (clientDao.getAllClients()).thenThrow(SQLException.class);
		clientService.getAllClients();
	}
	
	@Test
	public void Test_get_client_by_id_positive()
		throws SQLException, DatabaseException, BadParameterException, ClientNotFoundException {
		when (clientDao.getClientById(eq(10))).thenReturn(new Client(10, "Hello"));
		Client actual = clientService.getClientById("10");
		Client expected = new Client(10, "Hello");
		assertEquals(actual, expected);
	}
	
	@Test
	public void Test_get_client_by_id_null() throws DatabaseException, ClientNotFoundException {
		try {
			clientService.getClientById("");
			fail();
		} catch (BadParameterException e) {
			assertEquals(" was passed in by the user as the id, but it was not an int", e.getMessage());
		}
	}

	@Test
	public void Test_get_client_by_id_negative() throws DatabaseException, ClientNotFoundException {
		try {
			clientService.getClientById("-1");
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client id cannot be negative", e.getMessage());
		}
	}
	
	@Test
	public void Test_get_client_by_id_client_does_not_exist()
		throws DatabaseException, SQLException, BadParameterException {
		try {
			when (clientDao.getClientById(eq(10))).thenReturn(null);
			clientService.getClientById("10");
			fail();
		} catch (ClientNotFoundException e) {
			assertEquals("Client not found with id: 10", e.getMessage());
		}
	}
	
	@Test(expected = DatabaseException.class)
	public void test_get_client_by_id_id_negative()
		throws DatabaseException, BadParameterException, SQLException, ClientNotFoundException{
		when (clientDao.getClientById(isA(Integer.class))).thenThrow(SQLException.class);
		clientService.getClientById("10");
	}
	
	@Test
	public void Test_add_client_positive() throws DatabaseException, BadParameterException, SQLException {
		AddorEditClientDTO dto = new AddorEditClientDTO();
		dto.setName("Bob Larry");
		when (clientDao.addClient(dto)).thenReturn(new Client(10, "Bob Larry"));
		Client actual = clientService.addClient(dto);
		Client expected = clientService.addClient(dto);
		assertEquals(actual, expected);
	}
	@Test
	public void Test_add_client_name_null() throws DatabaseException {
		try {
			AddorEditClientDTO dto = new AddorEditClientDTO();
			dto.setName("");
			clientService.addClient(dto);
			fail();
			
		} catch (BadParameterException e) {
			assertEquals("input for name contains non-alphabetic characters or is null", e.getMessage());
		}
	}
	@Test
	public void Test_add_client_name_number() throws DatabaseException {
		try {
			AddorEditClientDTO dto = new AddorEditClientDTO();
			dto.setName("14asf");
			clientService.addClient(dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("input for name contains non-alphabetic characters or is null", e.getMessage());
		}
	}
	
	@Test(expected = DatabaseException.class)
	public void Test_add_client_database_exception() throws SQLException, DatabaseException, BadParameterException {
		when(clientDao.addClient(any())).thenThrow(SQLException.class);
		AddorEditClientDTO dto = new AddorEditClientDTO();
		dto.setName("some string");
		clientService.addClient(dto);
	}
	
	@Test
	public void Test_edit_client_positive() throws SQLException, DatabaseException, BadParameterException, ClientNotFoundException {
		AddorEditClientDTO dto = new AddorEditClientDTO();
		dto.setName("some string");
		when(clientDao.editClient(10, dto)).thenReturn(new Client(10, "some string"));
		Client expected = clientService.editClient("10", dto);
	}
	@Test
	public void Test_edit_client_name_null() throws DatabaseException, ClientNotFoundException {
		try {
			AddorEditClientDTO dto = new AddorEditClientDTO();
			dto.setName("");
			clientService.editClient("10", dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("input for name contains non-alphabetic characters or is null", e.getMessage());
		}
	}
	
	@Test
	public void Test_edit_client_id_not_int() throws DatabaseException, ClientNotFoundException {
		try {
			AddorEditClientDTO dto = new AddorEditClientDTO();
			dto.setName("some string");
			clientService.editClient("o", dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("o" + "was passed in, but it is not an integer", e.getMessage());
		}
	}
	@Test
	public void Test_edit_client_id_null() throws DatabaseException, ClientNotFoundException {
		try {
			AddorEditClientDTO dto = new AddorEditClientDTO();
			dto.setName("some string");
			clientService.editClient("", dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("" + "was passed in by the user as the id, but it was not an int", e.getMessage());
		}
	}
	@Test
	public void Test_edit_client_id_negative() throws DatabaseException, ClientNotFoundException {
		try {
			AddorEditClientDTO dto = new AddorEditClientDTO();
			dto.setName("some string");
			clientService.editClient("-1", dto);
			fail();
		} catch (BadParameterException e) {
			assertEquals("Client id cannot be negative", e.getMessage());
		}
	}
	@Test
	public void Test_edit_client_name_non_alpha() throws DatabaseException, ClientNotFoundException {
		try {
			AddorEditClientDTO dto = new AddorEditClientDTO();
			dto.setName("12awe");
			clientService.editClient("10", dto);
			fail();
			
		} catch (BadParameterException e) {
			assertEquals("input for name contains non-alphabetic characters or is null", e.getMessage());
			
		}
	}
	@Test(expected = DatabaseException.class)
	public void Test_edit_client_database_exception() throws DatabaseException, BadParameterException, SQLException, ClientNotFoundException {
		when(clientDao.editClient(anyInt(), any())).thenThrow(SQLException.class);
		AddorEditClientDTO dto = new AddorEditClientDTO();
		dto.setName("some string");
		clientService.editClient("10", dto);
	}
	@Test
	public void Test_delete_client_positive() throws SQLException, DatabaseException, BadParameterException {
		when (clientDao.getClientById(eq(10))).thenReturn(new Client(10, "Larry"));
		List<Account> mockAccounts = new ArrayList<>();
		mockAccounts.add(new Account(10, 1233, 10));
		when(accountDao.getAllAccountsFromClient(eq(10))).thenReturn(mockAccounts);
		Mockito.doNothing().when(accountDao).deleteAccount(eq(10),10);
		Mockito.doNothing().when(clientDao).deleteClient(10);
	}
	
	@Test
	public void Test_delete_client_id_string() throws DatabaseException, ClientNotFoundException {
		try {
			clientService.deleteClient("dd");
			fail();
			
		} catch (BadParameterException e) {
			assertEquals("dd" + "was passed in, but it is not an integer", e.getMessage());
		}
	}
	@Test(expected = DatabaseException.class)
	public void Test_delete_client_database_exception() throws SQLException, DatabaseException, BadParameterException, ClientNotFoundException {
		List<Account> mockAccounts = new ArrayList<>();
		mockAccounts.add(new Account(20, 1000, 1));
		when(accountDao.getAllAccountsFromClient(eq(10))).thenReturn(mockAccounts);
		Mockito.doThrow(new SQLException()).when(clientDao).deleteClient(anyInt());
		clientService.deleteClient("1");
		Mockito.verify(clientDao).deleteClient(1);
	}
	//@Test
//	public void test_client_with_account_positive() throws SQLException, DatabaseException, BadParameterException, AccountNotFoundException {
		
	//}
}
	
//		List<Account> blackPearlAccounts = new ArrayList<>();
//		blackPearlAccounts.add(new Account(1, 28000, 1));
//		blackPearlAccounts.add(new Account(2, 60000, 1));
//		when(accountDao.getAllAccountsFromClient(eq(1))).thenReturn(blackPearlAccounts);
//		
//		List<Account> royalFortuneAccounts = new ArrayList<>();
//		royalFortuneAccounts.add(new Account(10, "test1", 100, 2));
//		royalFortuneAccounts.add(new Account(53, "test2", 101, 2));
//		when(accountDao.getAllAccountsFromClient(eq(2))).thenReturn(royalFortuneAccounts);
//		
//		// actual = the real data being returned by the getAllClients method from clientService
//		List<Client> actual = clientService.getAllClients();
//		
//		System.out.println(actual);
//		
//		// expected = what we expect for the clients List to contain
//		List<Client> expected = new ArrayList<>();
//		Client s1 = new Client(1, "Black Pearlssss", 40);
//		s1.setAccounts(blackPearlAccounts);
//		Client s2 = new Client(2, "Royal Fortune", 10);
//		s2.setAccounts(royalFortuneAccounts);
//		
//		expected.add(s1);
//		expected.add(s2);
//		
//		// So, we do an assertEquals, to have these two compared to each other
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void test_getAllClients_negative() throws SQLException {
//		when(clientDao.getAllClients()).thenThrow(SQLException.class);
//		// Simulate a situation where clientDao.getAllClients() throws a SQLException
//		
//		try {
//			clientService.getAllClients();
//			
//			fail(); // We only reach the fail() assertion IF DatabaseException is not thrown and caught
//			// So this allows us to test for the DatabaseException occurring.
//			// Additionally, in the catch block, we're also checking the exception message itself
//		} catch (DatabaseException e) {
//			String exceptionMessage = e.getMessage();
//			assertEquals("Something went wrong with our DAO operations", exceptionMessage);
//		}
//		
//	}
//	
////	@Test(expected = BadParameterException.class)
////	public void test_getClientById_idStringIsNotAnInt() throws DatabaseException, ClientNotFoundException, BadParameterException {
////		clientService.getClientById("asdfsdf");
////	}
//	
//	@Test
//	public void test_getClientById_idStringIsNotAnInt() throws DatabaseException, ClientNotFoundException {
//		try {
//			clientService.getClientById("asdfasdf");
//			
//			fail();
//		} catch (BadParameterException e) {
//			assertEquals("asdfasdf was passed in by the user as the id, but it is not an int", e.getMessage());
//		}
//	}
//	
//	@Test
//	public void test_getClientById_existingId() throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {
//		when(clientDao.getClientById(eq(1))).thenReturn(new Client(1, "Black Pearl", 1));
//		
//		Client actual = clientService.getClientById("1");
//		
//		Client expected = new Client(1, "Black Pearl", 1);
//		expected.setAccounts(new ArrayList<>());
//		
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void test_getClientById_clientDoesNotExist() throws DatabaseException, ClientNotFoundException, BadParameterException {
//		try {
//			clientService.getClientById("10"); // Because I'm not providing a mock response for getClientId when the int parameter
//			// passed into that method is 10, it will by default return a null value
//			
//			fail();
//		} catch (ClientNotFoundException e) {
//			assertEquals("Client with id 10 was not found", e.getMessage());
//		}
//	}
//	
//	@Test
//	public void test_getClientById_sqlExceptionEncountered() throws SQLException, ClientNotFoundException, BadParameterException {
//		try {
//			when(clientDao.getClientById(anyInt())).thenThrow(SQLException.class);
//			
//			clientService.getClientById("1");
//			
//			fail();
//		} catch (DatabaseException e) {
//			assertEquals("Something went wrong with our DAO operations", e.getMessage());
//		}
//	}
//	
//	/*
//	 * addClient
//	 */
//	@Test
//	public void test_addClient_positivePath() throws SQLException, DatabaseException, BadParameterException {
//		
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("Black Pearl");
//		dto.setAge(10);
//		
//		when(clientDao.addClient(eq(dto))).thenReturn(new Client(1, "Black Pearl", 10));
//		
//		Client actual = clientService.addClient(dto);
//		
//		Client expected = new Client(1, "Black Pearl", 10);
//		expected.setAccounts(new ArrayList<>());
//		
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void test_addClient_blankName() throws DatabaseException {
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("");
//		dto.setAge(10);
//		
//		try {
//			clientService.addClient(dto);
//			
//			fail();
//		} catch (BadParameterException e) {
//			assertEquals("Client name cannot be blank", e.getMessage());
//		}
//		
//	}
//	
//	@Test
//	public void test_addClient_blankNameWithSpaces() throws DatabaseException {
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("                        ");
//		dto.setAge(10);
//		
//		try {
//			clientService.addClient(dto);
//			
//			fail();
//		} catch (BadParameterException e) {
//			assertEquals("Client name cannot be blank", e.getMessage());
//		}
//	}
//
//	@Test
//	public void test_addClient_negativeAge() throws DatabaseException {
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("Bach's Client");
//		dto.setAge(-1);
//		
//		try {
//			clientService.addClient(dto);
//			
//			fail();
//		} catch (BadParameterException e) {
//			assertEquals("Client age cannot be less than 0", e.getMessage());
//		}
//		
//	}
//	
//	@Test
//	public void test_addClient_negativeAgeAndBlankName() throws DatabaseException {
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("");
//		dto.setAge(-10);
//		
//		try {
//			clientService.addClient(dto);
//			
//			fail();
//		} catch (BadParameterException e) {
//			assertEquals("Client name cannot be blank and age cannot be less than 0", e.getMessage());
//		}
//		
//	}
//	
//	@Test(expected = DatabaseException.class)
//	public void test_addClient_SQLExceptionEncountered() throws SQLException, DatabaseException, BadParameterException {
//		when(clientDao.addClient(any())).thenThrow(SQLException.class);
//		
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("Black Pearl");
//		dto.setAge(10);
//		clientService.addClient(dto);
//	}
//	
//	/*
//	 * editClient
//	 */
//	
//	@Test
//	public void test_editClient_positivePath() throws DatabaseException, ClientNotFoundException, BadParameterException, SQLException {
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("Black Pearl");
//		dto.setAge(100);
//		
//		Client clientWithId10 = new Client(10, "Jolly Roger", 5);
//		when(clientDao.getClientById(eq(10))).thenReturn(clientWithId10);
//		
//		when(clientDao.editClient(eq(10), eq(dto))).thenReturn(new Client(10, "Black Pearl", 100));
//		
//		Client actual = clientService.editClient("10", dto);
//		
//		Client expected = new Client(10, "Black Pearl", 100);
//		expected.setAccounts(new ArrayList<>());
//		
//		assertEquals(expected, actual);
//	}
//	
//	@Test
//	public void test_editClient_clientDoesNotExist() throws DatabaseException, BadParameterException {
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("Black Pearl");
//		dto.setAge(100);
//		
//		try {
//			clientService.editClient("1000", dto);
//			
//			fail();
//		} catch (ClientNotFoundException e) {
//			assertEquals("Client with id 1000 was not found", e.getMessage());
//		}
//		
//	}
//	
//	@Test(expected = BadParameterException.class)
//	public void test_editClient_invalidId() throws DatabaseException, ClientNotFoundException, BadParameterException {
//		AddOrEditClientDTO dto = new AddOrEditClientDTO();
//		dto.setName("Black Pearl");
//		dto.setAge(100);
//		
//		clientService.editClient("abc", dto);
//	}
//	
//	@Test(expected = DatabaseException.class)
//	public void test_editClient_SQLExceptionEncountered() throws SQLException, DatabaseException, ClientNotFoundException, BadParameterException {
//		AddorEditClientDTO dto = new AddorEditClientDTO();
//		dto.setName("Black Pearl");
//		dto.setAge(100);
//		
//		when(clientDao.getClientById(eq(10))).thenReturn(new Client(10, "Jolly Roger", 5));
//		when(clientDao.editClient(eq(10), eq(dto))).thenThrow(SQLException.class);
//		
//		clientService.editClient("10", dto);
//	}
//	
//	/*
//	 * Exercise: Create tests for DeleteClient and have full test coverage
//	 */
//}
