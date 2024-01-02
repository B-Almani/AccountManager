package dk.bank.accountmanager.interfaces;

import dk.bank.accountmanager.entity.Account;

public interface IAccountRepository {

	public long saveAccount (Account account);
	public Account getAccount(long accountId);
	public void updateAccount(Account accound); 
	
	
}
