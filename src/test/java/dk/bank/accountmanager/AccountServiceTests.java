package dk.bank.accountmanager;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import dk.bank.accountmanager.entity.Account;
import dk.bank.accountmanager.entity.Transaction;
import dk.bank.accountmanager.exceptions.InvalidAmountException;
import dk.bank.accountmanager.interfaces.IAccountRepository;
import dk.bank.accountmanager.interfaces.ITransactionRepository;
import dk.bank.accountmanager.services.AccountService;

@ExtendWith(SpringExtension.class)
public class AccountServiceTests {
	
	@MockBean
	private IAccountRepository accountRepository;
	
	@MockBean
	private ITransactionRepository transactionRepository;
	
	private List<Transaction> transactions = new ArrayList<>();
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        AccountService employeeService() {
            return new AccountService();
        }
    }
	
	@Autowired
	private AccountService accountService;
	
	private Account testAccount = new Account(1L, "Test Account", 200.00); 
	
	@Test
	void createAccount_validDetails_ReturnCreatedAccount() throws Exception {
		when(accountRepository.saveAccount(testAccount)).thenReturn(1L);
		when(accountRepository.getAccount(1L)).thenReturn(testAccount);
		Account accountToCreate = new Account();
		accountToCreate.setName("Test Account");
		accountToCreate.setBalance(200.0);
		when(accountRepository.saveAccount(accountToCreate)).thenReturn(1L);
		
		testAccount = accountService.createAccount(testAccount);
		assertThat(testAccount.getAccountId()).isEqualTo(1L);
	}
	
	@Test
	void depositMoney_validAmount_ReturnUpdatedAccount() throws Exception {
		when(accountRepository.saveAccount(testAccount)).thenReturn(testAccount.getAccountId());
		when(accountRepository.getAccount(testAccount.getAccountId())).thenReturn(testAccount);
		
		double deposit = 100.00;
		double newTotal = testAccount.getBalance() + deposit;
		
		testAccount = accountService.depositMoney(testAccount.getAccountId(), deposit);
		
		assertThat(testAccount.getBalance()).isEqualTo(newTotal);
	}
	
	@Test
	void depositMoney_invalidAmount_throwInvalidAmountException() {
		double deposit = -100.00;
	
		assertThrows(InvalidAmountException.class, () -> 
					accountService.depositMoney(testAccount.getAccountId(), deposit));
	}
	
	@Test
	void withdrawMoney_validAmount_ReturnUpdatedAccount() throws Exception {
		when(accountRepository.saveAccount(testAccount)).thenReturn(1L);
		when(accountRepository.getAccount(1)).thenReturn(testAccount);
		
		double deposit = 100.00;
		double newTotal = testAccount.getBalance() - deposit;
		
		testAccount = accountService.withdrawMoney(testAccount.getAccountId(), deposit);
		assertThat(testAccount.getBalance()).isEqualTo(newTotal);
	}
	
	@Test
	void withdrawMoney_invalidAmount_throwInvalidAmountException() {
		when(accountRepository.saveAccount(testAccount)).thenReturn(1L);
		when(accountRepository.getAccount(1)).thenReturn(testAccount);
		
		double deposit = -100.00;
		assertThrows(InvalidAmountException.class, () -> 
			accountService.withdrawMoney(testAccount.getAccountId(), deposit));
	}
	
	@Test
	void withdrawMoney_tooLargeAmount_throwInvalidAmountException() {
		when(accountRepository.saveAccount(testAccount)).thenReturn(1L);
		when(accountRepository.getAccount(1)).thenReturn(testAccount);
		double deposit = 99999.99;
		
		assertThrows(InvalidAmountException.class, () -> 
			accountService.withdrawMoney(testAccount.getAccountId(), deposit));
	}
	
	@Test
	void getTransactions_validAccountId_returnTransactionList() {
		when(accountRepository.getAccount(1)).thenReturn(testAccount);
		
		Transaction transaction = new Transaction();
		transactions.add(transaction);
		
		when(transactionRepository.getTransaction(1, 1)).thenReturn(transactions);
		
		assertThat(accountService.getTransactions(testAccount.getAccountId(), 1)).hasSize(1);
	}
	
	@Test
	void getTransactions_invalidAccountId_throwIllegalArgumentException()  {
		when(accountRepository.getAccount(-1)).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> 
		accountService.getTransactions(-1, 100));

	}


}
