package com.example.ce316project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public class Project implements Serializable {
    private String projectName;
    private Configuration configuration;
    private String expectedOutputPath;
    private String submissionDirectoryPath;

    public Project(String projectName, Configuration configuration, String submissionDirectoryPath, String expectedOutputPath) {
        this.projectName = projectName;
        this.configuration = configuration;
        this.submissionDirectoryPath = submissionDirectoryPath;
        this.expectedOutputPath = expectedOutputPath;
    }

    public Project() {
        this.projectName = "";
        this.configuration = null;
        this.submissionDirectoryPath = "";
        this.expectedOutputPath = "";
    }

    // Yeni bir proje oluşturma
    public static Project createProject(String projectName, Configuration configuration, String submissionDirectoryPath, String expectedOutputPath) {
        return new Project(projectName, configuration, submissionDirectoryPath, expectedOutputPath);
    }


    public Project openProject(String dosyaYolu) {
        String projectName = "";
        Configuration configuration = new Configuration();
        String mainFilePath = "";
        String executableName = "";

        try (BufferedReader okuyucu = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = okuyucu.readLine()) != null) {
                if (satir.startsWith("Project Name:")) {
                    projectName = satir.substring(satir.indexOf(":") + 1).trim();
                } else if (satir.startsWith("Configuration Name:")) {
                    configuration.setConfigurationName(satir.substring(satir.indexOf(":") + 1).trim());
                } else if (satir.startsWith("Main File:")) {
                    mainFilePath = satir.substring(satir.indexOf(":") + 1).trim();
                } else if (satir.startsWith("Executable Name:")) {
                    executableName = satir.substring(satir.indexOf(":") + 1).trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Dosya yollarını kullanarak projeyi oluştur
        Project project = new Project(projectName, configuration, mainFilePath, executableName);

        return project;
    }

    public String expectedOutput(String dosyaYolu) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader okuyucu = new BufferedReader(new FileReader(dosyaYolu))) {
            String satir;
            while ((satir = okuyucu.readLine()) != null) {
                content.append(satir).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getExpectedOutputPath() {
        return expectedOutputPath;
    }

    public void setExpectedOutputPath(String expectedOutputPath) {
        this.expectedOutputPath = expectedOutputPath;
    }

    public String getSubmissionDirectoryPath() {
        return submissionDirectoryPath;
    }

    public void setSubmissionDirectoryPath(String submissionDirectoryPath) {
        this.submissionDirectoryPath = submissionDirectoryPath;
    }
}
