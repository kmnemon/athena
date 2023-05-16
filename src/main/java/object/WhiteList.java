package object;

import techdebt.Design;
import techdebt.Maintenance;
import techdebt.Regulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WhiteList {
    //rule, classes
    List<String> maintenanceWhiteList;
    List<String> regulationWhiteList;
    List<String> designWhiteList;

    public WhiteList() {
        maintenanceWhiteList = new ArrayList<>();
        regulationWhiteList = new ArrayList<>();
        designWhiteList = new ArrayList<>();

        transferWhiteListPathToCategory(readWhiteList());
    }

    Map<String, List<String>> readWhiteList(){
        //TO DO:
        return new HashMap<>();
    }

    void transferWhiteListPathToCategory(Map<String, List<String>> whiteListPath) {
        List<String> maintenanceWhiteListPath = whiteListPath.get("Maintenance");
        if (maintenanceWhiteListPath != null && !maintenanceWhiteListPath.isEmpty()) {
            maintenanceWhiteList = transferWhiteListPathToWhiteList(maintenanceWhiteListPath);
        }

        List<String> regulationWhiteListPath = whiteListPath.get("Regulation");
        if (regulationWhiteListPath != null && !regulationWhiteListPath.isEmpty()) {
            regulationWhiteList = transferWhiteListPathToWhiteList(regulationWhiteListPath);
        }

        List<String> designWhiteListPath = whiteListPath.get("Design");
        if (designWhiteListPath != null && !designWhiteListPath.isEmpty()) {
            designWhiteList = transferWhiteListPathToWhiteList(designWhiteListPath);
        }
    }

    List<String> transferWhiteListPathToWhiteList(List<String> path){
        //TO DO:
        return new ArrayList<>();
    }

    void filterWithWhiteList(P p){
        filterMaintenance(p.getMaintenance());
        filterRegulation(p.getRegulation());
        filterDesign(p.getDesign());
    }

    void filterMaintenance(Maintenance m) {
        for (String str : maintenanceWhiteList) {
            m.godClassWithVariables.remove(str);
            m.godClassWithMethods.remove(str);
            m.godComments.remove(str);


        }
    }


    void filterRegulation(Regulation r){

    }

    void filterDesign(Design d){

    }

    


}
