package techdebt;

import object.Project;
import pmd.Tools;

public class TechDebt {
    public static Project p;

    public static Project generateObjectsWithTechDebt(String codeDir, Project p){
        TechDebt.p = p;
        generateObjectsWithCyclomatic(codeDir);
        generateObjectsWithDuplication(codeDir);
        return TechDebt.p;
    }

    private static void generateObjectsWithCyclomatic(String codeDir){
        TechDebt.p.cyclomatics = Tools.generatePmdOutput(codeDir);
        TechDebt.p.cyclomatics.stream()
                .filter(l -> l.contains("has a cyclomatic complexity"))
                .forEach(TechDebt::addCyclomaticToObject);
    }

    private static void addCyclomaticToObject(String line){
        String packName = splitPackageName(line);
        String cname = splitClassName(line);
        String mname = splitMethodName(line);

        String tmname;
        if( p.packages.get(packName).classes.get(cname).methods.get(mname).cyclomatic == 0){
            tmname = mname;
        }else{
            int i = 2;
            while (p.packages.get(packName).classes.get(cname).methods.get(mname+ "@" + i).cyclomatic != 0){
                i++;
            }
            tmname = mname + "@" + i;
        }

        p.packages.get(packName).classes.get(cname).methods.get(tmname).cyclomatic = splitCyclomatic(line);
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

    private static String splitMethodName(String line){
        return line.substring(line.indexOf("'") + 1, line.lastIndexOf("("));
    }

    public static int splitCyclomatic(String line){
        int index = line.lastIndexOf(" ");
        int endIndex = line.lastIndexOf(".");
        return Integer.parseInt(line.substring(index+1, endIndex));
    }

    private static void generateObjectsWithDuplication(String codeDir){
        TechDebt.p.duplications = Tools.generateCpdOutput(codeDir);
        TechDebt.p.duplications.stream()
                .filter(TechDebt::filterDuplicationOutput)
                .forEach(TechDebt::addDuplicationOutput);
    }

    private static boolean filterDuplicationOutput(String str){
        return str.contains("Found a");
    }

    private static void addDuplicationOutput(String line){
        p.duplication += Integer.parseInt(line.substring(8, line.indexOf("line")-1));
    }

}
