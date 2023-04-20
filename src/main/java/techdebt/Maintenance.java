package techdebt;

import astfile.Ast;
import com.google.gson.GsonBuilder;
import object.Class;
import object.Method;
import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pmd.Rulesets;
import pmd.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Maintenance {
    transient Project p;
    public MaintenanceStatistics maintenanceStatistics;

    public List<String> cyclomaticOriginData;
    public List<String> duplicationOriginData;


    //class level
    public Map<String, Integer> godClassWithMethods;
    public Map<String, Integer> godClassWithVariables;
    public Map<String, Integer> godComments;

    //method level
    public Map<String, Integer> superMethodWithParameters;
    public Map<String, Integer> superMethodWithLines;
    public Map<String, Integer> superCyclomatics;

    public List<Integer> superDuplications;


    private static final Logger log = LoggerFactory.getLogger(Maintenance.class);

    public Maintenance(){
        this.maintenanceStatistics = new MaintenanceStatistics();
    }

    public Maintenance(Project p) {
        this.p = p;

        this.godClassWithMethods = new HashMap<>();
        this.godClassWithVariables = new HashMap<>();
        this.godComments = new HashMap<>();

        this.superMethodWithParameters = new HashMap<>();
        this.superMethodWithLines = new HashMap<>();
        this.superCyclomatics = new HashMap<>();
        this.superDuplications = new ArrayList<>();
    }

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }


    public void parseMaintenanceTechDebt(String codeDir, String reportDir){
        Ast.generateObjectsWithAst(codeDir, p);

        generateOriginData(codeDir, reportDir);
        parseCyclomaticToObject();
        parseDuplication();

        parseGodOrSuper();
        
        generateMaintenanceStatistics();
    }

    public void generateMaintenanceStatistics(){
        this.maintenanceStatistics = new MaintenanceStatistics(this);
    }


    private void parseGodOrSuper(){
        for (var packs : p.packages.entrySet()){
            for( var classes : packs.getValue().classes.entrySet()){
                parseGodClass(classes.getValue());
                for( var methods : classes.getValue().methods.entrySet()){
                    parseSuperMethod(methods.getValue());
                }
            }
        }
    }

    private void parseGodClass(Class c){
        parseGodClassWithMethods(c);
        parseGodClassWithVariables(c);
        parseGodComments(c);
    }

    private void parseGodClassWithMethods(Class c){
        if( c.methods.size() > DebtLimits.GODCLASS_WITH_METHODS){
            this.godClassWithMethods.put(generateFullClassName(c.packName, c.name), c.methods.size());
        }
    }

    private void parseGodClassWithVariables(Class c){
        if( c.variableCount > DebtLimits.GODCLASS_WITH_VARIABLES){
            this.godClassWithVariables.put(generateFullClassName(c.packName, c.name), c.variableCount);
        }
    }

    private void parseGodComments(Class c){
        int commentsCount = 0;
        for( Integer commentSize : c.comments){
            if( commentSize > DebtLimits.GODCOMMENTS){
                commentsCount += commentSize;
            }
        }

        if( commentsCount > 0) {
            this.godComments.put(generateFullClassName(c.packName, c.name), commentsCount);
        }
    }

    private String generateFullClassName(String packName, String cname){
        return packName + "." + cname;
    }

    private void parseSuperMethod(Method m){
        parseSuperMethodWithParameters(m);
        parseSuperMethodWithLines(m);
        parseSuperCyclomatics(m);
    }

    private void parseSuperMethodWithParameters(Method m){
        if( m.parametersCount > DebtLimits.SUPERMETHOD_WITH_PARAMETERS){
            this.superMethodWithParameters.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.parametersCount);
        }
    }

    private void parseSuperMethodWithLines(Method m){
        if( m.lines > DebtLimits.SUPERMETHOD_WITH_LINES){
            this.superMethodWithLines.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.lines);
        }
    }

    private void parseSuperCyclomatics(Method m){
        if( m.cyclomatic > DebtLimits.SUPERCYCLOMATICS){
            this.superCyclomatics.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.cyclomatic);
        }
    }

    private String generateFullMethodName(String packName, String cname, String mDeclaration){
        return packName + "." + cname + "." + mDeclaration;
    }

    //origin data
    private void generateOriginData(String codeDir, String reportDir){
        this.cyclomaticOriginData = Tools.generatePmdOutput(codeDir, Rulesets.CYCLOMATIC.toString(), reportDir);
        this.duplicationOriginData = Tools.generateCpdOutput(codeDir, reportDir);
    }


    //generate cyclomatic and duplication debt
    private void parseCyclomaticToObject(){
        cyclomaticOriginData.stream()
                .filter(l -> l.contains("has a cyclomatic complexity"))
                .forEach(this::addCyclomaticToObject);
    }

    private void addCyclomaticToObject(String line){
        String packName = splitPackageName(line);
        String cname = splitClassName(line);
        String mname = splitMethodName(line);

        String tmname;

        cname = findPrivateClassWithMethodName(packName, cname, mname);

        tmname = handleMethodOverloadSignature(packName, cname, mname);

        p.packages.get(packName).classes.get(cname).methods.get(tmname).cyclomatic = splitCyclomatic(line);
    }
    
    private String findPrivateClassWithMethodName(String packName, String cname, String mname) {
        if (!p.packages.get(packName).classes.get(cname).methods.containsKey(mname)) {
            for( var cs : p.packages.get(packName).classes.entrySet()){
                if( cs.getValue().methods.containsKey(mname)){
                    cname = cs.getValue().name;
                    break;
                }
            }
        }
        return cname;
    }

    private String handleMethodOverloadSignature(String packName, String cname, String mname) {
        String tmname;
        if( p.packages.get(packName).classes.get(cname).methods.get(mname).cyclomatic == 0){
            tmname = mname;
        }else{
            int i = 2;
            while (p.packages.get(packName).classes.get(cname).methods.get(mname + "@" + i).cyclomatic != 0){
                i++;
            }
            tmname = mname + "@" + i;
        }
        return tmname;
    }

    private static String splitPackageName(String line){
        int index = -1;
        int endIndex = line.lastIndexOf("\\");

        int tmpIndex = line.lastIndexOf("\\src\\main\\java\\");
        int tmpIndex1 = line.lastIndexOf("\\src\\test\\java\\");
        if( tmpIndex != -1){
            index = tmpIndex + 15;
        }else if(tmpIndex1 != -1){
            index = tmpIndex1 + 15;
        } else {
            log.error("split package name wrong");
        }

        return line.substring(index, endIndex).replace("\\", ".");
    }

    private static String splitClassName(String line){
        int index = line.lastIndexOf("\\");
        int endIndex = line.lastIndexOf(".java");
        return line.substring(index+1, endIndex);
    }

    private static String splitMethodName(String line){
        return line.substring(line.indexOf("'") + 1, line.lastIndexOf("("));
    }

    public static int splitCyclomatic(String line){
        int index = line.lastIndexOf(" ");
        int endIndex = line.lastIndexOf(".");
        return Integer.parseInt(line.substring(index+1, endIndex));
    }



    private void parseDuplication(){
        duplicationOriginData.stream()
                .filter(this::filterDuplicationOutput)
                .forEach(this::addDuplicationOutput);
    }

    private boolean filterDuplicationOutput(String str){
        return str.contains("Found a");
    }

    private void addDuplicationOutput(String line){
        int duplicationLineSize = Integer.parseInt(line.substring(8, line.indexOf("line")-1));
        if( duplicationLineSize > DebtLimits.SUPERDUPLICATIONS){
            this.superDuplications.add(duplicationLineSize);
        }
    }

}
