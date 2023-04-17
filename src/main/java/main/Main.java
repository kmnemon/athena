package main;


import object.DiffProject;
import object.Project;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        String reportDir = "/Users/ke/tmp/";
        if( !new File(reportDir).mkdirs()){
            deleteDirectory(reportDir);
        }

        Map<String, String> projectDirs = new HashMap<>();
        projectDirs.put("./src/test/java/testdata/", "");

        for(Map.Entry<String, String> pdir : projectDirs.entrySet()) {

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
            DiffProject dp = new DiffProject("diff", base, target, reportDir);
            dp.diffTechDebtObjectStatistics();
            dp.printDiffProject("text");

            System.out.println("~~~~diff finish~~~~");

        }

    }

    public static void deleteDirectory(String dir) {
        Arrays.stream(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(Predicate.not(File::isDirectory))
                .forEach(File::delete);
    }
}
