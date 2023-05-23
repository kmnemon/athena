package main;

import object.Project;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static main.Main.parseProject;

public class MainTest {
    @Test
    public void TestParseProject(){
        String projectDir;
        String reportDir;
        if(Main.UNIX){
            projectDir= "./src/test/java/testdata";
            reportDir = "/Users/ke/tmp/report/";
        }else {
            projectDir= ".\\src\\test\\java\\testdata";
            reportDir = "D:\\10_Code\\codequality\\unit-test-report\\";
        }

        Map<String, Boolean> rules = new HashMap<>();
        rules.put("maintenance", true);
        rules.put("regulation", true);
        rules.put("design", true);

        Project p = parseProject(projectDir, reportDir, rules);

//        assertNull(p.maintenance.godComments.get("testdata.X"));

    }
}
