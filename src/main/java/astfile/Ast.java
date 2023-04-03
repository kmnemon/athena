package astfile;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import object.Class;
import object.Method;
import object.Package;
import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;


public class Ast {
    public Project p;
    String packName;
    private static final Logger log = LoggerFactory.getLogger(Ast.class);


    public Ast(Project p) {
        this.p = p;
    }

    public void astToObjects(Path path) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(path);
            generatePackage(cu);
            generateClass(cu);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }


    private void generatePackage(CompilationUnit cu){
        cu.getPackageDeclaration().ifPresent(pd -> generatePackageByName(pd.getName().asString()));
    }

    void generatePackageByName(String name){
        if(!p.packages.containsKey(name)) {
            p.packages.put(name, new Package(name));

            this.packName = name;
        }
    }

    private class Classes extends VoidVisitorAdapter<Void>{
        @Override
        public void visit(ClassOrInterfaceDeclaration cid, Void arg){
            super.visit(cid, arg);
            Class c = new Class(cid.getNameAsString(), cid.isInterface(),cid.getFields().size());
            p.packages.get(packName).classes.put(cid.getNameAsString(), c);

            cid.getMethods().forEach(m -> generateMethod(m, cid.getNameAsString()));
        }
    }

    private void generateClass(CompilationUnit cu){
        VoidVisitor<?> ci = new Classes();
        ci.visit(cu, null);
    }

    private void generateMethod(MethodDeclaration md, String ciname){
        Method m = new Method(md.getNameAsString(), md.getParameters().size(), getMethodLines(md));
        p.packages.get(packName).classes.get(ciname).methods.put(md.getNameAsString(), m);
    }

    private int getMethodLines(MethodDeclaration md){
        return md.getEnd().get().line - md.getBegin().get().line + 1;
    }


}
