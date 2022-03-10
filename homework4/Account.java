// Account.java

/*
 Simple, thread-safe Account class encapsulates
 a balance and a transaction count.
*/
public class Account {
	private int id;
	private int balance;
	private int numTransactions;
	
	// It may work out to be handy for the account to
	// have a pointer to its Bank.
	// (a suggestion, not a requirement)
	private Bank bank;  
	/**
	 * Account constuctor.
	 */
	public Account(Bank bank, int id, int balance) {
		this.bank = bank;
		this.id = id;
		this.balance = balance;
		numTransactions = 0;
	}

	/**
	 * @param money
	 * Synchronied function to add money to the balance.
	 */
	public synchronized  void addMoney(int money){
		balance+=money;
		numTransactions++;
	}

	/**
	 * Synchronized function to pay money into another account and lose it on this one.
	 * @param money
	 */
	public synchronized  void loseMoney(int money){
		balance-=money;
		numTransactions++;
	}

	/**
	 * To string function to write all the parameters of the account.
	 * @return
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("acct:").append(id).append(" ");
		sb.append("bal:").append(balance).append(" ");
		sb.append("trans:").append(numTransactions).append(" ");
		return sb.toString();
	}
}
