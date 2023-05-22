package main;


import object.ChangeProject;
import object.DiffProject;
import object.LimitProject;
import object.Project;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Main {
    public static boolean UNIX = !System.getProperty("os.name").toLowerCase().contains("win");

    public static void main(String[] args) {
        InputStream inputStream = getInputStream();

        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);

        List<Map<String, String>> projectList = (List<Map<String, String>>) data.get("project");
        String reportDir = handleReportDir((String) data.get("report.uri"));
        Map<String, Boolean> rules = (Map<String, Boolean>) data.get("scan.rules");

        for (Map<String, String> projectPair : projectList) {
            Project target = parseProject(projectPair.get("target"), reportDir, rules);

            Project base = parseProject(projectPair.get("base"), reportDir, rules);

            diffProject(projectPair, target, base, reportDir, rules);
        }
    }

    private static InputStream getInputStream() {
        InputStream inputStream;
        if(UNIX) {
            inputStream = Main.class.getClassLoader().getResourceAsStream("application.yml");
        }else {
            inputStream = Main.class.getClassLoader().getResourceAsStream("application-win.yml");
        }
        return inputStream;
    }

    private static Project parseProject(String projectDir, String reportDir, Map<String, Boolean> rules) {
        if (projectDir == null || projectDir.isEmpty()) {
            return null;
        }

        System.out.printf("~~~~%s begin~~~~: %s\n", projectDir);
        Project p = new Project(projectDir, reportDir);
        p.parseTechDebt(rules);

        System.out.printf("~~~~%s finish~~~~\n", projectDir);
        System.out.println();//-------------------------//

        return p;
    }

    private static void diffProject(Map<String, String> project, Project target, Project base, String reportDir, Map<String, Boolean> rules) {
        if( target != null && base != null) {
            System.out.println("~~~~diff begin~~~~");
            DiffProject cp = new ChangeProject(project.get("target") +"-" + project.get("base"), base, target, reportDir);
            cp.parseDiffTechDebt(rules);

            DiffProject lp = new LimitProject(project.get("target") +"-" + project.get("base"), base, target, reportDir);
            lp.parseDiffTechDebt(rules);

            System.out.println("~~~~diff finish~~~~");
        }
    }

    public static String handleReportDir(String reportDir) {
        if(UNIX) {
            if (!reportDir.endsWith("/")) {
                reportDir = reportDir + "/";
            }
        }else {
            if (!reportDir.endsWith("\\")) {
                reportDir = reportDir + "\\";
            }
        }
        return reportDir;
    }









}

