package object;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class LimitProject extends DiffProject{
    public LimitProject(String name, Project base, Project target, String reportDir) {
        super(name, base, target, reportDir);
    }

    public void diffTechDebtObjectLimit() {
        this.diffMaintenanceLimit();
        this.diffRegulationLimit();
        this.diffDesignLimit();

        this.diffMaintenanceStatistics();
        this.diffRegulationStatistics();
        this.diffDesignStatistics();
    }

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    //Maintenance, regulation, design only increase data
    private void diffMaintenanceLimit() {
        this.maintenance.cyclomaticOriginData = diffListOnlyInSecondAndFilterLineNumber(base.maintenance.cyclomaticOriginData, target.maintenance.cyclomaticOriginData);

        this.maintenance.superDuplications = new ArrayList<>();

        this.maintenance.godClassWithMethods = diffMapOnlyIncreaseInSecondMap(base.maintenance.godClassWithMethods, target.maintenance.godClassWithMethods);
        this.maintenance.godClassWithVariables = diffMapOnlyIncreaseInSecondMap(base.maintenance.godClassWithVariables, target.maintenance.godClassWithVariables);
        this.maintenance.godComments = diffMapOnlyIncreaseInSecondMap(base.maintenance.godComments, target.maintenance.godComments);
        this.maintenance.superMethodWithParameters = diffMapOnlyIncreaseInSecondMap(base.maintenance.superMethodWithParameters, target.maintenance.superMethodWithParameters);
        this.maintenance.superMethodWithLines = diffMapOnlyIncreaseInSecondMap(base.maintenance.superMethodWithLines, target.maintenance.superMethodWithLines);
        this.maintenance.superCyclomatics = diffMapOnlyIncreaseInSecondMap(base.maintenance.superCyclomatics, target.maintenance.superCyclomatics);
    }

    private void diffRegulationLimit() {
        this.regulation.commentOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.commentOriginData, target.regulation.commentOriginData);
        this.regulation.constantOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.constantOriginData, target.regulation.constantOriginData);
        this.regulation.exceptionOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.exceptionOriginData, target.regulation.exceptionOriginData);
        this.regulation.flowControlOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.flowControlOriginData, target.regulation.flowControlOriginData);
        this.regulation.namingOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.namingOriginData, target.regulation.namingOriginData);
        this.regulation.oopOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.oopOriginData, target.regulation.oopOriginData);
        this.regulation.setOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.setOriginData, target.regulation.setOriginData);
        this.regulation.otherOriginData = diffListOnlyInSecondAndFilterLineNumber(base.regulation.otherOriginData, target.regulation.otherOriginData);
    }

    private void diffDesignLimit() {
        this.design.designOriginData = diffListOnlyInSecondAndFilterLineNumber(base.design.designOriginData, target.design.designOriginData);
        this.design.multithreadingOriginData = diffListOnlyInSecondAndFilterLineNumber(base.design.multithreadingOriginData, target.design.multithreadingOriginData);
        this.design.performanceOriginData = diffListOnlyInSecondAndFilterLineNumber(base.design.performanceOriginData, target.design.performanceOriginData);
    }

    private void diffMaintenanceStatistics() {
        this.maintenance.generateMaintenanceStatistics();

        this.maintenance.maintenanceStatistics.maxGodClassWithVariables = -999;
        this.maintenance.maintenanceStatistics.maxGodClassWithMethods = -999;
        this.maintenance.maintenanceStatistics.medianGodClassWithVariables = -999;
        this.maintenance.maintenanceStatistics.medianGodClassWithMethods = -999;

        this.maintenance.maintenanceStatistics.maxSuperMethodWithParameters = -999;
        this.maintenance.maintenanceStatistics.maxSuperMethodWithLines = -999;
        this.maintenance.maintenanceStatistics.medianSuperMethodWithParameters = -999;
        this.maintenance.maintenanceStatistics.medianSuperMethodWithLines = -999;

        this.maintenance.maintenanceStatistics.maxGodComment = -999;
        this.maintenance.maintenanceStatistics.medianGodComment = -999;

        this.maintenance.maintenanceStatistics.maxCyclomatic = -999;
        this.maintenance.maintenanceStatistics.medianCyclomatic = -999;

        this.maintenance.maintenanceStatistics.superDuplicationsCount = target.maintenance.maintenanceStatistics.superDuplicationsCount - base.maintenance.maintenanceStatistics.superDuplicationsCount;
        this.maintenance.maintenanceStatistics.maxDuplication = -999;
        this.maintenance.maintenanceStatistics.medianDuplication = -999;
    }

    private void diffRegulationStatistics() {
        this.regulation.generateRegulationStatistics();
    }

    private void diffDesignStatistics() {
        this.design.generateDesignStatistics();
    }

}
