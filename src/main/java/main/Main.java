package main;

import astfile.Ast;
import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techdebt.TechDebt;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
//        String FILE_PATH = "";
        String codeDir = "./src/test/java/testdata/";
        Project p = new Project(codeDir);
        Ast.generateObjectsWithAst(codeDir, p);
        TechDebt.generateObjectsWithTechDebt(codeDir, p);

    }
}
