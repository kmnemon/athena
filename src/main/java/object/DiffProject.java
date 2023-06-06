package object;

import com.google.gson.GsonBuilder;
import techdebt.Design;
import techdebt.Maintenance;
import techdebt.Regulation;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.UnaryOperator;

import static object.Project.getPrintWriter;
import static pmd.Tools.generateReportPathStr;

public abstract class DiffProject{
    public String name;
    transient Project base;
    transient Project target;

    public Maintenance maintenance;
    public Regulation regulation;
    public Design design;

    transient public String reportDir;

    public DiffProject(String name, Project base, Project target, String reportDir) {
        this.name = name;
        this.base = base;
        this.target = target;

        this.maintenance = new Maintenance();
        this.regulation = new Regulation();
        this.design = new Design();

        this.reportDir = reportDir;
    }

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public static List<String> diffListOnlyInSecond(List<String> base, List<String> target) {
        return diffListWithConsumer(base, target, null);
    }

    public static List<String> diffListOnlyInSecondAndFilterLineNumber(List<String> base, List<String> target) {
        return diffListWithConsumer(base, target, DiffProject::getRidOfLineNumber);
    }

    private static List<String> diffListWithConsumer(List<String> base, List<String> target, UnaryOperator<String> uop){
        if (target == null){
            return new ArrayList<>();
        }

        if( uop == null){
            uop = (str) -> {return str;};
        }

        List<String> diff = new ArrayList<>(target);
        for(Iterator<String> di = diff.iterator(); di.hasNext();){
            String ds = di.next();
            for(String bs : base){
                if( uop.apply(ds).equals(uop.apply(bs))){
                    di.remove();
                    break;
                }
            }
        }

        return diff;
    }

    public static<T> Map<T, Integer> diffMapOnlyIncreaseInSecondMap(Map<T, Integer> base, Map<T, Integer> target) {
        return diffMapWithComsumer(base, target, null);
    }

    public static Map<String, Integer> diffMapOnlyIncreaseInSecondMapAndFilterLineNumber(Map<String, Integer> base, Map<String, Integer> target) {
        return diffMapWithComsumer(base, target, DiffProject::getRidOfLineNumber);
    }

    private static<T> Map<T,Integer> diffMapWithComsumer(Map<T, Integer> base, Map<T, Integer> target, UnaryOperator<T> uop){
        if(target == null){
            return new HashMap<>();
        }

        if( uop == null){
            uop = (v) -> {return v;};
        }

        Map<T, Integer> diffMap = new HashMap<>(target);
        for (Iterator<Map.Entry<T, Integer>> it = diffMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<T, Integer> entryDiff = it.next();
            for(Map.Entry<T, Integer> entryBase : base.entrySet()){
                if(uop.apply(entryBase.getKey()).equals(uop.apply(entryDiff.getKey()))){
                    if(entryDiff.getValue() > entryBase.getValue()){
                        diffMap.put(entryDiff.getKey(), entryDiff.getValue() - entryBase.getValue());
                    }else {
                        it.remove();
                    }
                    break;
                }
            }
        }

        return diffMap;
    }

    public static String getRidOfLineNumber(String str){
        return str.replaceAll(":\\d+:", "");
    }

    public void printDiffProject(String type) {
        String pathStr = generateReportPathStr(name, type, reportDir);

        PrintWriter printWriter = getPrintWriter(pathStr);
        printWriter.println(this);
        printWriter.close();
    }


    public abstract void parseDiffTechDebt(Map<String, Boolean> rules);
}
