package techdebt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static techdebt.Maintenance.splitCyclomatic;

public class TechDebtUitlTest {
    @Test
    public void testTest(){
        String lines = "xxx\\src\\main\\java\\com\\ke\\aaa.java";
        int index = lines.lastIndexOf("\\src\\main\\java\\") + 15;

        int midindex = lines.lastIndexOf("\\");

        int endindex = lines.lastIndexOf(".java");

        String packageName = lines.substring(index, midindex).replace("\\", ".");
        String className = lines.substring(midindex+1, endindex);

        System.out.println(packageName);
        System.out.println(className);


    }

    @Test
    public void testSplitCyclomatic(){
        String s = "xxx of 13.";

        int cycl = splitCyclomatic(s);

        assertEquals(13, cycl);
    }
}
