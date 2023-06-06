package object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techdebt.Design;
import techdebt.Maintenance;
import techdebt.Regulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static pmd.Tools.generateReportPathStr;

public class Project{
    public String name;
    transient public Map<String, Package> packages;

    public Maintenance maintenance;
    public Regulation regulation;
    public Design design;

    public String codeDir;
    public String reportDir;

    private static final Logger log = LoggerFactory.getLogger(Project.class);

    public Project(String codeDir, String reportDir) {
        this.name = codeDir;
        this.packages = new HashMap<>();

        this.maintenance = new Maintenance(this);
        this.regulation = new Regulation();
        this.design = new Design();

        this.codeDir = codeDir;
        this.reportDir = reportDir;
    }


    public void parseTechDebt(Map<String, Boolean> rules) {
        createOrCleanReportDir(codeDir, reportDir);

        PrintWriter printWriter = getPrintWriter(generateReportPathStr(this.codeDir, "summary", this.reportDir));

        if( rules.get("maintenance")) {
            this.parseMaintenanceDebt();
            this.printMaintenanceStatistics(printWriter);
            this.printMaintenance();
        }

        if(rules.get("regulation")) {
            this.parseRegulationDebt();
            this.printRegulationStatistics(printWriter);
        }

        if(rules.get("design")) {
            this.parseDesignDebt();
            this.printDesignStatistics(printWriter);
        }
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

    public void parseMaintenanceDebt() {
        this.maintenance.parseMaintenanceTechDebt(this.codeDir, this.reportDir);
    }

    public void parseRegulationDebt() {
        this.regulation.parseRegulationTechDebt(this.codeDir, this.reportDir);
    }

    public void parseDesignDebt() {
        this.design.parseDesignTechDebt(this.codeDir, this.reportDir);
    }

    public void printMaintenance() {
        PrintWriter printWriter = getPrintWriter(generateReportPathStr(this.codeDir, "maintenance", this.reportDir));

        printWriter.println(this.maintenance);
        printWriter.flush();
        printWriter.close();
    }

    public void printMaintenanceStatistics(PrintWriter printWriter) {
        printWriter.println(this.maintenance.maintenanceStatistics);
        printWriter.flush();
    }

    public void printRegulationStatistics(PrintWriter printWriter) {
        printWriter.println(this.regulation.regulationStatistics);
        printWriter.flush();
    }

    public void printDesignStatistics(PrintWriter printWriter) {
        printWriter.println(this.design.designStatistics);
        printWriter.flush();
    }

    public static PrintWriter getPrintWriter(String pathStr) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileWriter(pathStr));
        }catch (IOException e){
            log.info("new FileWriter failed");
        }
        assert printWriter != null;
        return printWriter;
    }
}
