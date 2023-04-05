package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Class{
    public String name;
    public String packName;
    public boolean isInterface;
    public int variableCount;
    public Map<String, Method> methods;
    public List<Integer> comments;

    public Class(String name, String packName, boolean isInterface, int variableCount) {
        this.name = name;
        this.packName = name;
        this.isInterface = isInterface;
        this.variableCount = variableCount;
        this.methods = new HashMap<>();
        this.comments = new ArrayList<>();
    }

    public String getClassName(){
        return name;
    }

    public boolean isInterface(){
        return isInterface;
    }
    
    public boolean isSuperClassWithVariables(){
        return this.variableCount > 20;
    }

    public boolean isSuperClassWithMethods(){
        return this.methods.size() > 30;
    }

    public List<Integer> getSuperComments(){
        return comments.stream().filter(i -> i >10).collect(Collectors.toList());
    }


}
