package techdebt;

import object.Project;
import pmd.Rulesets;
import pmd.Tools;

import java.util.List;

public class Regulation {
    Project p;

    List<String> commentOriginData;
    List<String> concurrentOriginData;
    List<String> constantOriginData;
    List<String> exceptionOriginData;
    List<String> flowControlOriginData;
    List<String> namingOriginData;
    List<String> oopOriginData;
    List<String> ormOriginData;
    List<String> otherOriginData;
    List<String> setOriginData;

    public Regulation(Project p) {
        this.p = p;
    }

    public void parseRegulationTechDebt(String codeDir){
        generateOriginData(codeDir);

    }

    //origin data
    private void generateOriginData(String codeDir){
        this.commentOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.COMMENT.toString());
        this.concurrentOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.CONCURRENT.toString());
    }

}
