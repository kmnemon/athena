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
        this.regulation = new Regulation(this);
        this.design = new Design(this);
    }

    public static Project parseObjects(String codeDir){
        Project p = new Project(codeDir);
        Ast.generateObjectsWithAst(codeDir, p);
        p.maintenance.parseMaintenanceTechDebt(codeDir);
        p.regulation.parseRegulationTechDebt(codeDir);
        p.design.parseDesignTechDebt(codeDir);

        return p;
    }

    public void printObjects(String format) throws IOException{
        if(format == "cmd"){
            System.out.println(new PrintMaintenance(maintenance));
            System.out.println(new PrintRegulation(regulation));
            System.out.println(new PrintDesign(design));
        }else if( format = "text"){
            FileWriter fileWriter = new FileWriter(name);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(new PrintMaintenance(maintenance));
            printWriter.println(new PrintRegulation(regulation));
            printWriter.println(new PrintDesign(design));
            printWriter.close();
        }

    }









    

}
