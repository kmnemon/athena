package object;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Class{
    public String name;
    public String packName;
    public boolean isInterface;
    public int variableCount;
    public Map<String, Method> methods;
    public List<Integer> comments;

    public Class(String name, String packName, boolean isInterface, int variableCount) {
        this.name = name;
        this.packName = packName;
        this.isInterface = isInterface;
        this.variableCount = variableCount;
        this.methods = new HashMap<>();
        this.comments = new ArrayList<>();
    }

    public Map<String, Method> getMethodsWithAccess(Access access){
        Map<String, Method> filterMethods = new HashMap<>();

        for(var m : this.methods.entrySet()){
            if(m.getValue().access.asString().equals(access.asString())){
                filterMethods.put(m.getKey(), m.getValue());
            }
        }

        return filterMethods;
    }

}
