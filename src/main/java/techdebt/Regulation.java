package techdebt;

import object.Project;
import pmd.Rulesets;
import pmd.Tools;

import java.util.List;

public class Regulation {
    Project p;
    PrintRegulation printRegulation;

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
        generatePrintRegulation();

    }

    //origin data
    private void generateOriginData(String codeDir){
        this.commentOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.COMMENT.toString());
        this.concurrentOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.CONCURRENT.toString());
        this.constantOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.CONSTANT.toString());
        this.exceptionOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.EXCEPTION.toString());
        this.flowControlOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.FLOWCONTROL.toString());
        this.namingOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.NAMING.toString());
        this.oopOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.OOP.toString());
        this.ormOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.ORM.toString());
        this.otherOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.OTHER.toString());
        this.setOriginData = Tools.generateP3CPmdOutput(codeDir, Rulesets.SET.toString());

    }

    private void generatePrintRegulation(){
        this.printRegulation = new PrintRegulation(this);
    }

}
