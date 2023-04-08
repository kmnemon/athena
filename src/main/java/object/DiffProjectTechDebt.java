package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DiffProjectTechDebt {
    public static Project diffTechDebtObjects(Project base, Project target){
        Project diff = new Project("diff-"+base.name + ":" + target.name);
        diff.maintenance.printMaintenance = diffPrintMaintenance(base.maintenance.printMaintenance, target.maintenance.printMaintenance);
        diff.maintenance.regulation = diffPrintRegulation(base.maintenance.printRegulation, target.maintenance.printRegulation);
        diff.maintenance.printDesign = diffPrintDesign(base.maintenance.printDesign, target.maintenance.printDesign);

        return diff;
    }

    //Maintenance, regulation, design only increase data
    private static Maintenance diffMaintenance(Maintenance base, Maintenance target){
        Maintenance diffMt = new Maintenance();
        diffMt.cyclomaticOriginData = diffListOnlyInSecond(base.cyclomaticOriginData, target.cyclomaticOriginData);
        //duplicationOriginData diff value useless
        diffMt.superDuplications = diffListOnlyInSecond(base.superDuplications, target.superDuplications);
        diffMt.godClassWithMethods = diffMapOnlyIncreaseInSecondMap(base.godClassWithMethods, target.godClassWithMethods);
        diffMt.godClassWithVariables = diffMapOnlyIncreaseInSecondMap(base.godClassWithVariables, target.godClassWithVariables);
        diffMt.godComments = diffMapOnlyIncreaseInSecondMap(base.godComments, target.godComments);
        diffMt.superMethodWithParameters = diffMapOnlyIncreaseInSecondMap(base.superMethodWithParameters, target.superMethodWithParameters);
        diffMt.superMethodWithLines = diffMapOnlyIncreaseInSecondMap(base.superMethodWithLines, target.superMethodWithLines);
        diffMt.superCyclomatics = diffMapOnlyIncreaseInSecondMap(base.superCyclomatics, target.superCyclomatics);
        return diffMt;
    }

    private static Regulation diffRegulation(Regulation base, Regulation target){
        Regulation diffRe = new ArrayList();
        diffRe.commentOriginData = diffListOnlyInSecond(base.commentOriginData, target.commentOriginData);
        diffRe.concurrentOriginData = diffListOnlyInSecond(base.concurrentOriginData, target.concurrentOriginData);
        diffRe.constantOriginData = diffListOnlyInSecond(base.constantOriginData, target.constantOriginData);
        diffRe.exceptionOriginData = diffListOnlyInSecond(base.exceptionOriginData, target.exceptionOriginData);
        diffRe.flowControlOriginData = diffListOnlyInSecond(base.flowControlOriginData, target.flowControlOriginData);
        diffRe.namingOriginData = diffListOnlyInSecond(base.namingOriginData, target.namingOriginData);
        diffRe.oopOriginData = diffListOnlyInSecond(base.oopOriginData, target.oopOriginData);
        diffRe.ormOriginData = diffListOnlyInSecond(base.ormOriginData, target.ormOriginData);
        diffRe.otherOriginData = diffListOnlyInSecond(base.otherOriginData, target.otherOriginData);
        diffRe.setOriginData = diffListOnlyInSecond(base.setOriginData, target.setOriginData);
        return diffRe;
    }

    private static Design diffDesign(Design base, Design target){
        Design diffDesign = new Design();
        diffDesign.designOriginData = diffListOnlyInSecond(base.designOriginData, target.designOriginData);
        diffDesign.multithreadingOriginData = diffListOnlyInSecond(base.multithreadingOriginData, target.multithreadingOriginData);
        diffDesign.performanceOriginData = diffListOnlyInSecond(base.performanceOriginData, target.performanceOriginData);
        diffDesign.errorProneOriginData = diffListOnlyInSecond(base.errorProneOriginData, target.errorProneOriginData);
        return diffDesign;
    }

    public static diffListOnlyInSecond<T> (List<T>base, List<T> target){
        List<T> diff = new ArrayList(target);
        diff.removeAll(base);
        return diff;
    }

    private static Map<String, Integer> diffMapOnlyIncreaseInSecondMap(Map<String, Integer> base, Map<String, Integer> target){
       Map<String, Integer> diffMap = new HashMap<>();
       for( Map.Entry<String, Integer> entry : target.entrySet()){
           if( base.containsKey(entry.getKey())){
               Integer baseValue = base.get(entry.getKey());
               if(entry.getValue() > baseValue) {
                   diffMap.put(entry.getKey(), entry.getValue() - baseValue);
               }
           }else {
               diffMap.put(entry.getKey(), entry.getValue());
           }
       }

       return diffMap;
   }

    //diffPrint contain increase and decrease
    private static PrintMaintenance diffPrintMaintenance(PrintMaintenance base, PrintMaintenance target){
        PrintMaintenance diffPM = new PrintMaintenance();
        diffPM.godClassesCount = target.godClassesCount - base.godClassesCount;
        diffPM.maxGodClassWithVariable = target.maxGodClassWithVariable - base.maxGodClassWithVariable;
        diffPM.maxGodClassWithMethods = target.maxGodClassWithMethods - base.maxGodClassWithMethods;
        diffPM.superMethodsCount = target.superMethodsCount - base.superMethodsCount;
        diffPM.maxSuperMethodWithParameters = target.maxSuperMethodWithParameters - base.maxSuperMethodWithParameters;
        diffPM.maxSuperMethodWithLines = target.maxSuperMethodWithLines - base.maxSuperMethodWithLines;
        diffPM.goCommentsCount = target.goCommentsCount - base.goCommentsCount;
        diffPM.maxGodComment = target.maxGodComment - base.maxGodComment;
        diffPM.superCyclomaticsCount = target.superCyclomaticsCount= base.superCyclomaticsCount;
        diffPM.maxCyclomatic = target.maxCyclomatic - base.maxCyclomatic;
        diffPM.superDuplicationsCount = target.superDuplicationsCount - base.superDuplicationsCount;
        diffPM.maxDuplication = target.maxDuplication - base.maxDuplication;
    }

    private static PrintRegulation diffPrintRegulation(PrintRegulation base, PrintRegulation target){
        PrintRegulation diffPR = new PrintRegulation();
        diffPR.commentsCount = target.commentsCount - base.commentsCount;
        diffPR.concurrentsCount = target.concurrentsCount - base.concurrentsCount;
        diffPR.constantsCount = target.constantsCount - base.constantsCount;
        diffPR.exceptionsCount = target.exceptionsCount - base.exceptionsCount;
        diffPR.flowControlsCount = target.flowControlsCount - base.flowControlsCount;
        diffPR.namingsCount = target.namingsCount - base.namingsCount;
        diffPR.oopsCount = target.oopsCount - base.oopsCount;
        diffPR.ormsCount = target.ormsCount - base.ormsCount;
        diffPR.othersCount = target.othersCount - base.othersCount;
        diffPR.setsCount = target.setsCount - base.setsCount;
    }
    
    private static PrintDesign diffPrintDesign(PrintDesign base, PrintDesign target){
        PrintDesign diffDE = new PrintDesign();
        diffDE.designsCount = target.designsCount - base.designsCount;
        diffDE.multithreadingsCount = target.multithreadingsCount - base.multithreadingsCount;
        diffDE.performancesCount = target.performancesCount - base.performancesCount;
        diffDE.errorPronesCount = target.errorPronesCount - base.errorPronesCount;
    }

    public static Project diffProjectTechDebtDetails(Project base, Project target){
       Project diff = new Project("diff-"+base.name + ":" + target.name);
       diff.duplication = target.duplication - base.duplication;

       diff.godClassWithMethods = diffMap(base.godClassWithMethods, target.godClassWithMethods);
       diff.godClassWithVariables = diffMap(base.godClassWithVariables, target.godClassWithVariables);
       diff.godComments = diffMap(base.godComments, target.godComments);

       diff.superMethodWithParameters = diffMap(base.superMethodWithParameters, target.superMethodWithParameters);
       diff.superMethodWithLines = diffMap(base.superMethodWithLines, target.superMethodWithLines);
       diff.superCyclomatics = diffMap(base.superCyclomatics, target.superCyclomatics);
       return diff;
   }


}
