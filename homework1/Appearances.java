import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		HashMap<T,Integer> aHashes = new HashMap<>();
		HashMap<T,Integer> bHashes = new HashMap<>();
		fillmap(aHashes,a);
		fillmap(bHashes,b);
		int answer=0;
		for(T curKey : aHashes.keySet()){
			if(bHashes.containsKey(curKey) && aHashes.get(curKey).equals(bHashes.get(curKey))){
				answer++;
			}
		}
		return answer;
	}
	private static <T> void fillmap(HashMap<T,Integer> curMap,Collection<T> curCollection){
		for(T curValue : curCollection){
			if(curMap.containsKey(curValue)){
				curMap.put(curValue,curMap.get(curValue)+1);
			}else{
				curMap.put(curValue,1);
			}
		}
	}
	
}

