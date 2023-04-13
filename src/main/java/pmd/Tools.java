package pmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Tools {
    private static final Logger log = LoggerFactory.getLogger(Tools.class);

    private static String getPmdCommand(String codeDir, String ruleset, String report) {
//        String pmdDir = ".\\tools\\pmd-bin-6.55.0\\bin\\pmd.bat";
        String pmdDir = "./tools/pmd-bin-6.55.0/bin/pmd.bat";
        return pmdDir + " -d " + codeDir + " -f text -R " + ruleset + " >" + report;
    }

    private static String getP3CPmdCommand(String codeDir, String ruleset) {
        //String  pmdDir = "java -cp .\\tools\\p3c-pmd\\p3c-pmd.jar net.sourceforge.pmd.PMD -e UTF-8";
        String pmdDir = "java -cp ./tools/p3c-pmd/p3c-pmd.jar net.sourceforge.pmd.PMD -e UTF-8";
        return pmdDir + " -d " + codeDir + " -f text -R " + ruleset;
    }

    private static String getCpdCommand(String codeDir, String report) {
//        String cpdDir = ".\\tools\\pmd-bin-6.55.0\\bin\\cpd.bat";
        String cpdDir = "./tools/pmd-bin-6.55.0/bin/cpd.bat";
        String minimumTokens = "100";
        String language = "java";
        String encoding = "utf8";
        return cpdDir + " --minimum-tokens " + minimumTokens + " --files " + codeDir + " --language " + language + " --encoding " + encoding + " >" + report;
    }

    public static String generateReportPathStr(String codeDir, String rulesets, String reportDir) {
        String name = codeDir + "---" + rulesets;
        name = name.replace("/", "_");
        name = name.replace("\\", "_");
        name = name.replace(":", "");
        return reportDir + name;
    }

    public static List<String> generatePmdOutput(String codeDir, String ruleset, String reportDir) {
        String report = generateReportPathStr(codeDir, ruleset, reportDir);
        return generatePmdOrCpdOutput(getPmdCommand(codeDir, ruleset, report), report);
    }

    public static List<String> generateP3CPmdOutput(String codeDir, String ruleset, String reportDir) {
        String report = generateReportPathStr(codeDir, ruleset, reportDir);
        return generateP3COutput(getP3CPmdCommand(codeDir, ruleset), report);
    }

    public static List<String> generateCpdOutput(String codeDir, String reportDir) {
        String report = generateReportPathStr(codeDir, "duplication", reportDir);
        return generatePmdOrCpdOutput(getCpdCommand(codeDir, report), report);
    }

    private static List<String> generatePmdOrCpdOutput(String command, String report) {
        List<String> output = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec(command);

            boolean finished = process.waitFor(60, TimeUnit.SECONDS);
            if (finished) {
                output = Files.readAllLines(Paths.get(report));
            } else {
                log.info("pmd failed: " + command);
                process.destroy();
            }

        } catch (Exception e) {
            log.info("pmd failed: " + command);
            e.printStackTrace();
        }

        return output;
    }

    private static List<String> generateP3COutput(String command, String report) {
        List<String> output = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedWriter writer = new BufferedWriter((new FileWriter(new File(report))));
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            Thread inputStreamReader = new Thread(()->{
               try{
                   String line;
                   while ((line = reader.readLine())!= null){
                       output.add(line);
                       writer.write(line);
                       writer.newLine();
                   }

                   writer.close();

               }catch (IOException e){
                   log.info("p3c failed: " + command);
                   e.printStackTrace();
               }
            });

            inputStreamReader.start();
            boolean completed = process.waitFor(60, TimeUnit.SECONDS);
            if(!completed){
                log.info("p3c failed: " + command);
                process.destroy();
            }
            inputStreamReader.join();

        } catch (Exception e) {
            log.info("P3C failed: " + command);
            e.printStackTrace();
        }

        return output;
    }
}
