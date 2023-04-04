package object;

public class Method {
    public String name;
    public int parametersCount;
    public int lines;


    public Method(String name, int parametersCount, int lines) {
        this.name = name;
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
