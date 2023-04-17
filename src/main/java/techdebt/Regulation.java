package techdebt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pmd.Rulesets;
import pmd.Tools;

import java.util.List;

public class Regulation {
    public RegulationStatistics regulationStatistics;

    public List<String> commentOriginData;
    public List<String> constantOriginData;
    public List<String> exceptionOriginData;
    public List<String> flowControlOriginData;
    public List<String> namingOriginData;
    public List<String> oopOriginData;
    public List<String> setOriginData;
    public List<String> otherOriginData;

    public void parseRegulationTechDebt(String codeDir, String reportDir){
        generateOriginData(codeDir, reportDir);
        generateRegulationStatistics();

    }

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public Regulation() {
        this.regulationStatistics = new RegulationStatistics();
    }

    //origin data
    private void generateOriginData(String codeDir, String reportDir){
        this.commentOriginData = Tools.generateP3COutput(codeDir, Rulesets.COMMENT.toString(), reportDir);
        this.constantOriginData = Tools.generateP3COutput(codeDir, Rulesets.CONSTANT.toString(), reportDir);
        this.exceptionOriginData = Tools.generateP3COutput(codeDir, Rulesets.EXCEPTION.toString(), reportDir);
        this.flowControlOriginData = Tools.generateP3COutput(codeDir, Rulesets.FLOWCONTROL.toString(), reportDir);
        this.namingOriginData = Tools.generateP3COutput(codeDir, Rulesets.NAMING.toString(), reportDir);
        this.oopOriginData = Tools.generateP3COutput(codeDir, Rulesets.OOP.toString(), reportDir);
        this.setOriginData = Tools.generateP3COutput(codeDir, Rulesets.SET.toString(), reportDir);
        this.otherOriginData = Tools.generateP3COutput(codeDir, Rulesets.OTHER.toString(), reportDir);

    }

    public void generateRegulationStatistics(){
        this.regulationStatistics = new RegulationStatistics(this);
    }

}
