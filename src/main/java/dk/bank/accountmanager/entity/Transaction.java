package dk.bank.accountmanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2318180986596249865L;
	
	long transactionId;
	double amount;
	LocalDateTime timestamp;
	String name;
	
	public Transaction(long transactionId, double amount, LocalDateTime timestamp, String name, String details) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.timestamp = timestamp;
		this.name = name;
	}

	public Transaction() {
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
