package main;


import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        String FILE_PATH = "";
        String targetCodeDir = "./src/test/java/testdata/";
        String baseCodeDir = "./src/test/java/testdata/";
        String reportDir = "";



        Project target = Project.parseObjects(targetCodeDir);
        target.printPrintObjects("cmd", reportDir);

        Project base = Project.parseObjects(baseCodeDir);

       Project diff = DiffProjectTechDebt.diffTechDebtObjects(base, target);
       diff.printPrintObjects("cmd", reportDir);

    }
}
