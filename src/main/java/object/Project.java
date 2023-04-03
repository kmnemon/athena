package object;

import techdebt.ProjectDebt;

import java.util.HashMap;
import java.util.Map;

public class Project {
    public String name;
    public Map<String, Package> packages;

    public ProjectDebt pd;


    public Project(String name) {
        this.name = name;
        this.packages = new HashMap<>();
        this.pd = new ProjectDebt();
    }
}
