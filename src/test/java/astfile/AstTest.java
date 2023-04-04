package astfile;

import object.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static astfile.Ast.splitMethodSig;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AstTest {
    private Project p;
    Ast ast;
    @BeforeEach
    public void setUp(){
        p = new Project("1");
        Ast.p = p;
        Ast.astToObjects(Paths.get("./src/test/java//testdata/TestClass.java"));

    }
    @Test
    public void TestAstToPackage(){
        assertEquals("testdata", ast.p.packages.get("testdata").name);
    }

    @Test
    public void TestAstToClasses(){
        assertEquals(3, ast.p.packages.get("testdata").classes.size());
        assertEquals("TestClass", ast.p.packages.get("testdata").classes.get("TestClass").name);
        assertEquals("TT", ast.p.packages.get("testdata").classes.get("TT").name);

        assertEquals(2, ast.p.packages.get("testdata").classes.get("TestClass").variableCount);
    }

    @Test
    public void TestAstToMethods(){
        assertEquals(1, ast.p.packages.get("testdata").classes.get("TestClass").methods.size());
        assertEquals("fn", ast.p.packages.get("testdata").classes.get("TestClass").methods.get("private void fn(int fn1, int fn2, int fn3)").name);
        assertEquals("private void fn(int fn1, int fn2, int fn3)", ast.p.packages.get("testdata").classes.get("TestClass").methods.get("private void fn(int fn1, int fn2, int fn3)").declaration);
        assertEquals(3, ast.p.packages.get("testdata").classes.get("TestClass").methods.get("private void fn(int fn1, int fn2, int fn3)").parametersCount);
        assertEquals(7, ast.p.packages.get("testdata").classes.get("TestClass").methods.get("private void fn(int fn1, int fn2, int fn3)").lines);

    }

    @Test
    public void TestAstToComments(){
        assertEquals(6, ast.p.packages.get("testdata").classes.get("X").comments.size());
        assertEquals(2, ast.p.packages.get("testdata").classes.get("X").comments.get(3));
    }

    @Test
    public void TestSplitMethodSig(){
        String r1 = splitMethodSig("public String fn()", "fn");
        String r2 = splitMethodSig("String fn(int fn1)", "fn");
        String r3 = splitMethodSig("private void fn(int fn1, int fn2, int fn3)", "fn");

        assertEquals("fn()", r1);
        assertEquals("fn(int)", r2);
        assertEquals("fn(int, int, int)", r3);

    }
}
