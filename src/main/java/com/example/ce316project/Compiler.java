package com.example.ce316project;

import java.io.*;

public class Compiler {
    // C programını derleyip çalıştıran fonksiyon
    public String runCProgram(String cFilePath, String input, String compilerPath) {
        StringBuilder output = new StringBuilder();
        try {
            String compilerCommand = compilerPath + "gcc " + cFilePath + " -o program.c";
            Process compileProcess = Runtime.getRuntime().exec(compilerCommand);
            compileProcess.waitFor();

            if (compileProcess.exitValue() != 0) {
                output.append("C programı derlenirken hata oluştu.");
                return output.toString();
            }

            Process runProcess = Runtime.getRuntime().exec("program.c");

            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            runProcess.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output.append("Error occurred: ").append(e.getMessage());
        }
        return output.toString();
    }

    public String runJavaProgram(String javaFile, String input, String compilerPath) {
        StringBuilder output = new StringBuilder();
        try {
            // Java dosyasının dizinini ve adını ayır
            File javaFilePath = new File(javaFile);
            String parentDir = javaFilePath.getParent();
            String javaFileName = javaFilePath.getName().replace(".java", "");

            // Komut oluşturma
            String command = "java -cp \"" + parentDir + "\" " + javaFileName;
            if (compilerPath != null && !compilerPath.isEmpty()) {
                command = compilerPath + File.separator + "bin" + File.separator + command;
            }

            // Çalışma işlemini başlat
            Process runProcess = Runtime.getRuntime().exec(command);

            // Input verisi varsa, işleme gönder
            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                }
            }

            // Hata çıktısını oku
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                output.append(errorLine).append("\n");
            }

            // Çalışma işleminin çıktısını oku
            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Çalışma işleminin sonunu bekle
            runProcess.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output.append("Error occurred: ").append(e.getMessage());
        }
        return output.toString();
    }


    public String runPythonProgram(String pyFile, String input, String compilerPath) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder(compilerPath + "python", pyFile);
            pb.directory(new File(pyFile).getParentFile());
            Process p = pb.start();

            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()))) {
                    writer.write(input);
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            p.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output.append("Error occurred: ").append(e.getMessage());
        }
        return output.toString();
    }
}
