package object;

import techdebt.ClassDebt;

import java.util.HashMap;
import java.util.Map;

public class Class {
    public String name;
    public boolean isInterface;
    public int variableCount;
    public Map<String, Method> methods;

    ClassDebt cd;

    public Class(String name, boolean isInterface, int variableCount) {
        this.name = name;
        this.isInterface = isInterface;
        this.variableCount = variableCount;
        this.methods = new HashMap<>();
        this.cd = new ClassDebt();
    }
}
