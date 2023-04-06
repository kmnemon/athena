package main;


import object.DiffProjectTechDebt;
import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        String FILE_PATH = "";
        String codeDir = "./src/test/java/testdata/";
        Project target = Project.parseObjects(codeDir);

        Project base = Project.parseObjects("");

        Project diff = DiffProjectTechDebt.diffProjectTechDebt(base, target);

    }
}
