package main;


import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        String reportDir = "/Users/ke/tmp/";
        new File(reportDir).mkdirs();

        Map<String, String> projectDirs = new HashMap<>();

        projectDirs.put("./src/test/java/testdata/", "");

        for(Map.Entry<String, String> pdir : projectDirs.entrySet()) {

            Project target = new Project(pdir.getKey(), reportDir);
            System.out.println("~~~~target begin~~~~");
            target.parseMaintenanceDebt();
            target.printMaintenanceStatistics("text");

            target.parseRegulationDebt();
            target.printRegulationStatistics("text");

            target.parseDesignDebt();
            target.printDesignStatistics("text");
            System.out.println("~~~~target finish~~~~");

            System.out.println();
        }

    }
}
