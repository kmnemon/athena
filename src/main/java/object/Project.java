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

    public Project(String name) {
        this.name = name;
        this.packages = new HashMap<>();
        this.maintenance = new Maintenance(this);
        this.regulation = new Regulation(this);
    }

    public static Project parseObjects(String codeDir){
        Project p = new Project(codeDir);
        Ast.generateObjectsWithAst(codeDir, p);
        p.maintenance.parseMaintenanceTechDebt(codeDir);
        p.regulation.parseRegulationTechDebt(codeDir);

        return p;
    }







    

}
