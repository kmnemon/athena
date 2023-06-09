package pmd;

import main.Main;
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

    private static String getPmdCommand(String codeDir, String ruleset) {
        String pmdDir;
        if(Main.UNIX){
            pmdDir = "./tools/pmd-bin-6.55.0/bin/pmd.bat";
        }else{
            pmdDir = ".\\tools\\pmd-bin-6.55.0\\bin\\pmd.bat";
        }
        return pmdDir + " -d " + codeDir + " -f text -R " + ruleset;
    }

    private static String getP3CCommand(String codeDir, String ruleset) {
        String pmdDir;
        if(Main.UNIX) {
            pmdDir = "java -cp ./tools/p3c-pmd/p3c-pmd.jar net.sourceforge.pmd.PMD -e UTF-8";
        }else{
            pmdDir = "java -cp .\\tools\\p3c-pmd\\p3c-pmd.jar net.sourceforge.pmd.PMD -e UTF-8";
        }
        return pmdDir + " -d " + codeDir + " -f text -R " + ruleset;
    }

    private static String getCpdCommand(String codeDir) {
        String cpdDir;
        if(Main.UNIX) {
            cpdDir = "./tools/pmd-bin-6.55.0/bin/cpd.bat";
        }else {
            cpdDir = ".\\tools\\pmd-bin-6.55.0\\bin\\cpd.bat";
        }
        String minimumTokens = "100";
        String language = "java";
        String encoding = "utf8";
        return cpdDir + " --minimum-tokens " + minimumTokens + " --files " + codeDir + " --language " + language + " --encoding " + encoding;
    }

    public static String generateReportPathStr(String codeDir, String rulesets, String reportDir) {
        String name = codeDir;
        name = name.replace("/", "_");
        name = name.replace("\\", "_");
        name = name.replace(":", "");
        name = name.replace(".", "");

        rulesets = rulesets.replace("/", "_");
        rulesets = rulesets.replace(".", "_");

        File directory = new File(reportDir + name );
        if( !directory.exists()) {
            directory.mkdirs();
        }

        String fullReportDir;
        if(Main.UNIX){
            fullReportDir = reportDir +  name + "/" + rulesets;
        }else {
            fullReportDir = reportDir +  name + "\\" + rulesets;
        }

        return fullReportDir;
    }

    public static List<String> generatePmdOutput(String codeDir, String ruleset, String reportDir) {
        String report = generateReportPathStr(codeDir, ruleset, reportDir);
        return generateOutputWriteObjectFirst(getPmdCommand(codeDir, ruleset), report);
    }

    public static List<String> generateP3COutput(String codeDir, String ruleset, String reportDir) {
        String report = generateReportPathStr(codeDir, ruleset, reportDir);
        return generateOutputWriteObjectFirst(getP3CCommand(codeDir, ruleset), report);
    }

    public static List<String> generateCpdOutput(String codeDir, String reportDir) {
        String report = generateReportPathStr(codeDir, "duplication", reportDir);
        return generateOutputWriteObjectFirst(getCpdCommand(codeDir), report);
    }

    private static List<String> generateOutputWriteFileFirst(String command, String report) {
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

    private static List<String> generateOutputWriteObjectFirst(String command, String report) {
        List<String> output = new ArrayList<>();

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedWriter writer = new BufferedWriter((new FileWriter(report)));
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            Thread inputStreamReader = new Thread(() -> {
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        line = getRelativePath(line);

                        output.add(line);
                        writer.write(line);
                        writer.newLine();
                    }

                    writer.close();

                } catch (IOException e) {
                    log.info("p3c failed: " + command);
                    e.printStackTrace();
                }
            });

            inputStreamReader.start();
            boolean completed = process.waitFor(60, TimeUnit.SECONDS);
            if (!completed) {
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

    private static String getRelativePath(String line) {
        String prefixSrc;
        String prefixTest;

        if(Main.UNIX) {
            prefixSrc = "/src/main/java";
            prefixTest = "/src/test/java";
        }else {
            prefixSrc = "\\src\\main\\java";
            prefixTest = "\\src\\test\\ava";
        }

        int indexSrc = line.indexOf(prefixSrc);
        int indexTest = line.indexOf(prefixTest);

        if (indexSrc != -1) {
            line = line.substring(indexSrc);
        } else if (indexTest != -1) {
            line = line.substring(indexTest);
        }
        return line;
    }
}
