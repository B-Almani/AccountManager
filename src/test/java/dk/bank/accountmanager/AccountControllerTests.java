package dk.bank.accountmanager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import dk.bank.accountmanager.controllers.AccountController;
import dk.bank.accountmanager.entity.Account;
import dk.bank.accountmanager.entity.Transaction;
import dk.bank.accountmanager.exceptions.InvalidAmountException;
import dk.bank.accountmanager.interfaces.IAccountService;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTests {
	
	@MockBean
	IAccountService accountService;
	
	@Autowired
    private MockMvc mvc;
	
	private static Account testAccount; //=  new Account(1L, "Test Savings", 100.0, new ArrayList<>());
	//private static List<Transaction> spyTransactions;
	private static ObjectMapper objectMapper;
	
	@BeforeAll
	public static void init() {
		testAccount =  new Account(1L, "Test Savings", 100.0);
		objectMapper = new ObjectMapper();
		//spyTransactions = spy(testAccount.getTransactions());
		
	}
	
	@Test
	void createAccount_validData_statusCreatedReturnAccount() throws Exception {
		Account newAccount = new Account();
		newAccount.setName("Test Savings");
		newAccount.setBalance(100.0);
		when(accountService.createAccount(any(Account.class))).thenReturn(testAccount);
		
		
		String newAccountJSON = objectMapper.writeValueAsString(newAccount);
		
		mvc.perform(post("/api/v1/accounts")
			     .contentType(MediaType.APPLICATION_JSON_VALUE)
		    	 .content(newAccountJSON))
			     .andExpect(status().isCreated())
			     .andExpect(jsonPath("$.accountId", is(1)));
	}
	
	@Test
	void createAccount_invalidData_statusErrorReturnErrorMessage() throws Exception {
		Account newAccount = new Account();
		newAccount.setName("&%#¤");
		newAccount.setBalance(100.0);
		//To-Do: change exception.class
		when(accountService.createAccount(any(Account.class))).thenThrow(Exception.class);
		
		String newAccountJSON = objectMapper.writeValueAsString(newAccount);
		
		mvc.perform(post("/api/v1/accounts")
			     .contentType(MediaType.APPLICATION_JSON_VALUE)
		    	 .content(newAccountJSON))
			     .andExpect(status().isInternalServerError());
	}
	
	@Test
	void depositMoney_validAmount_statusOkReturnAccount() throws Exception {
		double depositAmount = 100.00;
		double newBalance = testAccount.getBalance() + depositAmount;
		testAccount.setBalance(newBalance);
		
		//when(spyTransactions.size()).thenReturn(1);
		when(accountService.depositMoney(testAccount.getAccountId(), depositAmount)).thenReturn(testAccount);
		
		mvc.perform(put("/api/v1/accounts/deposit/"+testAccount.getAccountId())
			     .contentType(MediaType.APPLICATION_JSON_VALUE)
		    	 .content(depositAmount+""))
			     .andExpect(status().isOk())
			     .andExpect(jsonPath("$.balance", is(newBalance)));
			     //.andExpect(jsonPath("$.transactions", hasSize(1)));
	}
	
	
	@Test
	void depositMoney_invalidAmount_returnInvalidAmountError() throws Exception {
		double depositAmount = -1000.00;
		when(accountService.depositMoney(testAccount.getAccountId(), depositAmount)).thenThrow(InvalidAmountException.class);
		
		mvc.perform(put("/api/v1/accounts/deposit/"+testAccount.getAccountId())
			     .contentType(MediaType.APPLICATION_JSON_VALUE)
		    	 .content(depositAmount+""))
			     .andExpect(status().isBadRequest());
	}
	
	@Test
	void withdrawMoney_validAmount_statusOkReturnAccount() throws Exception {
		double withdrawAmount = 100.00;
		double newBalance = testAccount.getBalance() - withdrawAmount;
		testAccount.setBalance(newBalance);
		when(accountService.withdrawMoney(testAccount.getAccountId(), withdrawAmount)).thenReturn(testAccount);
		
		mvc.perform(put("/api/v1/accounts/withdraw/"+testAccount.getAccountId())
			     .contentType(MediaType.APPLICATION_JSON_VALUE)
		    	 .content(withdrawAmount+""))
			     .andExpect(status().isOk())
			     .andExpect(jsonPath("$.balance", is(newBalance)));
	}
	
	
	@Test
	void withdrawMoney_invalidAmount_returnInvalidAmountError() throws Exception {
		double withdrawAmount = 200.00;
		when(accountService.withdrawMoney(testAccount.getAccountId(), withdrawAmount)).thenThrow(InvalidAmountException.class);
		
		mvc.perform(put("/api/v1/accounts/withdraw/"+testAccount.getAccountId())
			     .contentType(MediaType.APPLICATION_JSON_VALUE)
		    	 .content(withdrawAmount+""))
			     .andExpect(status().isBadRequest());
	}
	
	@Test
	void getBalance_existingAccount_returnBalance() throws Exception {
		when(accountService.getAvailableBalance(testAccount.getAccountId())).thenReturn(testAccount.getBalance());
		
		mvc.perform(get("/api/v1/accounts/balance/"+testAccount.getAccountId()))
			     .andExpect(status().isOk())
			     .andExpect(content().string(testAccount.getBalance()+""));
	}
	

}
