package com.example.ce316project;

import java.io.*;
import java.util.Scanner;

public class Compiler {
    // C programını derleyip çalıştıran fonksiyon
    public String runCProgram(String cFilePath, String input) {
        StringBuilder output = new StringBuilder();
        try {
            // C programını derle
            String compileCommand = "gcc " + cFilePath + " -o program";
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();

            // Derleme sırasında hata varsa
            if (compileProcess.exitValue() != 0) {
                output.append("Can not run the program.\n");
                return output.toString();
            }

            // Çalışma işlemini başlat
            Process runProcess = Runtime.getRuntime().exec("./program", null, new File(cFilePath).getParentFile());

            // Input verisi varsa, işleme gönder
            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                    writer.flush(); // input gönderimi için buffer'ı boşalt
                }
            } else {
                output.append("Enter input!\n"); // input boş olduğunda hata olarak işaretle
                return output.toString();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream())); // standart hata çıktısını oku

            // Standart çıktı
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Standart hata çıktısı
            while ((line = errorReader.readLine()) != null) {
                output.append("Error ").append(line).append("\n");
            }

            runProcess.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output.append("Error ").append(e.getMessage()).append("\n");
        }
        return output.toString();
    }







    public String runJavaProgram(String javaFile, String input) {

        if (input == null || input.isEmpty()) {
            input=" ";
        }
        StringBuilder output = new StringBuilder();
        try {
            // Java dosyasının dizinini ve adını ayır
            File javaFilePath = new File(javaFile);
            String parentDir = javaFilePath.getParent();
            String javaFileName = javaFilePath.getName().replace(".java", "");

            // .java dosyasını derle
            String compileCommand = "javac \"" + javaFile + "\"";
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();

            // Derleme sırasında hata varsa
            BufferedReader compileErrorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String compileErrorLine;
            while ((compileErrorLine = compileErrorReader.readLine()) != null) {
                output.append(compileErrorLine).append("\n");
            }
            compileErrorReader.close();

            // Derleme hatası varsa çık
            if (output.length() > 0) {
                return output.toString();
            }

            // Komut oluşturma
            String command = "java " + javaFileName;

            // Çalışma işlemini başlat
            Process runProcess = Runtime.getRuntime().exec(command, null, new File(parentDir));

            // Input verisi varsa, işleme gönder
            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                }
            } else {
                // Eğer input boşsa, programı normal çalıştırın, ancak uyarı verin
                output.append("Enter input!");
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
