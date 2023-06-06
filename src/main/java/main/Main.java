package main;


import object.*;
import util.Util;

import java.util.List;
import java.util.Map;

public class Main {
    public static final boolean UNIX = !System.getProperty("os.name").toLowerCase().contains("win");
    private static final Map<String, Object> data = Util.initEnvData("application.yml");

    private static final List<Map<String, String>> projectList = (List<Map<String, String>>) data.get("project");
    private static final String reportDir =Util.addEndCharacterToDirectory((String) data.get("report.uri"));
    private static final Map<String, Boolean> rules = (Map<String, Boolean>) data.get("scan.rules");

    public static void main(String[] args) {
        for (Map<String, String> projectPair : projectList) {
            Project target = parseProject(projectPair.get("target"));

            Project base = parseProject(projectPair.get("base"));

            diffProject(target, base);
        }
    }

    static Project parseProject(String projectDir) {
        if (projectDir == null || projectDir.isEmpty()) {
            return null;
        }

        System.out.printf("~~~~%s begin~~~~: \n", projectDir);

        Project p = new Project(projectDir, reportDir);
        p.parseTechDebt(rules);

        System.out.printf("~~~~%s finish~~~~\n\n", projectDir);

        return p;
    }

    private static void diffProject(Project target, Project base) {
        if( target != null && base != null) {
            System.out.println("~~~~diff begin~~~~");
            String projectName = target.name +"-" + base.name;

            DiffProject cp = new ChangeProject(projectName, base, target, reportDir);
            cp.parseDiffTechDebt(rules);

            DiffProject lp = new LimitProject(projectName, base, target, reportDir);
            lp.parseDiffTechDebt(rules);

            System.out.println("~~~~diff finish~~~~");
        }
    }
}

