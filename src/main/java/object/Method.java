package object;


public class Method {
    public String name;
    public String packName;
    public String cname;
    public String declaration;
    public Access access;
    public int parametersCount;
    public int lines;
    public int cyclomatic;



    public Method(String name, String packName, String cname, String declaration, Access access, int parametersCount, int lines) {
        this.name = name;
        this.packName = packName;
        this.cname = cname;
        this.declaration = declaration;
        this.access = access;
        this.parametersCount = parametersCount;
        this.lines = lines;
        this.cyclomatic = 0;
    }
}
