package object;

import main.Main;
import org.yaml.snakeyaml.Yaml;
import techdebt.Design;
import techdebt.Duplication;
import techdebt.Maintenance;
import techdebt.Regulation;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WhiteList {
    static Map<String, List<String>> maintenanceWhiteList;
    static Map<String, List<String>> regulationWhiteList;
    static Map<String, List<String>> designWhiteList;


    public static void initWhiteListFromYaml(){
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("whitelist.yml");
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);

        maintenanceWhiteList = (Map<String, List<String>>) data.get("maintenance");
        regulationWhiteList = (Map<String, List<String>>) data.get("regulation");
        designWhiteList = (Map<String, List<String>>) data.get("design");
    }

    public static void filterMaintenance(Maintenance m){
        for(var item : maintenanceWhiteList.entrySet()){
            if(item != null){
                for(var name : item.getValue()){
                    filterMaintenanceWithName(m, name);
                }
            }
        }
    }

    private static void filterMaintenanceWithName(Maintenance m, String name) {
        name = transformNameToDotFormatAndRemoveTheHead(name);

        removeItemWhenContainInName(m.godClassWithMethods, name);
        removeItemWhenContainInName(m.godClassWithVariables, name);
        removeItemWhenContainInName(m.godComments, name);

        removeItemWhenContainInName(m.superMethodWithParameters, name);
        removeItemWhenContainInName(m.superMethodWithLines, name);
        removeItemWhenContainInName(m.superCyclomatics, name);

        removeDuplicationWhenContainInName(m.superDuplications, name);
    }

    static String transformNameToDotFormatAndRemoveTheHead(String name){
        if(Main.UNIX){
            name = name.replace("/", ".");
        }else {
            name = name.replace("\\", ".");
        }

        name = name.replace("src.main.java", "");
        name = name.replace("src.test.java", "");

        name = name.replaceAll("^\\.+|\\.+$", "");

        return name;
    }

    static void removeItemWhenContainInName(Map<String, Integer> m, String name){
        m.entrySet().removeIf(item -> item.getKey().contains(name));
    }

    static void removeDuplicationWhenContainInName(Map<Duplication, Integer> m, String name){
        m.entrySet().removeIf(item -> item.getKey().file1.contains(name) || item.getKey().file2.contains(name));
    }


    public static void filterRegulation(Regulation r){
        for(var item : regulationWhiteList.entrySet()){
            if(item != null){
                for(var name : item.getValue()){
                    filterRegulationWithName(r, name);
                }
            }
        }
    }

    static void filterRegulationWithName(Regulation l, String name){
        removeItemWhenContainInName(l.commentOriginData, name);
        removeItemWhenContainInName(l.constantOriginData, name);
        removeItemWhenContainInName(l.exceptionOriginData, name);
        removeItemWhenContainInName(l.flowControlOriginData, name);
        removeItemWhenContainInName(l.namingOriginData, name);
        removeItemWhenContainInName(l.oopOriginData, name);
        removeItemWhenContainInName(l.setOriginData, name);
        removeItemWhenContainInName(l.otherOriginData, name);
    }

    public static void filterDesign(Design d){
        for(var item : designWhiteList.entrySet()){
            if(item != null){
                for(var name : item.getValue()){
                    filterDesignWithName(d, name);
                }
            }
        }
    }

    static void filterDesignWithName(Design d, String name){
        removeItemWhenContainInName(d.designOriginData, name);
        removeItemWhenContainInName(d.multithreadingOriginData, name);
        removeItemWhenContainInName(d.performanceOriginData, name);
    }

    static void removeItemWhenContainInName(List<String> l, String name){
        for(Iterator<String> li = l.iterator(); li.hasNext();){
            String str = li.next();
            if( str.contains(name)){
                li.remove();
            }
        }
    }

}
