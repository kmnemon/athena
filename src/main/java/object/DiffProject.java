package object;

import techdebt.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiffProject {
    public String name;
    Project base;
    Project target;

    public Maintenance maintenance;
    public Regulation regulation;
    public Design design;

    public DiffProject(String name, Project base, Project target) {
        this.name = name;
        this.base = base;
        this.target = target;

//        this.maintenance = new Maintenance();
        this.regulation = new Regulation();
        this.design = new Design();
    }

    public void diffTechDebtObjectStatistics() {
//        diffMaintenance();
//        diffRegulation();
//        diffDesign();

        this.diffMaintenanceStatistics();
        this.diffRegulationStatistics();
        this.diffDesignStatistics();
    }


    //Maintenance, regulation, design only increase data
    private void diffMaintenance() {
        this.maintenance.cyclomaticOriginData = diffListOnlyInSecond(base.maintenance.cyclomaticOriginData, target.maintenance.cyclomaticOriginData);
        //duplicationOriginData diff value useless
        this.maintenance.superDuplications = diffListOnlyInSecond(base.maintenance.superDuplications, target.maintenance.superDuplications);
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
        List<T> diff = new ArrayList<>(target);
        diff.removeAll(base);
        return diff;
    }


    private static Map<String, Integer> diffMapOnlyIncreaseInSecondMap(Map<String, Integer> base, Map<String, Integer> target) {
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

    //diffPrint contain increase and decrease
    private void diffMaintenanceStatistics() {
        this.maintenance.maintenanceStatistics.godClassesCount = target.maintenance.maintenanceStatistics.godClassesCount - base.maintenance.maintenanceStatistics.godClassesCount;
        this.maintenance.maintenanceStatistics.maxGodClassWithVariables = target.maintenance.maintenanceStatistics.maxGodClassWithVariables - base.maintenance.maintenanceStatistics.maxGodClassWithVariables;
        this.maintenance.maintenanceStatistics.maxGodClassWithMethods = target.maintenance.maintenanceStatistics.maxGodClassWithMethods - base.maintenance.maintenanceStatistics.maxGodClassWithMethods;
        this.maintenance.maintenanceStatistics.medianGodClassWithVariables = target.maintenance.maintenanceStatistics.medianGodClassWithVariables - base.maintenance.maintenanceStatistics.medianGodClassWithVariables;
        this.maintenance.maintenanceStatistics.medianGodClassWithMethods = target.maintenance.maintenanceStatistics.medianGodClassWithMethods -base.maintenance.maintenanceStatistics.medianGodClassWithMethods;

        this.maintenance.maintenanceStatistics.superMethodsCount = target.maintenance.maintenanceStatistics.superMethodsCount - base.maintenance.maintenanceStatistics.superMethodsCount;
        this.maintenance.maintenanceStatistics.maxSuperMethodWithParameters = target.maintenance.maintenanceStatistics.maxSuperMethodWithParameters - base.maintenance.maintenanceStatistics.maxSuperMethodWithParameters;
        this.maintenance.maintenanceStatistics.maxSuperMethodWithLines = target.maintenance.maintenanceStatistics.maxSuperMethodWithLines - base.maintenance.maintenanceStatistics.maxSuperMethodWithLines;
        this.maintenance.maintenanceStatistics.medianSuperMethodWithParameters = target.maintenance.maintenanceStatistics.medianSuperMethodWithParameters - base.maintenance.maintenanceStatistics.medianSuperMethodWithParameters;
        this.maintenance.maintenanceStatistics.medianSuperMethodWithLines = target.maintenance.maintenanceStatistics.medianSuperMethodWithLines - base.maintenance.maintenanceStatistics.medianSuperMethodWithLines;

        this.maintenance.maintenanceStatistics.godCommentsCount = target.maintenance.maintenanceStatistics.godCommentsCount - base.maintenance.maintenanceStatistics.godCommentsCount;
        this.maintenance.maintenanceStatistics.maxGodComment = target.maintenance.maintenanceStatistics.maxGodComment - base.maintenance.maintenanceStatistics.maxGodComment;
        this.maintenance.maintenanceStatistics.medianGodComment = target.maintenance.maintenanceStatistics.medianGodComment - base.maintenance.maintenanceStatistics.medianGodComment;

        this.maintenance.maintenanceStatistics.superCyclomaticsCount = target.maintenance.maintenanceStatistics.superCyclomaticsCount - base.maintenance.maintenanceStatistics.superCyclomaticsCount;
        this.maintenance.maintenanceStatistics.maxCyclomatic = target.maintenance.maintenanceStatistics.maxCyclomatic - base.maintenance.maintenanceStatistics.maxCyclomatic;
        this.maintenance.maintenanceStatistics.medianCyclomatic = target.maintenance.maintenanceStatistics.medianCyclomatic - base.maintenance.maintenanceStatistics.medianCyclomatic;

        this.maintenance.maintenanceStatistics.superDuplicationsCount = target.maintenance.maintenanceStatistics.superDuplicationsCount - base.maintenance.maintenanceStatistics.superDuplicationsCount;
        this.maintenance.maintenanceStatistics.maxDuplication = target.maintenance.maintenanceStatistics.maxDuplication - base.maintenance.maintenanceStatistics.maxDuplication;
        this.maintenance.maintenanceStatistics.medianDuplication = target.maintenance.maintenanceStatistics.medianDuplication - base.maintenance.maintenanceStatistics.medianDuplication;
    }

    private void diffRegulationStatistics() {
        this.regulation.regulationStatistics.commentsCount = target.regulation.regulationStatistics.commentsCount - base.regulation.regulationStatistics.commentsCount;
        this.regulation.regulationStatistics.constantsCount = target.regulation.regulationStatistics.constantsCount - base.regulation.regulationStatistics.constantsCount;
        this.regulation.regulationStatistics.exceptionsCount = target.regulation.regulationStatistics.exceptionsCount - base.regulation.regulationStatistics.exceptionsCount;
        this.regulation.regulationStatistics.flowControlsCount = target.regulation.regulationStatistics.flowControlsCount - base.regulation.regulationStatistics.flowControlsCount;
        this.regulation.regulationStatistics.namingsCount = target.regulation.regulationStatistics.namingsCount - base.regulation.regulationStatistics.namingsCount;
        this.regulation.regulationStatistics.oopsCount = target.regulation.regulationStatistics.oopsCount - base.regulation.regulationStatistics.oopsCount;
        this.regulation.regulationStatistics.setsCount = target.regulation.regulationStatistics.setsCount - base.regulation.regulationStatistics.setsCount;
        this.regulation.regulationStatistics.othersCount = target.regulation.regulationStatistics.othersCount - base.regulation.regulationStatistics.othersCount;
    }

    private void diffDesignStatistics() {
        this.design.designStatistics.designsCount = target.design.designStatistics.designsCount - base.design.designStatistics.designsCount;
        this.design.designStatistics.multithreadingsCount = target.design.designStatistics.multithreadingsCount - base.design.designStatistics.multithreadingsCount;
        this.design.designStatistics.performancesCount = target.design.designStatistics.performancesCount - base.design.designStatistics.performancesCount;
    }
}
