package pmd;

import main.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pmd.Tools.generateReportPathStr;

public class ToolsTest {
    @Test
    void testGenerateReportFileName(){
        String s;
        if(Main.UNIX) {
            s = generateReportPathStr("/123", "rulesets/java", "/report/2/");
        }else {
            s = generateReportPathStr("D:\\123", "rulesets/java", "D:\\report\\2\\");
        }

        if(Main.UNIX){
            assertEquals("/report/2/_123/rulesets_java", s);
        }else {
            assertEquals("D:\\report\\2\\D_123\\rulesets_java", s);
        }
    }
}
