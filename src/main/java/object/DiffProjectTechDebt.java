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

    private static Map<String, Integer> diffMap(Map<String, Integer> b, Map<String, Integer> t){
       Map<String, Integer> diffMap = new HashMap<>();
       for( Map.Entry<String, Integer> entry : t.entrySet()){
           if( b.containsKey(entry.getKey())){
               Integer baseValue = b.get(entry.getKey());
               if(!Objects.equals(entry.getValue(), baseValue)) {
                   diffMap.put(entry.getKey(), entry.getValue() - baseValue);
               }
           }else {
               diffMap.put(entry.getKey(), entry.getValue());
           }
       }

       //target delete content
       for( Map.Entry<String, Integer> entry : b.entrySet()){
           if(!t.containsKey(entry.getKey())){
               diffMap.put(entry.getKey(), -entry.getValue());
           }
       }

       return diffMap;
   }
}
