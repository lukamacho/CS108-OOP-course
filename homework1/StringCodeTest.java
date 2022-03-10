// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class StringCodeTest extends TestCase {
	//
	// blowup
	//
	@Test
	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
	}
	@Test
	public void testBlowup2() {
		// things with digits

		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));

		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));

		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));
	}
	@Test
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));

		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));
	}


	//
	// maxRun
	//
	@Test
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
	}
	@Test
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));
	}

	@Test
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
	}
	@Test
	public void testIntersect1(){
		assertTrue(StringCode.stringIntersect("aabbaa","bbacds",3));
		assertFalse(StringCode.stringIntersect("first","second",2));
		assertTrue(StringCode.stringIntersect("aaaabbbbcddddee","bbbbcddeaaa",5));
	}
	@Test
	public void testIntersect2(){
		StringBuilder bigF=new StringBuilder(),bigS=new StringBuilder() ;
		for(int i=0;i<10000;i++){
			bigF.append('a');
			bigS.append('a');
		}
		for(int i=0;i<493;i++){
			bigF.append('b');
			bigS.append('b');
		}
		StringBuilder first=new StringBuilder(),second=new StringBuilder();
		for(int i=0;i<1000;i++){
			first.append('a');
			second.append('a');
		}
		for(int i=0;i<500;i++){
			second.append('b');
		}
		for(int i=0;i<100;i++){
			first.append('b');
		}
		assertTrue(StringCode.stringIntersect(bigF.toString(),bigS.toString(),1000));
		assertFalse(StringCode.stringIntersect(bigF.toString(),bigS.toString(),1000000));
		assertTrue(StringCode.stringIntersect(first.toString(),second.toString(),101));
		assertFalse(StringCode.stringIntersect(first.toString(),second.toString(),1500));
	}
}