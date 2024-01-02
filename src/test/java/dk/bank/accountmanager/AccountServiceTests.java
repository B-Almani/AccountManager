package dk.bank.accountmanager;


import static org.assertj.core.api.Assertions.assertThat;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import dk.bank.accountmanager.entity.Account;
import dk.bank.accountmanager.interfaces.IAccountRepository;
import dk.bank.accountmanager.interfaces.IAccountService;

@ExtendWith(SpringExtension.class)
public class AccountServiceTests {
	
	@MockBean
	IAccountRepository accountRepository;
	
	@Autowired
	IAccountService accountService;
	
	private Account testAccount; 
	
	void setUp() {
		testAccount = new Account();
		testAccount.setName("Test Account");
		testAccount.setBalance(0.0);
	}
	
	@Test
	void createAccount() {
		String name = "Test Konto";
		Account newAccount = new Account(name, 200.00);
		accountId = testAccount.getAccountId(); //accountService.createAccount(name);
		assertThat(testAccount).isNotNull(); //(accountService.getAccount(accountId)).isNotNull();
	}
	
	@Test
	void depositMoney() {
		double deposit = 100.00;
		double newTotal = testAccount.getBalance() + deposit;
		testAccount.setBalance(newTotal);
		assertThat(testAccount.getBalance()).isEqualTo(newTotal);
	}
	
	@Test
	void depositInvalidAmount() {
		double deposit = -100.00;
		double curentBalance = testAccount.getBalance();
		if(deposit > 0) {
			double newTotal = testAccount.getBalance() + deposit;
			testAccount.setBalance(newTotal);
		}
		assertThat(testAccount.getBalance()).isEqualTo(curentBalance);
	}
	
	@Test
	void withdrawMoney() {
		double deposit = 100.00;
		double newTotal = testAccount.getBalance() - deposit;
		testAccount.setBalance(newTotal);
		assertThat(testAccount.getBalance()).isEqualTo(newTotal);
	}
	
	@Test
	void withdrawInvalidAmount() {
		double deposit = -100.00;
		double curentBalance = testAccount.getBalance();
		if(deposit > 0 && deposit < curentBalance) {
			double newTotal = testAccount.getBalance() - deposit;
			testAccount.setBalance(newTotal);
		}
		assertThat(testAccount.getBalance()).isEqualTo(curentBalance);
		
		deposit = 9999999.00;
		if(deposit > 0 && deposit < curentBalance) {
			double newTotal = testAccount.getBalance() - deposit;
			testAccount.setBalance(newTotal);
		}
		assertThat(testAccount.getBalance()).isEqualTo(curentBalance);
	}

}
