package object;

import java.util.HashMap;
import java.util.Map;

public class Project {
    public String name;
    public Map<String, Package> packages;

    public Project(String name) {
        this.name = name;
        this.packages = new HashMap<>();
    }
}
