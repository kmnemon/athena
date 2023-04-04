package techdebt;

import object.Project;
import pmd.Tools;

import java.util.List;

public class TechDebt {
    public static Project p;

    public static Project generateObjectsWithTechDebt(String codeDir, Project p){
        TechDebt.p = p;
        generateObjectsWithCyclomatic(codeDir);
        generateObjectsWithDuplication(codeDir);
        return TechDebt.p;
    }

    private static void generateObjectsWithCyclomatic(String codeDir){
        List<String> pmdOutput = Tools.generatePmdOutput(codeDir);
        pmdOutput.stream()
                .filter(l -> l.contains("has a cyclomatic complexity"))
                .forEach(TechDebt::addCyclomaticToObject);
    }

    private static void addCyclomaticToObject(String line){
        String packageName = splitPackageName(line);
        String className = splitClassName(line);
        String methodDeclaration = splitMethodDeclaration(line);

        int cyclomatic = splitCyclomatic(line);

        p.packages.get(packageName).classes.get(className).methods.get(methodDeclaration).cyclomatic = cyclomatic;
    }

    private static String splitPackageName(String line){
        int index = line.lastIndexOf("\\src\\main\\java\\") + 15;
        int endIndex = line.lastIndexOf("\\");
        return line.substring(index, endIndex).replace("\\", ".");
    }

    private static String splitClassName(String line){
        int index = line.lastIndexOf("\\");
        int endIndex = line.lastIndexOf(".java");
        return line.substring(index+1, endIndex);
    }

    private static String splitMethodDeclaration(String line){
        return "";
    }

    public static int splitCyclomatic(String line){
        int index = line.lastIndexOf(" ");
        int endIndex = line.lastIndexOf(".");
        return Integer.parseInt(line.substring(index+1, endIndex));
    }

    private static void generateObjectsWithDuplication(String codeDir){
        List<String> cpdOutput = Tools.generateCpdOutput(codeDir);
        cpdOutput.stream()
                .filter(TechDebt::filterDuplicationOutput)
                .forEach(TechDebt::addDuplicationOutput);
    }

    private static boolean filterDuplicationOutput(String str){
        return str.contains("Found a") || str.contains("Starting at line");
    }

    private static void addDuplicationOutput(String lines){
        //Found a 258 line xxx files:
        //Starting at line 258 of Dxxx.java
        //Starting at line 234 of xxx.java
    }

}
