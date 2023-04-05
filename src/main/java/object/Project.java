package object;

import java.util.HashMap;
import java.util.Map;

public class Project {
    public String name;
    public Map<String, Package> packages;

    public Project(String name) {
        this.name = name;
        this.packages = new HashMap<>();

        this.cyclomatic = new ArrayList<>();
        this.duplications = new ArrayList<>();

        this.godClassWithMethods = new HashMap<>();
        this.godClassWithVariables = new HashMap<>();
        this.godComments = new HashMap<>();

        this.superMethodWithParameters = new HashMap<>();
        this.superMethodWithLines = new HashMap<>();
        this.superCyclomatics = new HashMap<>();
    }

    public static parseObjects(String codeDir) Project{
        Project p = new Project(codeDir);
        Ast.generateObjectsWithAst(codeDir, p);
        TechDebt.generateObjectsWithTechDebt(codeDir, p);
        parseTechDebt();
        return p;
    }

    public List<String> cyclomatics;
    public List<String> duplications;

    public Map<String, int> godClassWithMethods;
    public Map<String, int> godClassWithVariables;
    public Map<String, int> godComments;


    public Map<String, int> superMethodWithParameters;
    public Map<String, int> superMethodWithLines;
    public Map<String, int> superCyclomatics;

    private void parseTechDebt(){
        this.packages.

    }

    private void parseGodClass(Class c){
        parseGodClassWithMethods(c);
        parseGodClassWithVariables(c);
        parseGodComments(c);
    }

    private void parseGodClassWithMethods(Class c){
        if( c.methods.size() > 30){
            this.godClassWithMethods.put(generateFullClassName(c.packName, c.name), c.methods.szie());
        }
    }

    private void parseGodClassWithVariables(Class c){
        if( c.variableCount > 20){
            this.godClassWithVariables.put(generateFullClassName(c.packName, c.name), c.variableCount);
        }
    }

    private void parseGodComments(Class c){
        if( c.comments > 10){
            this.godComments.put(generateFullClassName(c.packName, c.name), c.comments.size());
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
            this.superMethodWithParameters.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.parametersCount)
        }
    }

    private void parseSuperMethodWithLines(Method m){
        if( m.lines > 100){
            this.superMethodWithLines.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.lines;)
        }
    }

    private void parseSuperCyclomatics(Method m){
        if( m.cyclomatic > 9){
            this.superCyclomatics.put(generateFullMethodName(m.packName, m.cname, m.declaration), m.cyclomatic;)
        }
    }

    public String generateFullMethodName(String packName, String cname, String mDeclaration){
        return packName + "." + cname + "." + mDeclaration;
    }



    

}
