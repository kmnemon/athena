package object;

import astfile.Ast;
import techdebt.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Project {
    public String name;
    public Map<String, Package> packages;

    public Maintenance maintenance;
    public Regulation regulation;
    public Design design;

    public Project(String name) {
        this.name = name;
        this.packages = new HashMap<>();
        this.maintenance = new Maintenance(this);
        this.regulation = new Regulation();
        this.design = new Design();
    }

    public static Project parseObjects(String codeDir){
        Project p = new Project(codeDir);
        Ast.generateObjectsWithAst(codeDir, p);
        p.maintenance.parseMaintenanceTechDebt(codeDir);
        p.regulation.parseRegulationTechDebt(codeDir);
        p.design.parseDesignTechDebt(codeDir);

        return p;
    }

    public void printPrintObjects(String format, String reportDir) throws IOException {
        if(Objects.equals(format, "cmd")){
            System.out.println(maintenance.printMaintenance);
            System.out.println(regulation.printRegulation);
            System.out.println(design.printDesign);
        }else if(Objects.equals(format, "text")){
            FileWriter fileWriter = new FileWriter(new File(reportDir, "print_summary"));
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(maintenance.printMaintenance);
            printWriter.println(regulation.printRegulation);
            printWriter.println(design.printDesign);
            printWriter.close();
        }

    }

    public void printTechDebtObjects(String reportDir) throws IOException{
            FileWriter fileWriter = new FileWriter(new File(reportDir, "tech_debt_objects"));
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(maintenance);
            printWriter.println(regulation);
            printWriter.println(design);
            printWriter.close();
    }







    

}
