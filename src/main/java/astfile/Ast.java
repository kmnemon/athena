package astfile;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
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
import java.util.ArrayList;
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

        Enums en = new Enums();
        en.visit(cu, packName);

        if(ci.tmpClassName != null) {
            generateComment(cu, packName, ci.tmpClassName);
        }else {
            generateComment(cu, packName, en.tmpClassName);
        }
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

    private static class Enums extends VoidVisitorAdapter<String>{
        public String tmpClassName;

        @Override
        public void visit(EnumDeclaration emd, String packName){
            super.visit(emd, packName);
            Class c = new Class(emd.getNameAsString(), packName, false, emd.getFields().size());
            p.packages.get(packName).classes.put(emd.getNameAsString(), c);
            this.tmpClassName = emd.getNameAsString();

            emd.getMethods().forEach(m -> generateMethod(m, packName, emd.getNameAsString()));
        }
    }

    private static void generateComment(CompilationUnit cu, String packName, String cname) {
        List<Integer> blockCommentCount = parseBlockComment(cu.getAllContainedComments());
        blockCommentCount.forEach(cbc-> generateComments(cbc, packName, cname));

    }
    private static List<Integer> parseBlockComment(List<Comment> comments){
        List<Integer> commentBlockCount = new ArrayList<>();
        int preBegin = 0;
        int preEnd = -1;
        for( Comment comment : comments){
            if( preEnd == -1){
                preBegin = comment.getBegin().get().line;
                preEnd = comment.getEnd().get().line;
            } else if( preEnd +1 == comment.getBegin().get().line ){
                preEnd = comment.getEnd().get().line;
            } else {
                commentBlockCount.add(preEnd-preBegin+1);
                preBegin = comment.getBegin().get().line;
                preEnd = comment.getEnd().get().line;
            }
        }

        commentBlockCount.add(preEnd-preBegin+1);

        return commentBlockCount;
    }

    private static void generateComments(Integer bc, String packName, String cname){
        p.packages.get(packName).classes.get(cname).comments.add(bc);
    }

    private static void generateMethod(MethodDeclaration md,String packName, String cname){
        String mname;
        if( !p.packages.get(packName).classes.get(cname).methods.containsKey(md.getNameAsString())){
            mname = md.getNameAsString();
        }else{
            int i = 2;
            while (p.packages.get(packName).classes.get(cname).methods.containsKey(md.getNameAsString()+ "@" + i)){
                i++;
            }
            mname = md.getNameAsString() + "@" + i;
        }

        Method m = new Method(md.getNameAsString(), packName, cname, mname, md.getParameters().size(), getMethodLines(md));
        p.packages.get(packName).classes.get(cname).methods.put(mname, m);
    }

//    public static String splitMethodSig(String mDeclaration, String mName){
//        return mDeclaration.substring(mDeclaration.indexOf(mName), mDeclaration.lastIndexOf(")")+1)
//                .replaceAll("\\s[a-zA-Z\\d+]+?,", ",")
//                .replaceAll("\\s[a-zA-Z\\d+]+?\\)", ")")
//                .replaceAll("<[a-zA-Z\\s]*,\\s*[a-zA-Z\\s]*>|<\\w+>", "");
//    }

    private static int getMethodLines(MethodDeclaration md){
        return md.getEnd().get().line - md.getBegin().get().line + 1;
    }


}
