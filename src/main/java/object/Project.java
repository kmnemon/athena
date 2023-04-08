package object;

import astfile.Ast;
import techdebt.Maintenance;
import techdebt.Regulation;

import java.util.HashMap;
import java.util.Map;

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

    public void printPrintObjects(String format, String reportDir) throws IOException{
        if(format == "cmd"){
            System.out.println(new PrintMaintenance(maintenance));
            System.out.println(new PrintRegulation(regulation));
            System.out.println(new PrintDesign(design));
        }else if( format = "text"){
            FileWriter fileWriter = new FileWriter(new File(reportDir, "print_summary"));
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(new PrintMaintenance(maintenance));
            printWriter.println(new PrintRegulation(regulation));
            printWriter.println(new PrintDesign(design));
            printWriter.close();
        }

    }

    public void printTechDebtObjects(String reportDir) throws IOException{
            FileWriter fileWriter = new FileWriter(new File(reportDir, "tech_debt_objects"));
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(new Maintenance(maintenance));
            printWriter.println(new Regulation(regulation));
            printWriter.println(new Design(design));
            printWriter.close();
    }







    

}
