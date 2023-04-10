package main;


import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static techdebt.DiffProjectTechDebt.diffTechDebtObjects;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        String targetCodeDir = "./src/test/java/testdata/";
        String baseCodeDir = "./src/test/java/testdata/";
        String reportDir = "";


        Project target = Project.parseObjects(targetCodeDir);
        target.printPrintObjects("cmd", reportDir);

        Project base = Project.parseObjects(baseCodeDir);

        Project diff = diffTechDebtObjects(base, target);
        diff.printPrintObjects("cmd", reportDir);

    }
}
