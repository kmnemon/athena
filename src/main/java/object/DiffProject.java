package object;

import com.google.gson.GsonBuilder;
import techdebt.*;

import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

import static object.Project.getPrintWriter;
import static pmd.Tools.generateReportPathStr;

public class DiffProject {
    public String name;
    transient Project base;
    transient Project target;

    public Maintenance maintenanceChange;
    public Regulation regulationChange;
    public Design designChange;

    public Maintenance maintenanceOnlyIncrease;
    public Regulation regulationOnlyIncrease;
    public Design designOnlyIncrease;

    transient public String reportDir;

    public DiffProject(String name, Project base, Project target, String reportDir) {
        this.name = name;
        this.base = base;
        this.target = target;

        this.maintenanceChange = new Maintenance();
        this.regulationChange = new Regulation();
        this.designChange = new Design();

        this.maintenanceOnlyIncrease = new Maintenance();
        this.regulationOnlyIncrease = new Regulation();
        this.designOnlyIncrease = new Design();

        this.reportDir = reportDir;
    }

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public void diffTechDebtObject(){
        this.diffTechDebtObjectChange();
        this.diffTechDebtObjectOnlyIncrease();
        this.diffDesignChange();
    }

    public void diffTechDebtObjectChange(){
        this.diffMaintenanceChange();
        this.diffRegulationChange();
    }

    public void diffTechDebtObjectOnlyIncrease() {
        this.diffMaintenanceOnlyIncrease();
        this.diffRegulationOnlyIncrease();
        this.diffDesignOnlyIncrease();

        this.diffMaintenanceStatistics();
        this.diffRegulationStatistics();
        this.diffDesignStatistics();
    }

    private void diffMaintenanceChange(){

    }

    private void diffRegulationChange(){

    }

    private void diffDesignChange(){


    }

    //Maintenance, regulation, design only increase data
    private void diffMaintenanceOnlyIncrease() {
        this.maintenanceOnlyIncrease.cyclomaticOriginData = diffListOnlyInSecond(base.maintenance.cyclomaticOriginData, target.maintenance.cyclomaticOriginData);
        this.maintenanceOnlyIncrease.duplicationOriginData = diffListOnlyInSecond(base.maintenance.duplicationOriginData, target.maintenance.duplicationOriginData);
        //if sum change
        this.maintenanceOnlyIncrease.superDuplications = diffSumValue(base.maintenance.superDuplications, target.maintenance.superDuplications);

        this.maintenanceOnlyIncrease.godClassWithMethods = diffMapOnlyIncreaseInSecondMap(base.maintenance.godClassWithMethods, target.maintenance.godClassWithMethods);
        this.maintenanceOnlyIncrease.godClassWithVariables = diffMapOnlyIncreaseInSecondMap(base.maintenance.godClassWithVariables, target.maintenance.godClassWithVariables);
        this.maintenanceOnlyIncrease.godComments = diffMapOnlyIncreaseInSecondMap(base.maintenance.godComments, target.maintenance.godComments);
        this.maintenanceOnlyIncrease.superMethodWithParameters = diffMapOnlyIncreaseInSecondMap(base.maintenance.superMethodWithParameters, target.maintenance.superMethodWithParameters);
        this.maintenanceOnlyIncrease.superMethodWithLines = diffMapOnlyIncreaseInSecondMap(base.maintenance.superMethodWithLines, target.maintenance.superMethodWithLines);
        this.maintenanceOnlyIncrease.superCyclomatics = diffMapOnlyIncreaseInSecondMap(base.maintenance.superCyclomatics, target.maintenance.superCyclomatics);
    }

    private void diffRegulationOnlyIncrease() {
        this.regulationOnlyIncrease.commentOriginData = diffListOnlyInSecond(base.regulation.commentOriginData, target.regulation.commentOriginData);
        this.regulationOnlyIncrease.constantOriginData = diffListOnlyInSecond(base.regulation.constantOriginData, target.regulation.constantOriginData);
        this.regulationOnlyIncrease.exceptionOriginData = diffListOnlyInSecond(base.regulation.exceptionOriginData, target.regulation.exceptionOriginData);
        this.regulationOnlyIncrease.flowControlOriginData = diffListOnlyInSecond(base.regulation.flowControlOriginData, target.regulation.flowControlOriginData);
        this.regulationOnlyIncrease.namingOriginData = diffListOnlyInSecond(base.regulation.namingOriginData, target.regulation.namingOriginData);
        this.regulationOnlyIncrease.oopOriginData = diffListOnlyInSecond(base.regulation.oopOriginData, target.regulation.oopOriginData);
        this.regulationOnlyIncrease.setOriginData = diffListOnlyInSecond(base.regulation.setOriginData, target.regulation.setOriginData);
        this.regulationOnlyIncrease.otherOriginData = diffListOnlyInSecond(base.regulation.otherOriginData, target.regulation.otherOriginData);
    }

    private void diffDesignOnlyIncrease() {
        this.designOnlyIncrease.designOriginData = diffListOnlyInSecond(base.design.designOriginData, target.design.designOriginData);
        this.designOnlyIncrease.multithreadingOriginData = diffListOnlyInSecond(base.design.multithreadingOriginData, target.design.multithreadingOriginData);
        this.designOnlyIncrease.performanceOriginData = diffListOnlyInSecond(base.design.performanceOriginData, target.design.performanceOriginData);
    }

    public static List<String> diffListOnlyInSecond(List<String> base, List<String> target) {
        if (target == null){
            return new ArrayList<>();
        }

        base = getRidOfLineNumberFromList(base);
        target = getRidOfLineNumberFromList(target);

        List<String> diff = new ArrayList<>(target);
        diff.removeAll(base);
        return diff;
    }

    public static List<String> getRidOfLineNumberFromList(List<String> l){
        return l.stream().map(DiffProject::getRidOfLineNumber).collect(Collectors.toList());
    }

    private static Map<String, Integer> diffMapOnlyIncreaseInSecondMap(Map<String, Integer> base, Map<String, Integer> target) {
        if(target == null){
            return new HashMap<>();
        }

        base  = getRidOfLineNumberFromMap(base);
        target = getRidOfLineNumberFromMap(target);

        Map<String, Integer> diffMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : target.entrySet()) {
            if (base.containsKey(entry.getKey())) {
                Integer baseValue = base.get(entry.getKey());
                if (entry.getValue() > baseValue) {
                    diffMap.put(entry.getKey(), entry.getValue() - baseValue);
                }
            } else {
                diffMap.put(entry.getKey(), entry.getValue());
            }
        }

        return diffMap;
    }

    public static Map<String, Integer> getRidOfLineNumberFromMap(Map<String, Integer> m){
        Map<String, Integer> newMap = new HashMap<>();

        for (String key : m.keySet()) {
            String reducedKey = getRidOfLineNumber(key);
            newMap.put(reducedKey, m.get(key));
        }

        return newMap;
    }


    public static String getRidOfLineNumber(String str){
        return str.replaceAll(":\\d+:", "");
    }

    private static List<Integer> diffSumValueOnlyIncrease(List<Integer> base, List<Integer> target){
        List<Integer> l = diffSumValue(base, target);
        if( l.get(0) <0 ){
            l.set(0, 0);
        }
        return l;

    }

    private static List<Integer> diffSumValue(List<Integer> base, List<Integer> target){
        List<Integer> l = new ArrayList<>();
        l.add(target.stream().mapToInt(Integer::intValue).sum() - base.stream().mapToInt(Integer::intValue).sum());
        return l;

    }

    //diffPrint contain only changes
    private void diffMaintenanceStatistics() {
        this.maintenanceOnlyIncrease.generateMaintenanceStatistics();
    }

    private void diffRegulationStatistics() {
        this.regulationOnlyIncrease.generateRegulationStatistics();
    }

    private void diffDesignStatistics() {
        this.designOnlyIncrease.generateDesignStatistics();
    }


    public void printDiffProject(String format) {
        if (Objects.equals(format, "cmd")) {
            System.out.println(this);
        } else if (Objects.equals(format, "text")) {
            String pathStr = generateReportPathStr("", "diff--summary", reportDir);

            PrintWriter printWriter = getPrintWriter(pathStr);
            printWriter.println(this);
            printWriter.close();
        }
    }
}
