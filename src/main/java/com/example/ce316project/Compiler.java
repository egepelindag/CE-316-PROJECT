package com.example.ce316project;

import java.io.*;
import java.util.Scanner;

public class Compiler {
    public String runCProgram(String cFilePath, String input,String path) {
        StringBuilder output = new StringBuilder();
        try {
            String compileCommand = path+"\\gcc " + cFilePath + " -o program";
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();


            if (compileProcess.exitValue() != 0) {
                output.append("Can not run the program.\n");
                return output.toString();
            }


            Process runProcess = Runtime.getRuntime().exec("./program", null, new File(cFilePath).getParentFile());


            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                    writer.flush();
                }
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));


            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

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


    public String runCppProgram(String cppFilePath, String input, String path) {
        StringBuilder output = new StringBuilder();
        try {

            String compileCommand = path  + "\\g++ " + cppFilePath + " -o program";
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();


            if (compileProcess.exitValue() != 0) {
                output.append("Can not compile the program.\n");
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()))) {
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }
                return output.toString();
            }


            File parentDir = new File(cppFilePath).getParentFile();
            //Process runProcess = Runtime.getRuntime().exec(parentDir.getAbsolutePath() + File.separator + "program", null, parentDir);
            Process runProcess = Runtime.getRuntime().exec("./program", null, new File(cppFilePath).getParentFile());

            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                    writer.flush();
                }
            }


            try (BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
                 BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }

                while ((line = errorReader.readLine()) != null) {
                    output.append("Error ").append(line).append("\n");
                }
            }

            runProcess.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            output.append("Error ").append(e.getMessage()).append("\n");
        }
        return output.toString();
    }






    public String runJavaProgram(String javaFile, String input,String path) {

        if (input == null || input.isEmpty()) {
            input=" ";
        }
        StringBuilder output = new StringBuilder();
        try {

            File javaFilePath = new File(javaFile);
            String parentDir = javaFilePath.getParent();
            String javaFileName = javaFilePath.getName().replace(".java", "");


            String compileCommand = path+"\\javac \"" + javaFile + "\"";
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();


            BufferedReader compileErrorReader = new BufferedReader(new InputStreamReader(compileProcess.getErrorStream()));
            String compileErrorLine;
            while ((compileErrorLine = compileErrorReader.readLine()) != null) {
                output.append(compileErrorLine).append("\n");
            }
            compileErrorReader.close();


            if (output.length() > 0) {
                return output.toString();
            }


            String command = path+"\\java " + javaFileName;


            Process runProcess = Runtime.getRuntime().exec(command, null, new File(parentDir));


            if (input != null && !input.isEmpty()) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()))) {
                    writer.write(input);
                }
            }


            BufferedReader errorReader = new BufferedReader(new InputStreamReader(runProcess.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                output.append(errorLine).append("\n");
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






    public String runPythonProgram(String pyFile, String input, String compilerPath) {
        StringBuilder output = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder(compilerPath + "\\python ", pyFile);
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
