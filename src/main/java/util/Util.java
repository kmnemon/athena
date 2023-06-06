package util;

import main.Main;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Util {

    public static Map<String, Object> initEnvData(String env) {
        InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(env);
        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(inputStream);
        return data;
    }

    public static String addEndCharacterToDirectory(String reportDir) {
        if(Main.UNIX) {
            if (!reportDir.endsWith("/")) {
                reportDir = reportDir + "/";
            }
        }else {
            if (!reportDir.endsWith("\\")) {
                reportDir = reportDir + "\\";
            }
        }
        return reportDir;
    }
}
