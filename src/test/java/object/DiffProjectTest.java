package object;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiffProjectTest {

    @Test
    void testGetRidOfLineNumberFromList(){
        List<String> l = new ArrayList<>();
        l.add("abc :123:");
        l.add("sdfsf :23: dfdf");
        l.add("sdf/sdf/sfd.java:34: sdf");

        List<String> change = DiffProject.getRidOfLineNumberFromList(l);

        assertEquals(3, change.size());
        assertEquals("abc ", change.get(0));
        assertEquals("sdfsf  dfdf", change.get(1));
        assertEquals("sdf/sdf/sfd.java sdf", change.get(2));
    }

    @Test
    void testGetRidOfLineNumberFromMap(){
        Map<String, Integer> m = new HashMap<>();
        m.put("abc :123:", 1);
        m.put("sdf/sdf/sfd.java:34: sdf", 45);

        Map<String, Integer> change = DiffProject.getRidOfLineNumberFromMap(m);

        assertEquals(2, change.size());
        assertTrue(change.containsKey("sdf/sdf/sfd.java sdf"));
    }
}
