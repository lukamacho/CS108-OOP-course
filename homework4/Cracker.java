// Cracker.java
/*
 Generates SHA hashes of short strings in parallel.
*/
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.security.*;
import java.util.concurrent.*;

public class Cracker {
	// Array of chars used to produce strings
	public static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz0123456789.,-!".toCharArray();

	private CountDownLatch countLatch;
	private ArrayList<String> similars;
	private String crackingHash;
	private int length;

	/**
	 *
	 * @param pass
	 * @throws NoSuchAlgorithmException
	 * Writes the hash value of the string pass.
	 */
	private void generationMode(String pass) throws NoSuchAlgorithmException{
		String value = stringHash(pass);
		System.out.println(value);
	}

	/**
	 *
	 * @param value
	 * @return
	 * @throws NoSuchAlgorithmException
	 * This function generates the hash value of the given string.
	 */
	private String stringHash(String value) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA");
		md.update(value.getBytes());
		return hexToString(md.digest());
	}

	/**
	 *
	 * @param passwordHash
	 * @param length
	 * @param num
	 * @throws InterruptedException
	 * Cracker mode generates all the strings which have the same hash as passwordHash and have
	 * the precise length.
	 */
	private void crackerMode(String passwordHash,int length, int num) throws InterruptedException {
		similars = new ArrayList<>();
		countLatch = new CountDownLatch(num);
		crackingHash = passwordHash;
		this.length = length;
		startProcessing(num);
		countLatch.await();
		writeSimilars();
	}

	/**
	 *
	 * @param num
	 * This function start the num amount workers.
	 */
	private void startProcessing(int num){
		int eachLength = CHARS.length / num;
		for(int i = 0 ;i < num; i++){
			int start = i * eachLength, end ;
			if(i != num -1){
				end = (i + 1)* eachLength;
			}else{
				end = CHARS.length;
			}
			Worker curWorker = new Worker(start, end);
			curWorker.start();
		}
	}

	/**
	 * Function writes all the similar hash valued strings.
	 */
	private void writeSimilars(){
		int n = similars.size();
		for(int i = 0; i < n; i++){
			System.out.println(similars.get(i));
		}
	}
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}


	/**
	 * Worker thread class which saves the information of each worker which was already
	 * started and haven't finished yet.
	 */
	private class Worker extends Thread{
		private final int start, end;

		/**
		 *
		 * @param start
		 * @param end
		 * Worker thread constructor.
		 */
		public Worker(int start, int end){
			this.start = start;
			this.end = end;
		}
		@Override
		public void run(){
			for(int i = start; i < end; i++){
				try {
					recurseHashes(Character.toString(CHARS[i]));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
			countLatch.countDown();
		}

		/**
		 *
		 * @param currentPass
		 * @throws NoSuchAlgorithmException
		 * Recursive function to generate all the string which have the same hash as current
		 * password and have the already precised length.
		 */
		private void recurseHashes(String currentPass) throws NoSuchAlgorithmException {
			if(currentPass.length() > length){
				return;
			}
			if(currentPass.length() == length){
				if(stringHash(currentPass).equals(crackingHash)){
					similars.add(currentPass);
				}
			}
			for( int i = 0; i < CHARS.length ; i++){
				recurseHashes(currentPass + CHARS[i]);
			}
		}
	}

	/**
	 *
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws InterruptedException
	 * Main function to run the cracker function it must be call from the terminal.
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
		Cracker cr = new Cracker();

		if (args.length == 0) {
			System.out.println("Args: target length [workers]");
		}else if(args.length==1){
			String pass = args[0];
			cr.generationMode(pass);
		}else {
			// args: targ len [num]
			String password = args[0];
			int len = Integer.parseInt(args[1]);
			int num = 1;
			if (args.length > 2) {
				num = Integer.parseInt(args[2]);
			}
			cr.crackerMode(password, len, num);

			// a! 34800e15707fae815d7c90d49de44aca97e2d759
			// xyz 66b27417d37e024c46526c2f6d358a754fc552f3
		}

	}

}
