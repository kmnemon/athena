package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Class {
    public String name;
    public boolean isInterface;
    public int variableCount;
    public Map<String, Method> methods;
    public List<Integer> comments;

    public Class(String name, boolean isInterface, int variableCount) {
        this.name = name;
        this.isInterface = isInterface;
        this.variableCount = variableCount;
        this.methods = new HashMap<>();
        this.comments = new ArrayList<>();
    }

    public boolean isSuperClassWithVariables(){
        return this.variableCount > 10;
    }

    public boolean isSuperClassWithMethods(){
        return this.methods.size() > 20;
    }

    public List<Integer> getSupperComments(){
        return comments.stream().filter(i -> i >6).collect(Collectors.toList());
    }


}
