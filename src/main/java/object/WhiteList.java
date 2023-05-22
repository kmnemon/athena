package object;

import main.Main;
import org.yaml.snakeyaml.Yaml;
import techdebt.Design;
import techdebt.Duplication;
import techdebt.Maintenance;
import techdebt.Regulation;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class WhiteList {
    static Map<String, List<String>> maintenanceWhiteList;
    static Map<String, List<String>> regulationWhiteList;
    static Map<String, List<String>> designWhiteList;


    static void initWhiteListFromYaml(){
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("whitelist.yml");
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);

        maintenanceWhiteList = (Map<String, List<String>>) data.get("maintenance");
        regulationWhiteList = (Map<String, List<String>>) data.get("regulation");
        designWhiteList = (Map<String, List<String>>) data.get("design");
    }

    public static void filterWithWhiteList(Project p){
        initWhiteListFromYaml();

        filterMaintenance(p.maintenance);
        filterRegulation(p.regulation);
        filterDesign(p.design);
    }

    static void filterMaintenance(Maintenance m){
        for(var item : maintenanceWhiteList.entrySet()){
            if(item != null){
                for(var name : item.getValue()){
                    filterMaintenanceWithName(m, name);
                }
            }
        }
    }

    private static void filterMaintenanceWithName(Maintenance m, String name) {
        removeItemWhenContainInName(m.godClassWithMethods, name);
        removeItemWhenContainInName(m.godClassWithVariables, name);
        removeItemWhenContainInName(m.godComments, name);

        removeItemWhenContainInName(m.superMethodWithParameters, name);
        removeItemWhenContainInName(m.superMethodWithLines, name);
        removeItemWhenContainInName(m.superCyclomatics, name);

        removeDuplicationWhenContainInName(m.superDuplications, name);
    }

    static void removeItemWhenContainInName(Map<String, Integer> m, String name){
        m.entrySet().removeIf(item -> item.getKey().contains(name));
    }

    static void removeDuplicationWhenContainInName(Map<Duplication, Integer> m, String packName){
        m.entrySet().removeIf(item -> item.getKey().file1.contains(packName) || item.getKey().file2.contains(packName));
    }


    static void filterRegulation(Regulation r){

    }

    static void filterDesign(Design d){

    }


}
