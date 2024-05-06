package com.example.ce316project;

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
