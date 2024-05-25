package com.example.ce316project;

import java.io.*;

public class Configuration {
    private String language;
    private String compilerPath;
    private String compilerParams;
    private String runParams;
    private String configurationName;
    private static final String CONFIG_FOLDER = "Configurations";

    public Configuration() {
    }

    public Configuration(String language, String compilerPath, String compilerParams, String runParams, String configurationName) {
        this.setLanguage(language);
        this.setCompilerPath(compilerPath);
        this.setCompilerParams(compilerParams);
        this.setRunParams(runParams);
        this.setConfigurationName(configurationName);
    }

    public static Configuration createConfiguration(String language, String compilerPath, String compilerParams, String runParams, String configurationName) {
        return new Configuration(language, compilerPath, compilerParams, runParams, configurationName);
    }

    public void saveConfiguration() {
        if (getLanguage() != null && !getLanguage().isEmpty() && getCompilerPath() != null && !getCompilerPath().isEmpty() && getConfigurationName() != null && !getConfigurationName().isEmpty()) {
            String configFilePath = CONFIG_FOLDER + File.separator + getConfigurationName() + "_config.txt";
            File folder = new File(CONFIG_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            try (PrintWriter writer = new PrintWriter(configFilePath)) {
                writer.println("Language: " + getLanguage());
                writer.println("Compiler Path: " + getCompilerPath());
                writer.println("Compiler Parameters: " + getCompilerParams());
                writer.println("Run Parameters: " + getRunParams());
                writer.println("Configuration Name: " + getConfigurationName());
                System.out.println("Configuration saved: " + getConfigurationName());
            } catch (IOException e) {
                System.out.println("Failed to save configuration: " + getConfigurationName());
                e.printStackTrace();
            }
        } else {
            System.out.println("Configuration could not be saved!");
        }
    }

    public void editConfiguration(String language, String compilerPath, String compilerParams, String runParams, String configurationName) {
        this.setLanguage(language);
        this.setCompilerPath(compilerPath);
        this.setCompilerParams(compilerParams);
        this.setRunParams(runParams);
        this.setConfigurationName(configurationName);
        saveConfiguration();
    }

    public static void removeConfiguration(String configurationName) {
        String configFilePath = CONFIG_FOLDER + File.separator + configurationName;
        File configFile = new File(configFilePath);
        if (configFile.exists()) {
            if (configFile.delete()) {
                System.out.println("Configuration removed: " + configurationName);
            } else {
                System.out.println("Failed to remove configuration: " + configurationName);
            }
        } else {
            System.out.println("Configuration does not exist: " + configurationName);
        }
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCompilerPath() {
        return compilerPath;
    }

    public void setCompilerPath(String compilerPath) {
        this.compilerPath = compilerPath;
    }

    public String getCompilerParams() {
        return compilerParams;
    }

    public void setCompilerParams(String compilerParams) {
        this.compilerParams = compilerParams;
    }

    public String getRunParams() {
        return runParams;
    }

    public void setRunParams(String runParams) {
        this.runParams = runParams;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }
}
