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
    }

    public void diffTechDebtObjectChange(){
        this.diffMaintenanceStatisticsChange();
        this.diffRegulationChange();
        this.diffDesignChange();
    }

    public void diffTechDebtObjectOnlyIncrease() {
        this.diffMaintenanceOnlyIncrease();
        this.diffRegulationOnlyIncrease();
        this.diffDesignOnlyIncrease();

        this.diffMaintenanceStatistics();
        this.diffRegulationStatistics();
        this.diffDesignStatistics();
    }

    private void diffMaintenanceStatisticsChange(){
        maintenanceChange.maintenanceStatistics.godClassesCount = target.maintenance.maintenanceStatistics.godClassesCount - base.maintenance.maintenanceStatistics.godClassesCount;
        maintenanceChange.maintenanceStatistics.maxGodClassWithVariables = target.maintenance.maintenanceStatistics.maxGodClassWithVariables - base.maintenance.maintenanceStatistics.maxGodClassWithVariables;
        maintenanceChange.maintenanceStatistics.maxGodClassWithMethods = target.maintenance.maintenanceStatistics.maxGodClassWithMethods - base.maintenance.maintenanceStatistics.maxGodClassWithMethods;
        maintenanceChange.maintenanceStatistics.medianGodClassWithVariables = target.maintenance.maintenanceStatistics.medianGodClassWithVariables - base.maintenance.maintenanceStatistics.medianGodClassWithVariables;
        maintenanceChange.maintenanceStatistics.medianGodClassWithMethods = target.maintenance.maintenanceStatistics.medianGodClassWithMethods - base.maintenance.maintenanceStatistics.medianGodClassWithMethods;

        maintenanceChange.maintenanceStatistics.superMethodsCount = target.maintenance.maintenanceStatistics.superMethodsCount - base.maintenance.maintenanceStatistics.superMethodsCount;
        maintenanceChange.maintenanceStatistics.maxSuperMethodWithParameters = target.maintenance.maintenanceStatistics.maxSuperMethodWithParameters - base.maintenance.maintenanceStatistics.maxSuperMethodWithParameters;
        maintenanceChange.maintenanceStatistics.maxSuperMethodWithLines = target.maintenance.maintenanceStatistics.maxSuperMethodWithLines - base.maintenance.maintenanceStatistics.maxSuperMethodWithLines;
        maintenanceChange.maintenanceStatistics.medianSuperMethodWithParameters = target.maintenance.maintenanceStatistics.medianSuperMethodWithParameters - base.maintenance.maintenanceStatistics.medianSuperMethodWithParameters;
        maintenanceChange.maintenanceStatistics.medianSuperMethodWithLines = target.maintenance.maintenanceStatistics.medianSuperMethodWithLines - base.maintenance.maintenanceStatistics.medianSuperMethodWithLines;

        maintenanceChange.maintenanceStatistics.godCommentsCount = target.maintenance.maintenanceStatistics.godCommentsCount - base.maintenance.maintenanceStatistics.godCommentsCount;
        maintenanceChange.maintenanceStatistics.maxGodComment = target.maintenance.maintenanceStatistics.maxGodComment - base.maintenance.maintenanceStatistics.maxGodComment;
        maintenanceChange.maintenanceStatistics.medianGodComment = target.maintenance.maintenanceStatistics.medianGodComment - base.maintenance.maintenanceStatistics.medianGodComment;

        maintenanceChange.maintenanceStatistics.superCyclomaticsCount = target.maintenance.maintenanceStatistics.superCyclomaticsCount - base.maintenance.maintenanceStatistics.superCyclomaticsCount;
        maintenanceChange.maintenanceStatistics.maxCyclomatic = target.maintenance.maintenanceStatistics.maxCyclomatic - base.maintenance.maintenanceStatistics.maxCyclomatic;
        maintenanceChange.maintenanceStatistics.medianCyclomatic = target.maintenance.maintenanceStatistics.medianCyclomatic - base.maintenance.maintenanceStatistics.medianCyclomatic;

        maintenanceChange.maintenanceStatistics.superDuplicationsCount = target.maintenance.maintenanceStatistics.superDuplicationsCount - base.maintenance.maintenanceStatistics.superDuplicationsCount;
        maintenanceChange.maintenanceStatistics.maxDuplication = target.maintenance.maintenanceStatistics.maxDuplication - base.maintenance.maintenanceStatistics.maxDuplication;
        maintenanceChange.maintenanceStatistics.medianDuplication = target.maintenance.maintenanceStatistics.medianDuplication - base.maintenance.maintenanceStatistics.medianDuplication;

    }

    private void diffRegulationChange(){
        regulationChange.regulationStatistics.commentsCount = target.regulation.regulationStatistics.commentsCount - base.regulation.regulationStatistics.commentsCount;
        regulationChange.regulationStatistics.constantsCount = target.regulation.regulationStatistics.constantsCount - base.regulation.regulationStatistics.constantsCount;
        regulationChange.regulationStatistics.exceptionsCount = target.regulation.regulationStatistics.exceptionsCount - base.regulation.regulationStatistics.exceptionsCount;
        regulationChange.regulationStatistics.flowControlsCount = target.regulation.regulationStatistics.flowControlsCount - base.regulation.regulationStatistics.flowControlsCount;
        regulationChange.regulationStatistics.namingsCount = target.regulation.regulationStatistics.namingsCount - base.regulation.regulationStatistics.namingsCount;
        regulationChange.regulationStatistics.oopsCount = target.regulation.regulationStatistics.oopsCount - base.regulation.regulationStatistics.oopsCount;
        regulationChange.regulationStatistics.setsCount = target.regulation.regulationStatistics.setsCount - base.regulation.regulationStatistics.setsCount;
        regulationChange.regulationStatistics.othersCount = target.regulation.regulationStatistics.othersCount - base.regulation.regulationStatistics.othersCount;
    }

    private void diffDesignChange(){
        designChange.designStatistics.designsCount = target.design.designStatistics.designsCount - base.design.designStatistics.designsCount;
        designChange.designStatistics.multithreadingsCount = target.design.designStatistics.multithreadingsCount - base.design.designStatistics.multithreadingsCount;
        designChange.designStatistics.performancesCount = target.design.designStatistics.performancesCount - base.design.designStatistics.performancesCount;
    }

    //Maintenance, regulation, design only increase data
    private void diffMaintenanceOnlyIncrease() {
        this.maintenanceOnlyIncrease.cyclomaticOriginData = diffListOnlyInSecond(base.maintenance.cyclomaticOriginData, target.maintenance.cyclomaticOriginData);
        this.maintenanceOnlyIncrease.duplicationOriginData = diffListOnlyInSecond(base.maintenance.duplicationOriginData, target.maintenance.duplicationOriginData);
        //if sum change
        this.maintenanceOnlyIncrease.superDuplications = diffSumValueOnlyIncrease(base.maintenance.superDuplications, target.maintenance.superDuplications);

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

        List<String> diff = new ArrayList<>(target);
        for(String ds : diff){
            for(String bs : base){
                if( getRidOfLineNumber(ds).equals(getRidOfLineNumber(bs))){
                    diff.remove(ds);
                    break;
                }
            }
        }

        return diff;
    }


    public static Map<String, Integer> diffMapOnlyIncreaseInSecondMap(Map<String, Integer> base, Map<String, Integer> target) {
        if(target == null){
            return new HashMap<>();
        }

        Map<String, Integer> diffMap = new HashMap<>(target);
        for (Iterator<Map.Entry<String, Integer>> it = diffMap.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, Integer> entryDiff = it.next();
            for(Map.Entry<String, Integer> entryBase : base.entrySet()){
                if(getRidOfLineNumber(entryBase.getKey()).equals(getRidOfLineNumber(entryDiff.getKey()))){
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
            String pathStr = generateReportPathStr(name, "diff--summary", reportDir);

            PrintWriter printWriter = getPrintWriter(pathStr);
            printWriter.println(this);
            printWriter.close();
        }
    }
}
