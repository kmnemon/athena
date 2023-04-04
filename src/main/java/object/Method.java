package object;

import java.util.ArrayList;
import java.util.List;

public class Method {
    public String name;
    public String declaration;
    public int parametersCount;
    public int lines;
    public int cyclomatic;


    public Method(String name, String declaration, int parametersCount, int lines) {
        this.name = name;
        this.declaration = declaration;
        this.parametersCount = parametersCount;
        this.lines = lines;
    }

    public boolean isSuperMethodWithParameters(){
        return this.parametersCount > 6;
    }

    public boolean isSuperMethodWithLines(){
        return this.lines > 100;
    }
}
