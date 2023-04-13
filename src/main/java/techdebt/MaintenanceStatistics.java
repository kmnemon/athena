package techdebt;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class MaintenanceStatistics {
    public int godClassesCount;
    public int maxGodClassWithVariables;
    public int maxGodClassWithMethods;
    public int medianGodClassWithVariables;
    public int medianGodClassWithMethods;


    public int superMethodsCount;
    public int maxSuperMethodWithParameters;
    public int maxSuperMethodWithLines;
    public int medianSuperMethodWithParameters;
    public int medianSuperMethodWithLines;


    public int godCommentsCount;
    public int maxGodComment;
    public int medianGodComment;

    public int superCyclomaticsCount;
    public int maxCyclomatic;
    public int medianCyclomatic;

    public int superDuplicationsCount;
    public int maxDuplication;
    public int medianDuplication;

    public MaintenanceStatistics(){}
    public MaintenanceStatistics(Maintenance m) {
        setGodClassesCount(m);
        setMaxGodClassWithVariables(m);
        setMaxGodClassWithMethods(m);
        setMedianGodClassWithVariables(m);
        setMedianGodClassWithMethods(m);

        setSuperMethodsCount(m);
        setMaxSuperMethodWithParameters(m);
        setMaxSuperMethodWithLines(m);
        setMedianSuperMethodWithParameters(m);
        setMedianSuperMethodWithLines(m);

        setGodCommentsCount(m);
        setMaxGodComment(m);
        setMedianGodComment(m);

        setSuperCyclomaticsCount(m);
        setMaxCyclomatic(m);
        setMedianCyclomatic(m);

        setSuperDuplicationsCount(m);
        setMaxDuplication(m);
        setMedianDuplication(m);
    }



    void setGodClassesCount(Maintenance m) {
        this.godClassesCount = m.godClassWithVariables.size() + m.godClassWithMethods.size();
    }

    void setMaxGodClassWithVariables(Maintenance m) {
        this.maxGodClassWithVariables = getMapMaxValue(m.godClassWithVariables);
    }

    void setMaxGodClassWithMethods(Maintenance m) {
        this.maxGodClassWithMethods = getMapMaxValue(m.godClassWithMethods);
    }

    void setMedianGodClassWithVariables(Maintenance m){
        this.medianGodClassWithVariables = getMedianValue(m.godClassWithVariables);
    }

    void setMedianGodClassWithMethods(Maintenance m){
        this.medianGodClassWithMethods = getMedianValue(m.godClassWithMethods);
    }



    void setSuperMethodsCount(Maintenance m) {
        this.superMethodsCount = m.superMethodWithParameters.size() + m.superMethodWithLines.size();
    }

    void setMaxSuperMethodWithParameters(Maintenance m) {
        this.maxSuperMethodWithParameters = getMapMaxValue(m.superMethodWithParameters);
    }

    void setMaxSuperMethodWithLines(Maintenance m) {
        this.maxSuperMethodWithLines = getMapMaxValue(m.superMethodWithLines);
    }

    void setMedianSuperMethodWithParameters(Maintenance m){
        this.medianSuperMethodWithParameters = getMedianValue(m.superMethodWithParameters);
    }

    void setMedianSuperMethodWithLines(Maintenance m){
        this.medianSuperMethodWithLines = getMedianValue(m.superMethodWithLines);
    }


    void setGodCommentsCount(Maintenance m) {
        this.godCommentsCount = m.godComments.size();
    }

    void setMaxGodComment(Maintenance m) {
        this.maxGodComment = getMapMaxValue(m.godComments);
    }

    void setMedianGodComment(Maintenance m){ this.medianGodComment = getMedianValue(m.godComments);}



    void setSuperCyclomaticsCount(Maintenance m) {
        this.superCyclomaticsCount = m.superCyclomatics.size();
    }

    void setMaxCyclomatic(Maintenance m) {
        this.maxCyclomatic = getMapMaxValue(m.superCyclomatics);
    }

    void setMedianCyclomatic(Maintenance m){ this.medianCyclomatic = getMedianValue(m.superCyclomatics); }


    void setSuperDuplicationsCount(Maintenance m) {
        this.superDuplicationsCount = m.superDuplications.size();
    }

    void setMaxDuplication(Maintenance m) {
        if(!m.superDuplications.isEmpty()) {
            this.maxDuplication = Collections.max(m.superDuplications);
        }
    }

    void setMedianDuplication(Maintenance m){
        if(!m.superDuplications.isEmpty()) {
            Collections.sort(m.superDuplications);
            this.medianDuplication = m.superDuplications.get(m.superDuplications.size()/2);
        }
    }

    public static int getMapMaxValue(Map<String, Integer> m) {
        int value = 0;
        var ov = m.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1);
        if( ov.isPresent()){
            value = ov.get().getValue();
        }
        return value;
    }

    public static int getMedianValue(Map<String, Integer> m){
        if( m.isEmpty()){
            return 0;
        }

        List<Integer> list = new ArrayList<>(m.values());
        Collections.sort(list);
        return list.get(list.size() / 2);
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
