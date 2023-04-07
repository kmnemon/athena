package techdebt;


import com.google.gson.Gson;
import java.util.Collections;



public class PrintMaintenance {
    public int godClassesCount;
    public int maxGodClassWithVariable;
    public int maxGodClassWithMethods;

    public int superMethodsCount;
    public int maxSuperMethodWithParameters;
    public int maxSuperMethodWithLines;

    public int goCommentsCount;
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
        setGoCommentsCount(m);
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
        this.maxGodClassWithVariable = m.godClassWithVariables.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    void setMaxGodClassWithMethods(Maintenance m) {
        this.maxGodClassWithMethods = m.godClassWithMethods.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    void setSuperMethodsCount(Maintenance m) {
        this.superMethodsCount = m.superMethodWithParameters.size() + m.superMethodWithLines.size();
    }

    void setMaxSuperMethodWithParameters(Maintenance m) {
        this.maxSuperMethodWithParameters = m.superMethodWithParameters.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    void setMaxSuperMethodWithLines(Maintenance m) {
        this.maxSuperMethodWithLines = m.superMethodWithLines.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    void setGoCommentsCount(Maintenance m) {
        this.goCommentsCount = m.godComments.size();
    }

    void setMaxGodComment(Maintenance m) {
        this.maxGodComment = m.godComments.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    void setSuperCyclomaticsCount(Maintenance m) {
        this.superCyclomaticsCount = m.superCyclomatics.size();
    }

    public void setMaxCyclomatic(Maintenance m) {
        this.maxCyclomatic = m.superCyclomatics.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    void setSuperDuplicationsCount(Maintenance m) {
        this.superDuplicationsCount = m.superDuplications.size();
    }

    void setMaxDuplication(Maintenance m) {
        this.maxDuplication = Collections.max(m.superDuplications);
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
