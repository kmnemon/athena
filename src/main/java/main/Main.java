package main;


import object.DiffProject;
import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String reportDir = "/Users/ke/tmp/";
        new File(reportDir).mkdirs();

        Map<String, String> projectDirs = new HashMap<>();

        projectDirs.put("./src/test/java/testdata/", "");

        for(Map.Entry<String, String> pdir : projectDirs.entrySet()) {

            System.out.println("~~~~target begin~~~~");
            Project target = new Project(pdir.getKey(), reportDir);
            target.parseMaintenanceDebt();
            target.printMaintenanceStatistics("text");

            target.parseRegulationDebt();
            target.printRegulationStatistics("text");

            target.parseDesignDebt();
            target.printDesignStatistics("text");
            System.out.println("~~~~target finish~~~~");

            System.out.println();//-------------------------//

            System.out.println("~~~~base begin~~~~");
            Project base = new Project(pdir.getValue(), reportDir);
            base.parseMaintenanceDebt();
            base.printMaintenanceStatistics("text");

            base.parseRegulationDebt();
            base.printRegulationStatistics("text");

            base.parseDesignDebt();
            base.printDesignStatistics("text");
            System.out.println("~~~~base finish~~~~");

            System.out.println();//-------------------------//

            System.out.println("~~~~diff begin~~~~");
            DiffProject dp = new DiffProject("diff", base, target, reportDir);
            dp.diffTechDebtObjectStatistics();
            dp.printDiffProject("text");

            System.out.println("~~~~diff finish~~~~");

        }

    }
}
