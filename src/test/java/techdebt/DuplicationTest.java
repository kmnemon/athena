package techdebt;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DuplicationTest {
    @Test
    void TestDuplicationEquality(){
        Duplication d1 = new Duplication("a", "b");
        Duplication d2 = new Duplication("a", "b");
        Duplication d3= new Duplication("a", "c");
        Duplication d4 = new Duplication("b", "a");

        assertTrue(d1.equals(d2));
        assertFalse(d1.equals(d3));
        assertFalse(d1.equals(d4));
    }

    @Test
    void TestDuplicationMap(){
        Duplication d1 = new Duplication("a", "b");
        Duplication d2 = new Duplication("a", "b");
        Duplication d3= new Duplication("a", "c");
        Duplication d4 = new Duplication("b", "a");

        Map<Duplication, Integer> map = new HashMap<>();
        map.put(d1, 1);
        map.put(d2, 2);
        System.out.println(map.size());
    }

}
