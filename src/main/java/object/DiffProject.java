package object;

import com.google.gson.GsonBuilder;
import techdebt.*;

import java.io.PrintWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static object.Project.getPrintWriter;
import static pmd.Tools.generateReportPathStr;

public class DiffProject implements P{
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

    public static Map<String, Integer> diffMapOnlyIncreaseInSecondMap(Map<String, Integer> base, Map<String, Integer> target) {
        return diffMapWithComsumer(base, target, null);
    }

    public static Map<String, Integer> diffMapOnlyIncreaseInSecondMapAndFilterLineNumber(Map<String, Integer> base, Map<String, Integer> target) {
        return diffMapWithComsumer(base, target, DiffProject::getRidOfLineNumber);
    }

    private static Map<String,Integer> diffMapWithComsumer(Map<String, Integer> base, Map<String, Integer> target, UnaryOperator<String> uop){
        if(target == null){
            return new HashMap<>();
        }

        if( uop == null){
            uop = (str) -> {return str;};
        }

        Map<String, Integer> diffMap = new HashMap<>(target);
        for (Iterator<Map.Entry<String, Integer>> it = diffMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Integer> entryDiff = it.next();
            for(Map.Entry<String, Integer> entryBase : base.entrySet()){
                if(uop.apply(entryBase.getKey()).equals(uop.apply(entryDiff.getKey()))){
                    if(entryDiff.getValue() > entryBase.getValue()){
                        diffMap.put(entryDiff.getKey(), entryDiff.getValue() - entryBase.getValue());
                    }else {
                        it.remove();
                    }
                }
            }
        }

        return diffMap;
    }

    public static String getRidOfLineNumber(String str){
        return str.replaceAll(":\\d+:", "");
    }

    public void printDiffProject(String format) {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this);
        } else if (Objects.equals(format, "text")) {
            String pathStr = generateReportPathStr(name, "diff--summary", reportDir);

            PrintWriter printWriter = getPrintWriter(pathStr);
            printWriter.println(this);
            printWriter.close();
        }
    }


    public String getName() {
        return name;
    }

    public Maintenance getMaintenance() {
        return maintenance;
    }

    public Regulation getRegulation() {
        return regulation;
    }

    public Design getDesign() {
        return design;
    }
}
