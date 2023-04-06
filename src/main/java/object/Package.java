package object;

import java.util.HashMap;
import java.util.Map;

public class Package {
    public String name;
    public Map<String, Class> classes;

    public Package(String name) {
        this.name = name;
        this.classes = new HashMap<>();
    }
}
