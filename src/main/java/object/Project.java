package object;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techdebt.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static pmd.Tools.generateReportPathStr;

public class Project {
    public String name;
    public Map<String, Package> packages;

    public Maintenance maintenance;
    public Regulation regulation;
    public Design design;

    public String codeDir;
    public String reportDir;
    private PrintWriter printWriter;

    private static final Logger log = LoggerFactory.getLogger(Project.class);

    public Project(String codeDir, String reportDir) {
        this.name = codeDir;
        this.packages = new HashMap<>();

        this.maintenance = new Maintenance(this);
        this.regulation = new Regulation();
        this.design = new Design();

        this.codeDir = codeDir;
        this.reportDir = reportDir;

        this.printWriter = getPrintWriter(generateReportPathStr(this.codeDir, "summary", this.reportDir));
    }

    public void parseObjects() {
        this.parseMaintenanceDebt();
        this.parseRegulationDebt();
        this.parseDesignDebt();
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

    public void printMaintenanceStatistics(String format){
        if (Objects.equals(format, "cmd")) {
            System.out.println(this.maintenance.maintenanceStatistics);
        } else if (Objects.equals(format, "text")) {
            printWriter.println(this.maintenance.maintenanceStatistics);
            printWriter.flush();
        }

    }

    public void printRegulationStatistics(String format) {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this.regulation.regulationStatistics);
        } else if (Objects.equals(format, "text")) {
            printWriter.println(this.regulation.regulationStatistics);
            printWriter.flush();
        }
    }

    public void printDesignStatistics(String format) {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this.design.designStatistics);
        } else if (Objects.equals(format, "text")) {
            printWriter.println(this.design.designStatistics);
            printWriter.flush();
        }
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
