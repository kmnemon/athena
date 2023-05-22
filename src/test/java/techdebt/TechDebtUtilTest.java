package techdebt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static techdebt.Maintenance.splitCyclomatic;

public class TechDebtUtilTest {
    @Test
    public void testSplitCyclomatic(){
        String s = "xxx of 13.";

        int cycl = splitCyclomatic(s);

        assertEquals(13, cycl);
    }
}
