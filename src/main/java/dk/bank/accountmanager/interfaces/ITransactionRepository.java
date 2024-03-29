package dk.bank.accountmanager.interfaces;

import java.util.List;

import dk.bank.accountmanager.entity.Transaction;

public interface ITransactionRepository {
	public List<Transaction> getTransaction(long accountId, int listSize);
	public Transaction saveTransaction(Transaction transaction, long accountId);
	public Transaction saveTransaction(Transaction transaction);
}
