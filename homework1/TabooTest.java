// TabooTest.java
// Taboo class tests -- nothing provided.


import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TabooTest  {
    @Test
    public void basicFollowTest(){
        List<String> curList = Arrays.asList("a","b","c","d","b","d","j");
        Taboo<String> tab = new Taboo<>(curList);

        Set<String> first = new HashSet<>(Arrays.asList("c","d"));
        assertEquals(first,tab.noFollow("b"));

        Set<String> second = new HashSet<>(Arrays.asList("b","j"));
        assertEquals(second,tab.noFollow("d"));

        Set<String> third = new HashSet<>();
        assertEquals(third, tab.noFollow("j"));

        Set<String> fourth = new HashSet<>(Arrays.asList("b"));
        assertEquals(fourth,tab.noFollow("a"));

    }

    @Test
    public void basicReduceTest(){
        List<String> curList = Arrays.asList("a","a","b",null,"c","d","e");
        Taboo<String> tab = new Taboo<>(curList);

        List<String> first = new ArrayList<>(Arrays.asList("a","a","c","d","e","b"));
        tab.reduce(first);
        List<String> answerFirst = Arrays.asList("a","c","e","b");
        assertEquals(first,answerFirst);

        List<String> second = new ArrayList<>(Arrays.asList("b","d","e","c","d"));
        tab.reduce(second);
        List<String> secondAnswer = Arrays.asList("b","d","c");
        assertEquals(second,secondAnswer);
    }
    @Test
    public void reduceEdgeTest(){
        List <Integer> curList = Arrays.asList(11,12,13,15,15,13,null,121);
        Taboo <Integer> tab = new Taboo<>(curList);

        List <Integer> first = new ArrayList<>(Arrays.asList(11,12,15,13,15,13,19));
        tab.reduce(first);
        List <Integer> firstAnswer = new ArrayList<>(Arrays.asList(11,15,19));
        assertEquals(firstAnswer,first);

        List <Integer> second = new ArrayList<>(Arrays.asList(12,15,11,12,13,null));
        tab.reduce(second);
        List <Integer> secondAnswer = new ArrayList<>(Arrays.asList(12,15,11,13,null));
        assertEquals(second,secondAnswer);
    }
}