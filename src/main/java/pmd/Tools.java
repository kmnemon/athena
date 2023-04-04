package pmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Tools {
    private static final Logger log = LoggerFactory.getLogger(Tools.class);

    private static String getPmdCommand(String codeDir) {
        String pmdDir = ".\\tools\\pmd-bin-6.45.0\\bin\\pmd.bat";
        String rulesets = "category/java/design.xml/CyclomaticComplexity";
        return pmdDir + " -d" + codeDir + " -f text -R" + rulesets;
    }

    private static String getCpdCommand(String codeDir) {
        String cpdDir = ".\\tools\\pmd-bin-6.45.0\\bin\\cpd.bat";
        String minimumTokens = "100";
        String language = "java";
        String encoding = "utf8";
        return cpdDir + " --minimum-tokens " + minimumTokens + " --files " + codeDir + " --language " + language + " --encoding " + encoding;
    }

    public static List<String> generatePmdOutput(String codeDir){
        return generatePmdOrCpdOutput(getPmdCommand(codeDir));
    }

    public static List<String> generateCpdOutput(String codeDir){
        return generatePmdOrCpdOutput(getCpdCommand(codeDir));
    }

    private static List<String> generatePmdOrCpdOutput(String command) {
        List<String> output = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = stdInput.readLine()) != null) {
                output.add(line);
            }

        } catch (IOException e) {
            log.error(e.toString());
        }

        return output;
    }
}
