package techdebt;

import object.Project;

public class Design {
    Project p;
    PrintDesign printDesign;

    List<String> designOriginData;
    List<String> multithreadingOriginData;
    List<String> performanceOriginData;
    List<String> errorProneOriginData;

    public Design(Project p) {
        this.p = p;
    }

    public void parseDesignTechDebt(String codeDir){
        generateOriginData(codeDir);
        generatePrintDesign();

    }

    //origin data
    private void generateOriginData(String codeDir){
        this.designOriginData = Tools.generatePmdOutput(codeDir, Rulesets.DESIGN.toString());
        this.multithreadingOriginData = Tools.generatePmdOutput(codeDir, Rulesets.MULTITHREADING.toString());
        this.performanceOriginData = Tools.generatePmdOutput(codeDir, Rulesets.PERFORMANCE.toString());
        this.errorProneOriginData = Tools.generatePmdOutput(codeDir, Rulesets.ERROR_PRONE.toString());
    }

    private void generatePrintDesign(){
        this.printDesign = new PrintDesign(this);
    }
}
