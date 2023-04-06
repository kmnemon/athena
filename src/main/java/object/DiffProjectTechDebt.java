package object;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DiffProjectTechDebt {
    public static Project diffProjectTechDebt(Project base, Project target){
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
