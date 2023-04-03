package object;

import techdebt.MethodDebt;

public class Method {
    public String name;
    public int parametersCount;
    public int lines;

    MethodDebt md;


    public Method(String name, int parametersCount, int lines) {
        this.name = name;
        this.parametersCount = parametersCount;
        this.lines = lines;
        this.md = new MethodDebt();
    }
}
