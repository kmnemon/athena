package main;


import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        String FILE_PATH = "";
        String codeDir = "./src/test/java/testdata/";
        Project target = Project.parseObjects(codeDir);
        target.printObjects("cmd");

        Project base = Project.parseObjects("");

       Project diff = object.DiffProjectTechDebt.diffTechDebtObjects(base, target);
       diff.printObjects("cmd");

    }
}
