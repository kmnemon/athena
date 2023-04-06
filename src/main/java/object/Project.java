package object;

import astfile.Ast;
import techdebt.TechDebt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project {
    public String name;
    public Map<String, Package> packages;

    public List<String> cyclomatics;
    public List<String> duplications;

    //project level
    public Integer duplication;

    //class level
    public Map<String, Integer> godClassWithMethods;
    public Map<String, Integer> godClassWithVariables;
    public Map<String, Integer> godComments;

    //method level
    public Map<String, Integer> superMethodWithParameters;
    public Map<String, Integer> superMethodWithLines;
    public Map<String, Integer> superCyclomatics;


    public Project(String name) {
        this.name = name;
        this.packages = new HashMap<>();

        this.cyclomatics = new ArrayList<>();
        this.duplications = new ArrayList<>();

        this.godClassWithMethods = new HashMap<>();
        this.godClassWithVariables = new HashMap<>();
        this.godComments = new HashMap<>();

        this.superMethodWithParameters = new HashMap<>();
        this.superMethodWithLines = new HashMap<>();
        this.superCyclomatics = new HashMap<>();
        this.duplication = 0;
    }

    @Override
    public String toString(){
        String str = "Project Tech: \n";
        str += "    God Classes: \n";
        str += "        godClassWithMethods: "+ this.godClassWithMethods.size();
        str += "sdf";
        return str;
    }



    public static Project parseObjects(String codeDir){
        Project p = new Project(codeDir);
        Ast.generateObjectsWithAst(codeDir, p);
        TechDebt.generateObjectsWithTechDebt(codeDir, p);
        p.parseTechDebt();
        return p;
    }



    private void parseTechDebt(){
        for (var packs : this.packages.entrySet()){
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
        if( c.methods.size() > 3){
            this.godClassWithMethods.put(generateFullClassName(c.packName, c.name), c.methods.size());
        }
    }

    private void parseGodClassWithVariables(Class c){
        if( c.variableCount > 2){
            this.godClassWithVariables.put(generateFullClassName(c.packName, c.name), c.variableCount);
        }
    }

    private void parseGodComments(Class c){
        int commentsCount = 0;
        for( Integer commentSize : c.comments){
            if( commentSize > 1){
                commentsCount += commentSize;
            }
        }

        if( commentsCount > 0) {
            this.godComments.put(generateFullClassName(c.packName, c.name), commentsCount);
        }
    }

    public String generateFullClassName(String packName, String cname){
        return packName + "." + cname;
    }

    private void parseSuperMethod(Method m){
        parseSuperMethodWithParameters(m);
        parseSuperMethodWithLines(m);
        parseSuperCyclomatics(m);
    }

    private void parseSuperMethodWithParameters(Method m){
        if( m.parametersCount > 10){
            this.superMethodWithParameters.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.parametersCount);
        }
    }

    private void parseSuperMethodWithLines(Method m){
        if( m.lines > 100){
            this.superMethodWithLines.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.lines);
        }
    }

    private void parseSuperCyclomatics(Method m){
        if( m.cyclomatic > 9){
            this.superCyclomatics.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.cyclomatic);
        }
    }

    public String generateFullMethodName(String packName, String cname, String mDeclaration){
        return packName + "." + cname + "." + mDeclaration;
    }



    

}
