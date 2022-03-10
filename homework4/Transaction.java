// Transaction.java
/*
 (provided code)
 Transaction is just a dumb struct to hold
 one transaction. Supports toString.
*/
public class Transaction {
	public int from;
	public int to;
	public int amount;

	/**
	 *
	 * @param from
	 * @param to
	 * @param amount
	 * Transaction constructor.
	 */
   	public Transaction(int from, int to, int amount) {
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	/**
	 *
	 * @return
	 * Returns the number of the account from which the money is payed.
	 */
	public int fromAcc(){
   		return from;
	}

	/**
	 *
	 * @return
	 * Retruns the number of the account where the money was added.
	 */
	public int toAcc(){
   		return to;
	}

	/**
	 * Returns the transaction amount.
	 * @return
	 */
	public int getAmount(){
   		return amount;
	}

	/**
	 * Returns all the information about the transaction.
	 * @return
	 */
	public String toString() {
   		return("from:" + from + " to:" + to + " amt:" + amount);
	}
}
