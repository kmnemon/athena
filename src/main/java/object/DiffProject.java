package object;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import techdebt.*;

import java.io.PrintWriter;
import java.util.*;

import static object.Project.getPrintWriter;
import static pmd.Tools.generateReportPathStr;

public class DiffProject {
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

    public void diffTechDebtObjectStatistics() {
        this.diffMaintenance();
        this.diffRegulation();
        this.diffDesign();

        this.diffMaintenanceStatistics();
        this.diffRegulationStatistics();
        this.diffDesignStatistics();
    }


    //Maintenance, regulation, design only increase data
    private void diffMaintenance() {
        this.maintenance.cyclomaticOriginData = diffListOnlyInSecond(base.maintenance.cyclomaticOriginData, target.maintenance.cyclomaticOriginData);
        this.maintenance.duplicationOriginData = diffListOnlyInSecond(base.maintenance.duplicationOriginData, target.maintenance.duplicationOriginData);
        //if sum change
        this.maintenance.superDuplications = diffSumValue(base.maintenance.superDuplications, target.maintenance.superDuplications);

        this.maintenance.godClassWithMethods = diffMapOnlyIncreaseInSecondMap(base.maintenance.godClassWithMethods, target.maintenance.godClassWithMethods);
        this.maintenance.godClassWithVariables = diffMapOnlyIncreaseInSecondMap(base.maintenance.godClassWithVariables, target.maintenance.godClassWithVariables);
        this.maintenance.godComments = diffMapOnlyIncreaseInSecondMap(base.maintenance.godComments, target.maintenance.godComments);
        this.maintenance.superMethodWithParameters = diffMapOnlyIncreaseInSecondMap(base.maintenance.superMethodWithParameters, target.maintenance.superMethodWithParameters);
        this.maintenance.superMethodWithLines = diffMapOnlyIncreaseInSecondMap(base.maintenance.superMethodWithLines, target.maintenance.superMethodWithLines);
        this.maintenance.superCyclomatics = diffMapOnlyIncreaseInSecondMap(base.maintenance.superCyclomatics, target.maintenance.superCyclomatics);
    }

    private void diffRegulation() {
        this.regulation.commentOriginData = diffListOnlyInSecond(base.regulation.commentOriginData, target.regulation.commentOriginData);
        this.regulation.constantOriginData = diffListOnlyInSecond(base.regulation.constantOriginData, target.regulation.constantOriginData);
        this.regulation.exceptionOriginData = diffListOnlyInSecond(base.regulation.exceptionOriginData, target.regulation.exceptionOriginData);
        this.regulation.flowControlOriginData = diffListOnlyInSecond(base.regulation.flowControlOriginData, target.regulation.flowControlOriginData);
        this.regulation.namingOriginData = diffListOnlyInSecond(base.regulation.namingOriginData, target.regulation.namingOriginData);
        this.regulation.oopOriginData = diffListOnlyInSecond(base.regulation.oopOriginData, target.regulation.oopOriginData);
        this.regulation.setOriginData = diffListOnlyInSecond(base.regulation.setOriginData, target.regulation.setOriginData);
        this.regulation.otherOriginData = diffListOnlyInSecond(base.regulation.otherOriginData, target.regulation.otherOriginData);
    }

    private void diffDesign() {
        this.design.designOriginData = diffListOnlyInSecond(base.design.designOriginData, target.design.designOriginData);
        this.design.multithreadingOriginData = diffListOnlyInSecond(base.design.multithreadingOriginData, target.design.multithreadingOriginData);
        this.design.performanceOriginData = diffListOnlyInSecond(base.design.performanceOriginData, target.design.performanceOriginData);
    }

    public static <T> List<T> diffListOnlyInSecond(List<T> base, List<T> target) {
        if (target == null){
            return new ArrayList<>();
        }

        List<T> diff = new ArrayList<>(target);
        diff.removeAll(base);
        return diff;
    }


    private static Map<String, Integer> diffMapOnlyIncreaseInSecondMap(Map<String, Integer> base, Map<String, Integer> target) {
        if(target == null){
            return new HashMap<>();
        }

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

    private static List<Integer> diffSumValue(List<Integer> base, List<Integer> target){
        return List.of(target.stream().mapToInt(Integer::intValue).sum() - base.stream().mapToInt(Integer::intValue).sum());

    }

    //diffPrint contain only changes
    private void diffMaintenanceStatistics() {
        this.maintenance.generateMaintenanceStatistics();
    }

    private void diffRegulationStatistics() {
        this.regulation.generateRegulationStatistics();
    }

    private void diffDesignStatistics() {
        this.design.generateDesignStatistics();
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
