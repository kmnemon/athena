package object;

import astfile.Ast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import techdebt.*;

import java.io.File;
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
        this.printWriter = getPrintWriter();
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

    public void printMaintenanceStatistics(String format) throws IOException {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this.maintenance.maintenanceStatistics);
        } else if (Objects.equals(format, "text")) {
            printWriter.println(this.maintenance.maintenanceStatistics);
        }

    }

    public void printRegulationStatistics(String format) throws IOException {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this.regulation.regulationStatistics);
        } else if (Objects.equals(format, "text")) {
            printWriter.println(this.regulation.regulationStatistics);
        }
    }

    public void printDesignStatistics(String format) throws IOException {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this.design.designStatistics);
        } else if (Objects.equals(format, "text")) {
            printWriter.println(this.design.designStatistics);
        }
    }

    public void printObjectStatistics(String format) throws IOException {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this.maintenance.maintenanceStatistics);
            System.out.println(this.regulation.regulationStatistics);
            System.out.println(this.design.designStatistics);
        } else if (Objects.equals(format, "text")) {
            printWriter.println(this.maintenance.maintenanceStatistics);
            printWriter.println(this.regulation.regulationStatistics);
            printWriter.println(this.design.designStatistics);
            printWriter.close();
        }

    }

    public void printTechDebtObjects(String reportDir) throws IOException {
        FileWriter fileWriter = new FileWriter(new File(reportDir, "tech_debt_objects"));
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(maintenance);
        printWriter.println(regulation);
        printWriter.println(design);
        printWriter.close();
    }

    private PrintWriter getPrintWriter() {
        PrintWriter printWriter = null;
        String pathStr = generateReportPathStr(codeDir, "", reportDir);

        try {
            printWriter = new PrintWriter(new FileWriter(pathStr + "summary"));
        }catch (IOException e){
            log.info("new FileWriter failed");
        }
        assert printWriter != null;
        return printWriter;
    }

}
