package astfile;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import object.Class;
import object.Method;
import object.Package;
import object.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;


public class Ast {
    public static Project p;

    private static final Logger log = LoggerFactory.getLogger(Ast.class);

    public static Project generateObjectsWithAst(String codeDir, Project p){
        Ast.p = p;

        try(Stream<Path> stream = Files.walk(Paths.get(codeDir))){
            stream.filter(Files::isRegularFile)
                    .filter(Ast::isJavaFile)
                    .forEach(Ast::astToObjects);

        }catch (IOException e){
            log.error(e.toString());
        }

        return Ast.p;
    }

    public static boolean isJavaFile(Path path){
        return path.toString().endsWith(".java");
    }

    public static void astToObjects(Path path) {
        try {
            CompilationUnit cu = StaticJavaParser.parse(path);
            String packName = generatePackage(cu);
            generateClass(cu, packName);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }


    private static String generatePackage(CompilationUnit cu){
        cu.getPackageDeclaration().ifPresent(pd -> generatePackageByName(pd.getNameAsString()));
        return cu.getPackageDeclaration().get().getNameAsString();
    }

    private static void  generatePackageByName(String name){
        if(!p.packages.containsKey(name)) {
            p.packages.put(name, new Package(name));
        }
    }

    private static void generateClass(CompilationUnit cu, String packName){
        Classes ci = new Classes();
        ci.visit(cu, packName);
        generateComment(cu, packName, ci.tmpClassName);
    }

    private static class Classes extends VoidVisitorAdapter<String>{
        public String tmpClassName;

        @Override
        public void visit(ClassOrInterfaceDeclaration cid, String packName){
            super.visit(cid, packName);
            Class c = new Class(cid.getNameAsString(), packName, cid.isInterface(),cid.getFields().size());
            p.packages.get(packName).classes.put(cid.getNameAsString(), c);
            this.tmpClassName = cid.getNameAsString();

            cid.getMethods().forEach(m -> generateMethod(m, packName, cid.getNameAsString()));
        }
    }

    private static void generateComment(CompilationUnit cu, String packName, String cname) {
        List<Comment> comments = cu.getAllContainedComments();
        comments.forEach(ct-> generateComments(ct, packName, cname));

    }

    private static void generateComments(Comment ct, String packName, String cname){
        int lines = ct.getEnd().get().line - ct.getBegin().get().line +1;
        p.packages.get(packName).classes.get(cname).comments.add(lines);
    }

    private static void generateMethod(MethodDeclaration md,String packName, String cname){
        Method m = new Method(md.getNameAsString(), packName, cname, md.getDeclarationAsString(), md.getParameters().size(), getMethodLines(md));
        p.packages.get(packName).classes.get(cname).methods.put(splitMethodSig(md.getDeclarationAsString(), md.getNameAsString()), m);
    }

    public static String splitMethodSig(String mDeclaration, String mName){
        return mDeclaration.substring(mDeclaration.indexOf(mName)).replaceAll("\\s[a-zA-Z\\d+]+?,", ",").replaceAll("\\s[a-zA-Z\\d+]+?\\)", ")");
    }

    private static int getMethodLines(MethodDeclaration md){
        return md.getEnd().get().line - md.getBegin().get().line + 1;
    }


}
