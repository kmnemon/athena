package object;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static object.DiffProject.diffListOnlyInSecond;
import static object.DiffProject.diffMapOnlyIncreaseInSecondMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiffProjectTest {

    @Test
    void testDiffListOnlyInSecond(){
        List<String> a = new ArrayList<>();
        a.add("abc:151:");
        a.add(("bcd:345: .java"));

        List<String> b = new ArrayList<>();
        b.add(("abc:678:"));

        List<String> diff = diffListOnlyInSecond(b, a);

        assertEquals(1, diff.size());
        assertEquals("bcd:345: .java", diff.get(0));
    }

    @Test
    void testDiffMapOnlyIncreaseInSecondMap(){
        Map<String, Integer> target = new HashMap<>();
        target.put("abc :123:", 1);
        target.put("sdf/sdf/sfd.java:34: sdf", 45);
        target.put("sdf/sdf/sfd123.java:34: sdf", 4);

        Map<String, Integer> base = new HashMap<>();
        base.put("abc :13:", 1);
        base.put("sdf/sdf/sfd.java:346: sdf", 4);
        base.put("sdf/sdf.java:346: sdf", 4);


        Map<String, Integer> change = diffMapOnlyIncreaseInSecondMap(base, target);

        assertEquals(2, change.size());
        assertTrue(change.containsKey("sdf/sdf/sfd.java:34: sdf"));
        assertTrue(change.containsKey("sdf/sdf/sfd123.java:34: sdf"));

    }
}
