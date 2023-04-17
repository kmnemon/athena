package techdebt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pmd.Rulesets;
import pmd.Tools;

import java.util.ArrayList;
import java.util.List;

public class Design {
    public DesignStatistics designStatistics;

    public List<String> designOriginData;
    public List<String> multithreadingOriginData;
    public List<String> performanceOriginData;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }

    public Design(){
        this.designStatistics = new DesignStatistics();
    }


    public void parseDesignTechDebt(String codeDir, String reportDir){
        generateOriginData(codeDir, reportDir);
        generateDesignStatistics();

    }

    //origin data
    private void generateOriginData(String codeDir, String reportDir){
        this.designOriginData = Tools.generatePmdOutput(codeDir, Rulesets.DESIGN.toString(), reportDir);
        this.multithreadingOriginData = Tools.generatePmdOutput(codeDir, Rulesets.MULTITHREADING.toString(), reportDir);
        this.performanceOriginData = Tools.generatePmdOutput(codeDir, Rulesets.PERFORMANCE.toString(), reportDir);
    }

    public void generateDesignStatistics(){
        this.designStatistics = new DesignStatistics(this);
    }


}
