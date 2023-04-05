package object;

import java.util.ArrayList;
import java.util.List;

public class Method {
    public String name;
    public String packName;
    public String cname;
    public String declaration;
    public int parametersCount;
    public int lines;
    public int cyclomatic;


    public Method(String name, String packName, String cname, String declaration, int parametersCount, int lines) {
        this.name = name;
        this.packName = packName;
        this.cname = cname;
        this.declaration = declaration;
        this.parametersCount = parametersCount;
        this.lines = lines;
        this.cyclomatic = 0;
    }

    public boolean isSuperMethodWithParameters(){
        return this.parametersCount > 10;
    }

    public boolean isSuperMethodWithLines(){
        return this.lines > 100;
    }
}
