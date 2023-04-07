package techdebt;


import com.google.gson.Gson;
import java.util.Collections;



public class PrintMaintenance {
    private Maintenance m;

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

    public PrintMaintenance(Maintenance m) {
        this.m = m;
        setGodClassesCount();
        setMaxGodClassWithVariable();
        setMaxGodClassWithMethods();
        setSuperMethodsCount();
        setMaxSuperMethodWithParameters();
        setMaxSuperMethodWithLines();
        setGoCommentsCount();
        setMaxGodComment();
        setSuperCyclomaticsCount();
        setMaxCyclomatic();
        setSuperDuplicationsCount();
        setMaxDuplication();
    }



    public void setGodClassesCount() {
        this.godClassesCount = m.godClassWithVariables.size() + m.godClassWithMethods.size();
    }

    public void setMaxGodClassWithVariable() {
        this.maxGodClassWithVariable = m.godClassWithVariables.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    public void setMaxGodClassWithMethods() {
        this.maxGodClassWithMethods = m.godClassWithMethods.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    public void setSuperMethodsCount() {
        this.superMethodsCount = m.superMethodWithParameters.size() + m.superMethodWithLines.size();
    }

    public void setMaxSuperMethodWithParameters() {
        this.maxSuperMethodWithParameters = m.superMethodWithParameters.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    public void setMaxSuperMethodWithLines() {
        this.maxSuperMethodWithLines = m.superMethodWithLines.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    public void setGoCommentsCount() {
        this.goCommentsCount = m.godComments.size();
    }

    public void setMaxGodComment() {
        this.maxGodComment = m.godComments.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    public void setSuperCyclomaticsCount() {
        this.superCyclomaticsCount = m.superCyclomatics.size();
    }

    public void setMaxCyclomatic() {
        this.maxCyclomatic = m.superCyclomatics.entrySet().stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
    }

    public void setSuperDuplicationsCount() {
        this.superDuplicationsCount = m.superDuplications.size();
    }

    public void setMaxDuplication() {
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
