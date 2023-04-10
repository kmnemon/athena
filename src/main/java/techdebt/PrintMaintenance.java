package techdebt;


import com.google.gson.Gson;
import java.util.Collections;
import java.util.Map;


public class PrintMaintenance {
    public int godClassesCount;
    public int maxGodClassWithVariable;
    public int maxGodClassWithMethods;

    public int superMethodsCount;
    public int maxSuperMethodWithParameters;
    public int maxSuperMethodWithLines;

    public int godCommentsCount;
    public int maxGodComment;

    public int superCyclomaticsCount;
    public int maxCyclomatic;

    public int superDuplicationsCount;
    public int maxDuplication;

    public PrintMaintenance(){}

    public PrintMaintenance(Maintenance m) {
        setGodClassesCount(m);
        setMaxGodClassWithVariable(m);
        setMaxGodClassWithMethods(m);
        setSuperMethodsCount(m);
        setMaxSuperMethodWithParameters(m);
        setMaxSuperMethodWithLines(m);
        setGodCommentsCount(m);
        setMaxGodComment(m);
        setSuperCyclomaticsCount(m);
        setMaxCyclomatic(m);
        setSuperDuplicationsCount(m);
        setMaxDuplication(m);
    }



    void setGodClassesCount(Maintenance m) {
        this.godClassesCount = m.godClassWithVariables.size() + m.godClassWithMethods.size();
    }

    void setMaxGodClassWithVariable(Maintenance m) {
        this.maxGodClassWithVariable = getMapMaxValue(m.godClassWithVariables);
    }

    void setMaxGodClassWithMethods(Maintenance m) {
        this.maxGodClassWithMethods = getMapMaxValue(m.godClassWithMethods);
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

    void setGodCommentsCount(Maintenance m) {
        this.godCommentsCount = m.godComments.size();
    }

    void setMaxGodComment(Maintenance m) {
        this.maxGodComment = getMapMaxValue(m.godComments);
    }

    void setSuperCyclomaticsCount(Maintenance m) {
        this.superCyclomaticsCount = m.superCyclomatics.size();
    }

    public void setMaxCyclomatic(Maintenance m) {
        this.maxCyclomatic = getMapMaxValue(m.superCyclomatics);
    }

    void setSuperDuplicationsCount(Maintenance m) {
        this.superDuplicationsCount = m.superDuplications.size();
    }

    void setMaxDuplication(Maintenance m) {
        if(!m.superDuplications.isEmpty()) {
            this.maxDuplication = Collections.max(m.superDuplications);
        }
    }

    int getMapMaxValue(Map<String, Integer> m) {
        int value = 0;
        var ov = m.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1);
        if( ov.isPresent()){
            value = ov.get().getValue();
        }
        return value;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }

    public static void main(String[] args) {
//        Maintenance m = new Maintenance(new Project("df"));
//        PrintMaintenance mobilePhone = new PrintMaintenance(m);
//        System.out.println(new Gson().toJson(mobilePhone));

    }
}
