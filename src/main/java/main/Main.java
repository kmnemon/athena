package main;


import object.DiffProject;
import object.Project;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    static Map<String, String> projectDirs = new HashMap<>();

    public static void main(String[] args) {
        String reportDir = "/Users/ke/tmp";
        projectDirs.put("./src/test/java/testdata/", "");

        for(Map.Entry<String, String> pdir : projectDirs.entrySet()) {
            createOrCleanReportDir(pdir.getKey(), reportDir);
            createOrCleanReportDir(pdir.getValue(), reportDir);

            System.out.println("~~~~target begin~~~~");
            Project target = new Project(pdir.getKey(), reportDir);
            target.parseMaintenanceDebt();
            target.printMaintenanceStatistics("text");

            target.parseRegulationDebt();
            target.printRegulationStatistics("text");

            target.parseDesignDebt();
            target.printDesignStatistics("text");
            System.out.println("~~~~target finish~~~~");

            System.out.println();//-------------------------//

            System.out.println("~~~~base begin~~~~");
            Project base = new Project(pdir.getValue(), reportDir);
            base.parseMaintenanceDebt();
            base.printMaintenanceStatistics("text");

            base.parseRegulationDebt();
            base.printRegulationStatistics("text");

            base.parseDesignDebt();
            base.printDesignStatistics("text");
            System.out.println("~~~~base finish~~~~");

            System.out.println();//-------------------------//

            System.out.println("~~~~diff begin~~~~");
            DiffProject dp = new DiffProject(pdir.getValue(), base, target, reportDir);
            dp.diffTechDebtObject();
            dp.printDiffProject("text");

            System.out.println("~~~~diff finish~~~~");

        }

    }

    private static void createOrCleanReportDir(String codeDir, String reportDir) {
        codeDir = codeDir.replace("/", "_");
        codeDir = codeDir.replace("\\", "_");
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
