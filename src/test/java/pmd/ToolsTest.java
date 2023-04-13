package pmd;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pmd.Tools.generateReportPathStr;

public class ToolsTest {
    @Test
    void TestGenerateReportFileName(){
        String s = generateReportPathStr("D:\\123", "rulesets/java", "D:\\supprt\\2\\");

        assertEquals("D:\\supprt\\2\\rulesets_java-D_123", s);
    }
}
