package object;

import org.junit.jupiter.api.Test;
import techdebt.Duplication;

import java.util.HashMap;
import java.util.Map;

import static object.WhiteList.removeDuplicationWhenContainInName;
import static object.WhiteList.removeItemWhenContainInName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class WhiteListTest {
    @Test
    public void TestInitWhiteListFromYaml(){
        WhiteList.initWhiteListFromYaml();

        assertEquals(2, WhiteList.maintenanceWhiteList.size());
    }

    @Test
    public void TestRemoveItemWhenContainInPackage(){
        Map<String, Integer> m = new HashMap<>();
        m.put("com.ke.pmd.A", 3);
        m.put("com.ke.object", 4);

        removeItemWhenContainInName(m, "com.ke.pmd");

        assertEquals(1, m.size());
        assertNull(m.get("com.ke.pmd.A"));
    }

    @Test
    public void TestRemoveDuplicationWhenContainInPackage(){
        Map<Duplication, Integer> m = new HashMap<>();
        Duplication d = new Duplication("com.ke.pmd.A", "com.ke.object.B");
        Duplication d2 = new Duplication("com.ke.object.A", "com.ke.object.B");
        Duplication d3 = new Duplication("com.ke.pmd.A", "com.ke.pmd.B");

        m.put(d,1);
        m.put(d2, 2);
        m.put(d3, 3);

        removeDuplicationWhenContainInName(m, "com.ke.object");

        assertEquals(1, m.size());
        assertEquals(3, m.get(d3));

    }
}
