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

        String reportDir = (String) data.get("report.uri");

        for (Map<String, String> project : projectList) {
            Project target = null;
            String targetDir = project.get("target");
            if(targetDir != null && !targetDir.isEmpty()) {
                reportDir = createOrCleanReportDir(targetDir, reportDir);
                System.out.printf("~~~~target begin~~~~: %s\n", targetDir);
                target = new Project(project.get("target"), reportDir);
                target.parseMaintenanceDebt();
                target.printMaintenanceStatistics("text");

                target.parseRegulationDebt();
                target.printRegulationStatistics("text");

                target.parseDesignDebt();
                target.printDesignStatistics("text");
                System.out.println("~~~~target finish~~~~");
            }

            System.out.println();//-------------------------//
            Project base = null;
            String baseDir = project.get("base");
            if( baseDir != null && !baseDir.isEmpty()) {
                System.out.printf("~~~~base begin~~~~: %s\n", baseDir);
                reportDir = createOrCleanReportDir(baseDir, reportDir);

                base = new Project(project.get("base"), reportDir);
                base.parseMaintenanceDebt();
                base.printMaintenanceStatistics("text");

                base.parseRegulationDebt();
                base.printRegulationStatistics("text");

                base.parseDesignDebt();
                base.printDesignStatistics("text");
                System.out.println("~~~~base finish~~~~");
            }

            System.out.println();//-------------------------//

            if( target != null && base != null) {
                System.out.println("~~~~diff begin~~~~");
                DiffProject dp = new DiffProject(project.get("target"), base, target, reportDir);
                dp.diffTechDebtObject();
                dp.printDiffProject("text");

                System.out.println("~~~~diff finish~~~~");
            }

        }

    }

    public static String createOrCleanReportDir(String codeDir, String reportDir) {
        if(!reportDir.endsWith("/")){
            reportDir = reportDir + "/";
        }

        codeDir = codeDir.replace("/", "_");
        codeDir = codeDir.replace(":", "");

        File directory = new File(reportDir + codeDir);
        if(directory.exists()){
            deleteDirectory(reportDir + codeDir);
        }

        return reportDir;
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

