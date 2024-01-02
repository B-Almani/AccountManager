package dk.bank.accountmanager.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3984985981354593676L;
	
	long accountId;
	String name;
	double balance;
	//List<Transaction> transactions;
	
	public Account() {
		
	}

	public Account(long accountId, String name, double balance) { //, List<Transaction> transactions) {
		super();
		this.accountId = accountId;
		this.name = name;
		this.balance = balance;
	//	this.transactions = transactions;
	}

	public long getAccountId() {
		return accountId;
	}
	
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	/*
	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	
	*/

}