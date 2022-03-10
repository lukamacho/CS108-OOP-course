import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import java.util.*;

public class AppearancesTest extends TestCase {
	// utility -- converts a string to a list with one
	// elem for each char.
	private List<String> stringToList(String s) {
		List<String> list = new ArrayList<String>();
		for (int i=0; i<s.length(); i++) {
			list.add(String.valueOf(s.charAt(i)));
			// note: String.valueOf() converts lots of things to string form
		}
		return list;
	}
	@Test
	public void testSameCount1() {
		List<String> a = stringToList("abbccc");
		List<String> b = stringToList("cccbba");
		assertEquals(3, Appearances.sameCount(a, b));
	}
	@Test
	public void testSameCount2() {
		// basic List<Integer> cases
		List<Integer> a = Arrays.asList(1, 2, 3, 1, 2, 3, 5);
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 9, 9, 1)));
		assertEquals(2, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1)));
		assertEquals(1, Appearances.sameCount(a, Arrays.asList(1, 3, 3, 1, 1)));
	}
	@Test
	public void myTest(){
		List<List<Integer> > a = new ArrayList<>();
		List<List<Integer> > b = new ArrayList<>();
		List<List<Integer> > c = new ArrayList<>();
		List<Integer> third = new ArrayList<>();
		third.add(1);
		third.add(3);
		List<Integer> first = new ArrayList<>();
		first.add(1);
		first.add(2);
		first.add(3);
		List<Integer> second = new ArrayList<>();
		second.add(4);
		second.add(5);
		second.add(6);
		a.add(first);
		a.add(second);
		b.add(first);
		b.add(second);
		b.add(third);
		c.add(second);
		c.add(first);
		c.add(first);
		c.add(third);
		assertEquals(2,Appearances.sameCount(a,b));
		assertEquals(1,Appearances.sameCount(a,c));
		assertEquals(2,Appearances.sameCount(b,c));
	}
}

