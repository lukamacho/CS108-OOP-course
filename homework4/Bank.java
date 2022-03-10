// Bank.java

/*
 Creates a bunch of accounts and uses threads
 to post transactions to the accounts concurrently.
*/
import java.io.*;
import java.util.*;
import java.util.concurrent.*;


public class Bank {
	public static final int ACCOUNTS = 20;	 // number of accounts
	private static final int amount = 1000;  //Account money amount
	private static final int defaultNum = 1; // Default number of workers
	private final Transaction nullTrans = new Transaction(-1,0,0);//Given null transaction example
	private ArrayBlockingQueue<Transaction> transactionQueue;
	private ArrayList<Account> accounts;
	private CountDownLatch countLatch;
	private final int maxThreads = 500;
	/*
	 Reads transaction data (from/to/amt) from a file for processing.
	 (provided code)
	 */
	public void readFile(String file) {
			try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			// Use stream tokenizer to get successive words from file
			StreamTokenizer tokenizer = new StreamTokenizer(reader);
			
			while (true) {
				int read = tokenizer.nextToken();
				if (read == StreamTokenizer.TT_EOF) {
					transactionQueue.add(nullTrans);
					break;
				}
				int from = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int to = (int)tokenizer.nval;
				
				tokenizer.nextToken();
				int amount = (int)tokenizer.nval;
				
				// Use the from/to/amount
				Transaction cur =  new Transaction(from,to,amount);
				transactionQueue.put(cur);
			}
		}
		catch (Exception e) { e.printStackTrace(); System.exit(1);
		}
	}

	/*
	 Processes one file of transaction data
	 -fork off workers
	 -read file into the buffer
	 -wait for the workers to finish
	*/
	public void processFile(Bank b,String file, int numWorkers) throws InterruptedException {
			transactionQueue = new ArrayBlockingQueue<>(maxThreads);
			accounts = new ArrayList<>();
			countLatch = new CountDownLatch(numWorkers);

			for(int i=0;i<ACCOUNTS;i++){
				Account cur = new Account(b,i,amount);
				accounts.add(cur);
			}
			for(int i=0;i<numWorkers;i++){
				new worker().start();
			}
			readFile(file);
			countLatch.await();
			printResults();
	}

	/**
	 * This function prints the account result after the all transactions.
	 */
 	private void printResults(){
		for(int i=0;i<ACCOUNTS;i++){
			System.out.println(accounts.get(i).toString());
		}
	}
	/*
	* Inner worker thread class which enables workers to start working.
	* */
	class worker extends Thread{
		@Override
		public void run(){
			while(true){
				try {
					Transaction tr = transactionQueue.take();
					if(tr == nullTrans){
						countLatch.countDown();
						transactionQueue.add(nullTrans);
						break;
					}
					makeTransaction(tr);
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
		}

		/**
		 * This method makes the transaction.
		 * @param tr
		 */
		private void makeTransaction(Transaction tr) {
			int from = tr.fromAcc();
			int to = tr.toAcc();
			int amount = tr.getAmount();
			accounts.get(from).loseMoney(amount);
			accounts.get(to).addMoney(amount);
		}
	}
	
	/*
	 Looks at commandline args and calls Bank processing.
	*/
	public static void main(String[] args) throws InterruptedException {
		// deal with command-lines args
		if (args.length == 0) {
			System.out.println("Args: transaction-file [num-workers [limit]]");
		}else {
			Bank b = new Bank();
			String file = args[0];

			int numWorkers = 1;
			if (args.length >= 2) {
				numWorkers = Integer.parseInt(args[1]);
				b.processFile(b, args[0], numWorkers);
			} else if (args.length == 1) {
				b.processFile(b, file, defaultNum);
			}
		}
	}
}

