package object;

import techdebt.PackageDebt;

import java.util.HashMap;
import java.util.Map;

public class Package {
    public String name;
    public Map<String, Class> classes;

    PackageDebt packd;

    public Package(String name) {
        this.name = name;
        this.classes = new HashMap<>();
        this.packd = new PackageDebt();
    }
}
