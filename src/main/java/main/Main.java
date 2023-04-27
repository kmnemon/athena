package main;


import object.DiffProject;
import object.Project;
import org.yaml.snakeyaml.Yaml;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("application.yml");
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);

        List<Map<String, String>> projectList = (List<Map<String, String>>) data.get("project");

        String reportDir = handleReportDir((String) data.get("report.uri"));

        for (Map<String, String> project : projectList) {
            Project target = null;
            target = parseProject(project, target, "target", reportDir);

            Project base = null;
            base = parseProject(project, base, "base", reportDir);

            diffProject(project, target, base, reportDir);
        }
    }

    private static void diffProject(Map<String, String> project, Project target, Project base, String reportDir) {
        if( target != null && base != null) {
            System.out.println("~~~~diff begin~~~~");
            DiffProject dp = new DiffProject(project.get("target"), base, target, reportDir);
            dp.diffTechDebtObject();
            dp.printDiffProject("text");

            System.out.println("~~~~diff finish~~~~");
        }
    }

    public static String handleReportDir(String reportDir) {
        if(!reportDir.endsWith("/")){
            reportDir = reportDir + "/";
        }
        return reportDir;
    }

    private static Project parseProject(Map<String, String> projects, Project p, String flag, String reportDir) {
        String pDir = projects.get(flag);
        if(pDir != null && !pDir.isEmpty()) {
            createOrCleanReportDir(pDir, reportDir);
            System.out.printf("~~~~%s begin~~~~: %s\n", flag, pDir);
            p = new Project(projects.get(flag), reportDir);
            p.parseMaintenanceDebt();
            p.printMaintenanceStatistics("text");

            p.parseRegulationDebt();
            p.printRegulationStatistics("text");

            p.parseDesignDebt();
            p.printDesignStatistics("text");
            System.out.printf("~~~~%s finish~~~~\n", flag);
            System.out.println();//-------------------------//
        }
        return p;
    }

    public static void createOrCleanReportDir(String codeDir, String reportDir) {
        codeDir = codeDir.replace("/", "_");
        codeDir = codeDir.replace(":", "");

        File directory = new File(reportDir + codeDir);
        if(directory.exists()){
            deleteDirectory(reportDir + codeDir);
        }
    }

    public static void deleteDirectory(String dir) {
        try{
            Files.walk(Path.of(dir))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



}

