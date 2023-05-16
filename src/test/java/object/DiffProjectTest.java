package object;

import org.junit.jupiter.api.Test;

import java.util.*;

import static object.DiffProject.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiffProjectTest {

    @Test
    void testDiffListOnlyInSecond(){
        List<String> a = new ArrayList<>();
        a.add("abc");
        a.add(("bcd.java"));

        List<String> b = new ArrayList<>();
        b.add(("abc"));

        List<String> diff = diffListOnlyInSecond(b, a);

        assertEquals(1, diff.size());
        assertEquals("bcd.java", diff.get(0));
    }

    @Test
    void testDiffListOnlyInSecondAndFilterLineNumber(){
        List<String> a = new ArrayList<>();
        a.add("abc:15:");
        a.add(("bcd:345: .java"));

        List<String> b = new ArrayList<>();
        b.add(("abc:678:"));

        List<String> diff = diffListOnlyInSecondAndFilterLineNumber(b, a);

        assertEquals(1, diff.size());
        assertEquals("bcd:345: .java", diff.get(0));
    }

    @Test
    void testDiffMapOnlyIncreaseInSecondMap(){
        Map<String, Integer> target = new HashMap<>();
        target.put("abc:", 1);
        target.put("sdf/sdf/sfd.java: sdf", 45);
        target.put("sdf/sdf/sfd123.java: sdf", 4);

        Map<String, Integer> base = new HashMap<>();
        base.put("abc:", 1);
        base.put("sdf/sdf/sfd.java: sdf", 4);
        base.put("sdf/sdf.java: sdf", 4);


        Map<String, Integer> change = diffMapOnlyIncreaseInSecondMap(base, target);

        assertEquals(2, change.size());
        assertTrue(change.containsKey("sdf/sdf/sfd.java: sdf"));
        assertTrue(change.containsKey("sdf/sdf/sfd123.java: sdf"));

    }

    @Test
    void testdiffMapOnlyIncreaseInSecondMapAndFilterLineNumber(){
        Map<String, Integer> target = new HashMap<>();
        target.put("abc :123:", 1);
        target.put("sdf/sdf/sfd.java:34: sdf", 45);
        target.put("sdf/sdf/sfd123.java:34: sdf", 4);

        Map<String, Integer> base = new HashMap<>();
        base.put("abc :13:", 1);
        base.put("sdf/sdf/sfd.java:346: sdf", 4);
        base.put("sdf/sdf.java:346: sdf", 4);

        Map<String, Integer> diffMap = new HashMap<>(target);
        for (Iterator<Map.Entry<String, Integer>> it = diffMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Integer> entryDiff = it.next();



//        Map<String, Integer> change = diffMapOnlyIncreaseInSecondMap(base, target);
//
//        assertEquals(2, change.size());
//        assertTrue(change.containsKey("sdf/sdf/sfd.java:34: sdf"));
//        assertTrue(change.containsKey("sdf/sdf/sfd123.java:34: sdf"));

    }

        System.out.println(diffMap);
    }
}
