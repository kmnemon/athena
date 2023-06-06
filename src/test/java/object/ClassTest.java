package object;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ClassTest {
    @Test
    public void getMethodsWithAccessTest(){
        Class c = new Class("ca", "packa", false, 3);

        Method m1 = new Method("a", "packa","ca", "voidfn", Access.PUBLIC, 3, 59);
        Method m2 = new Method("b", "packa","ca", "voidfn", Access.PUBLIC, 3, 59);

        c.methods.put("a", m1);
        c.methods.put("b", m2);
        c.methods.put("c", new Method("a", "packa","ca", "voidfn", Access.PRIVATE, 3, 59));
        c.methods.put("d", new Method("a", "packa","ca", "voidfn", Access.NONE, 3, 59));

        Map<String, Method> filterMethods = c.getMethodsWithAccess(Access.PUBLIC);

        assertEquals(2, filterMethods.size());
        assertSame(m1, c.methods.get("a"));
        assertSame(m2, c.methods.get("b"));


    }
}
