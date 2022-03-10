// CS108 HW1 -- String static methods

import java.util.HashSet;

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str.length()==0){
			return 0;
		}
		int answer=0;
		int cur=0;
		char previous=str.charAt(0);
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)==previous){
				cur++;
			}else{
				if(answer<cur){
					answer=cur;
				}
				cur=1;
				previous=str.charAt(i);
			}
		}
		return answer;
	}


	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String answer="";
		int n=str.length();
		for(int i=0;i<n;i++){
			if(str.charAt(i)>='0'&&str.charAt(i)<='9'){
				int times= str.charAt(i)-'0';
				if(i==n-1){
					times=0;
				}
				while(times>0){
					times--;
					answer+=str.charAt((i+1));
				}
			}else{
				answer+=str.charAt(i);
			}
		}
		return answer;
	}

	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		int n=a.length();
		HashSet<String> subStrings=new HashSet<String>();

		for(int i=0;i<n-len;i++){
			String cur=a.substring(i,i+len);
			subStrings.add(cur);
		}
		for(int i=0;i<b.length()-len;i++){
			String cur=b.substring(i,i+len);
			if(subStrings.contains(cur)==true){
				return true;
			}
		}
		return false;
	}
}