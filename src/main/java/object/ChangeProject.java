package object;

import com.google.gson.GsonBuilder;

import java.util.Map;

public class ChangeProject extends DiffProject{

    public ChangeProject(String name, Project base, Project target, String reportDir) {
        super(name, base, target, reportDir);
    }

    public void diffTechDebtObjectChange(Map<String, Boolean> rules){
        if(rules.get("maintenance")) {
            this.diffMaintenanceStatisticsChange();
        }

        if(rules.get("regulation")) {
            this.diffRegulationStatisticsChange();
        }

        if(rules.get("design")) {
            this.diffDesignStatisticsChange();
        }
    }

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    private void diffMaintenanceStatisticsChange(){
        maintenance.maintenanceStatistics.godClassesCount = target.maintenance.maintenanceStatistics.godClassesCount - base.maintenance.maintenanceStatistics.godClassesCount;
        maintenance.maintenanceStatistics.maxGodClassWithVariables = target.maintenance.maintenanceStatistics.maxGodClassWithVariables - base.maintenance.maintenanceStatistics.maxGodClassWithVariables;
        maintenance.maintenanceStatistics.maxGodClassWithMethods = target.maintenance.maintenanceStatistics.maxGodClassWithMethods - base.maintenance.maintenanceStatistics.maxGodClassWithMethods;
        maintenance.maintenanceStatistics.medianGodClassWithVariables = target.maintenance.maintenanceStatistics.medianGodClassWithVariables - base.maintenance.maintenanceStatistics.medianGodClassWithVariables;
        maintenance.maintenanceStatistics.medianGodClassWithMethods = target.maintenance.maintenanceStatistics.medianGodClassWithMethods - base.maintenance.maintenanceStatistics.medianGodClassWithMethods;

        maintenance.maintenanceStatistics.superMethodsCount = target.maintenance.maintenanceStatistics.superMethodsCount - base.maintenance.maintenanceStatistics.superMethodsCount;
        maintenance.maintenanceStatistics.maxSuperMethodWithParameters = target.maintenance.maintenanceStatistics.maxSuperMethodWithParameters - base.maintenance.maintenanceStatistics.maxSuperMethodWithParameters;
        maintenance.maintenanceStatistics.maxSuperMethodWithLines = target.maintenance.maintenanceStatistics.maxSuperMethodWithLines - base.maintenance.maintenanceStatistics.maxSuperMethodWithLines;
        maintenance.maintenanceStatistics.medianSuperMethodWithParameters = target.maintenance.maintenanceStatistics.medianSuperMethodWithParameters - base.maintenance.maintenanceStatistics.medianSuperMethodWithParameters;
        maintenance.maintenanceStatistics.medianSuperMethodWithLines = target.maintenance.maintenanceStatistics.medianSuperMethodWithLines - base.maintenance.maintenanceStatistics.medianSuperMethodWithLines;

        maintenance.maintenanceStatistics.godCommentsCount = target.maintenance.maintenanceStatistics.godCommentsCount - base.maintenance.maintenanceStatistics.godCommentsCount;
        maintenance.maintenanceStatistics.maxGodComment = target.maintenance.maintenanceStatistics.maxGodComment - base.maintenance.maintenanceStatistics.maxGodComment;
        maintenance.maintenanceStatistics.medianGodComment = target.maintenance.maintenanceStatistics.medianGodComment - base.maintenance.maintenanceStatistics.medianGodComment;

        maintenance.maintenanceStatistics.superCyclomaticsCount = target.maintenance.maintenanceStatistics.superCyclomaticsCount - base.maintenance.maintenanceStatistics.superCyclomaticsCount;
        maintenance.maintenanceStatistics.maxCyclomatic = target.maintenance.maintenanceStatistics.maxCyclomatic - base.maintenance.maintenanceStatistics.maxCyclomatic;
        maintenance.maintenanceStatistics.medianCyclomatic = target.maintenance.maintenanceStatistics.medianCyclomatic - base.maintenance.maintenanceStatistics.medianCyclomatic;

        maintenance.maintenanceStatistics.superDuplicationsCount = target.maintenance.maintenanceStatistics.superDuplicationsCount - base.maintenance.maintenanceStatistics.superDuplicationsCount;
        maintenance.maintenanceStatistics.maxDuplication = target.maintenance.maintenanceStatistics.maxDuplication - base.maintenance.maintenanceStatistics.maxDuplication;
        maintenance.maintenanceStatistics.medianDuplication = target.maintenance.maintenanceStatistics.medianDuplication - base.maintenance.maintenanceStatistics.medianDuplication;

    }

    private void diffRegulationStatisticsChange(){
        regulation.regulationStatistics.commentsCount = target.regulation.regulationStatistics.commentsCount - base.regulation.regulationStatistics.commentsCount;
        regulation.regulationStatistics.constantsCount = target.regulation.regulationStatistics.constantsCount - base.regulation.regulationStatistics.constantsCount;
        regulation.regulationStatistics.exceptionsCount = target.regulation.regulationStatistics.exceptionsCount - base.regulation.regulationStatistics.exceptionsCount;
        regulation.regulationStatistics.flowControlsCount = target.regulation.regulationStatistics.flowControlsCount - base.regulation.regulationStatistics.flowControlsCount;
        regulation.regulationStatistics.namingsCount = target.regulation.regulationStatistics.namingsCount - base.regulation.regulationStatistics.namingsCount;
        regulation.regulationStatistics.oopsCount = target.regulation.regulationStatistics.oopsCount - base.regulation.regulationStatistics.oopsCount;
        regulation.regulationStatistics.setsCount = target.regulation.regulationStatistics.setsCount - base.regulation.regulationStatistics.setsCount;
        regulation.regulationStatistics.othersCount = target.regulation.regulationStatistics.othersCount - base.regulation.regulationStatistics.othersCount;
    }

    private void diffDesignStatisticsChange(){
        design.designStatistics.designsCount = target.design.designStatistics.designsCount - base.design.designStatistics.designsCount;
        design.designStatistics.multithreadingsCount = target.design.designStatistics.multithreadingsCount - base.design.designStatistics.multithreadingsCount;
        design.designStatistics.performancesCount = target.design.designStatistics.performancesCount - base.design.designStatistics.performancesCount;
    }
}
