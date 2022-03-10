
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	HashMap<T,HashSet<T>> ruleBase;
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		ruleBase = new HashMap<>();
		T previous = null;
		for(T cur : rules){
			if(previous != null && cur != null){
				if(!ruleBase.containsKey((previous))){
					ruleBase.put(previous,new HashSet<T>());
				}
				ruleBase.get(previous).add(cur);
			}
			previous = cur;
		}
	}

	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(ruleBase.containsKey(elem)){
			return ruleBase.get(elem);
		}
		return Collections.emptySet();
	}

	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		T previous = null;
		Iterator itr = list.iterator();
		while(itr.hasNext()){
			T curValue = (T)itr.next();
			if(previous != null && curValue != null && ruleBase.containsKey(previous)
					&& ruleBase.get(previous).contains(curValue)){
				itr.remove();
			}else{
				previous=curValue;
			}
		}
	}
}