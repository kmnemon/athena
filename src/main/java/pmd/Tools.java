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

    private static String getPmdCommand(String codeDir, String rulesets) {
//        String pmdDir = ".\\tools\\pmd-bin-6.55.0\\bin\\pmd.bat";
        String pmdDir = "./tools/pmd-bin-6.55.0/bin/pmd.bat";
        return pmdDir + " -d " + codeDir + " -f text -R " + rulesets;
    }

    private static String getP3CPmdCommand(String codeDir, String ruleset){
        //Stirng pmdDir = "java -cp .\\tools\\p3c-pmd\\p3c-pmd.jar net.sourceforge.pmd.PMD -e UTF-8";
        String pmdDir = "java -cp ./tools/p3c-pmd/p3c-pmd.jar net.sourceforge.pmd.PMD -e UTF-8";
        return pmdDir + " -d " + codeDir + " -f text -R " + ruleset;
    }

    private static String getCpdCommand(String codeDir) {
//        String cpdDir = ".\\tools\\pmd-bin-6.55.0\\bin\\cpd.bat";
        String cpdDir = "./tools/pmd-bin-6.55.0/bin/cpd.bat";
        String minimumTokens = "100";
        String language = "java";
        String encoding = "utf8";
        return cpdDir + " --minimum-tokens " + minimumTokens + " --files " + codeDir + " --language " + language + " --encoding " + encoding;
    }

    public static List<String> generatePmdOutput(String codeDir, String ruleset){
        return generatePmdOrCpdOutput(getPmdCommand(codeDir, ruleset));
    }

    public static List<String> generateP3CPmdOutput(String codeDir, String ruleset){
        return generatePmdOrCpdOutput(getP3CPmdCommand(codeDir, ruleset));
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
