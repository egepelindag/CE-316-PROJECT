package com.example.ce316project;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import javafx.scene.control.MenuItem;


public class Controller {
    @FXML
    private MenuItem newProjectMenuItem;
    @FXML
    private TextArea projectInput;
    @FXML
    private Button mainExitButton;
    @FXML
    private MenuItem javaConfigMenuItem;
    @FXML
    private MenuItem cConfigMenuItem;
    @FXML
    private MenuItem cSharpConfigMenuItem;
    @FXML
    private MenuItem pythonConfigMenuItem;
    @FXML
    private MenuButton configMenuButton;
    @FXML
    private Button importConfigurationButton;
    @FXML
    private Button chooseSubmissionButton;
    @FXML
    private Button chooseExpectedOutpuButton;
    @FXML
    private TextField submissionDirectoryTextField;
    @FXML
    private TextField expectedOutputFileDirectoryTextField;
    @FXML
    private Button newConfigButton;
    @FXML
    private Button createProjectButton;
    @FXML
    private TextField createProjectGetName;
    @FXML
    private TextField newConfigName;
    @FXML
    private TextField newConfigParameters;
    @FXML
    private TextField newConfigMainFile;
    @FXML
    private TextField newConfigExeName;
    @FXML
    private TextField newConfigPath;
    @FXML
    private Button newConfigChooseButton;
    @FXML
    private Button newConfigCreateButton;
    @FXML
    private Button runButton;
    @FXML
    private TextArea submissionOutputText;
    @FXML
    private Button exitButton;
    @FXML
    private Button welcomeNewProjectButton;
    @FXML
    private Button welcomeOpenProjectButton;
    @FXML
    private Button welcomeConfigButton;
    @FXML
    private Button mainBackButton;
    @FXML
    private MenuItem openProjectMenuItem;
    @FXML
    private TextField projectNameTextField;
    @FXML
    private TextArea expectedOutputText;
    @FXML
    private TextArea resultText;
    @FXML
    private TextField studentNameTextField;
    
    @FXML
    private Button welcomeHelpButton;

    private Button createProjectBackButton;



    static Project project= new Project();
    static Configuration configuration= new Configuration();

    @FXML
    public void mainExitButton(ActionEvent event){
        Platform.exit();
    }


    @FXML
    public void newProjectButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("newProject.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setResizable(false);
            newStage.setScene(scene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    public void javaConfigOnAction(ActionEvent event) {
        configMenuButton.setText("JAVA");
        configuration = Configuration.createConfiguration("Java", "", "-g", "","java");

        configuration.saveConfiguration();

        project.setConfiguration(configuration);

        System.out.println(project.getConfiguration().getCompilerPath());
    }

    @FXML
    public void cConfigMenuOnAction(ActionEvent event) {
        configMenuButton.setText("C");
        configuration = Configuration.createConfiguration("C", "", "-Wall -o", "","c");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);
    }


    @FXML
    public void cSharpConfigOnAction(ActionEvent event) {
        configMenuButton.setText("C#");
        configuration = Configuration.createConfiguration("C#", "csc", "-out:", "mono","c#");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);
    }

    @FXML
    public void pythonConfigOnAction(ActionEvent event) {
        configMenuButton.setText("Python");
        configuration = Configuration.createConfiguration("Python", "python", "", "python","python");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);
    }



    @FXML
    public void runButton(ActionEvent event) throws IOException, InterruptedException {
        Compiler compiler = new Compiler();

        
        System.out.println("config name = " + project.getConfiguration().getConfigurationName());
        String codeOutput="";
        String language="";
        System.out.println(project.getConfiguration().getConfigurationName());


        if (Objects.equals(project.getConfiguration().getConfigurationName(), "JAVA")){
            language="JAVA";
            System.out.println("main file path: " + project.getSubmissionDirectoryPath());

            System.out.println("input " + projectInput.getText());


            String userInput = projectInput.getText();

            codeOutput = compiler.runJavaProgram(project.getSubmissionDirectoryPath(), userInput);

            System.out.println("output: " + codeOutput);

            if (codeOutput != null) {
                submissionOutputText.setText(codeOutput);
            } else {
                submissionOutputText.setText("Error!");
            }


        }else if (Objects.equals(project.getConfiguration().getConfigurationName(), "C")){
            language="C";
            System.out.println("C code running");
            System.out.println("input: "+projectInput.getText());
            String CInput = projectInput.getText();
            codeOutput = compiler.runCProgram(project.getSubmissionDirectoryPath(), CInput);
            System.out.println("output: "+codeOutput);
            submissionOutputText.setText(codeOutput);
        }


        String expectedOutput=project.expectedOutput(project.getExpectedOutputPath());
        expectedOutputText.setText(expectedOutput);

        String result;
        if (Objects.equals(codeOutput, expectedOutput)) {
            result = "Success!";
        } else {
            result = "Failed!";
        }

        resultText.setText(result);

        String projectPath = project.getProjectName() + "_" + language;
        String path = "Projects/" + projectPath + ".txt";
        System.out.println("path: " + path);

        try {

            File file = new File(path);
            if (!file.exists()) {
                System.out.println("File not found: " + path);
                return;
            }


            String content = new String(Files.readAllBytes(Paths.get(path)));
            if (content.contains("Result:")) {

                content = content.replaceAll("Result:.*", "Result: " + result);
            } else {

                content += "\nResult: " + result;
            }

            Files.write(Paths.get(path), content.getBytes());
            System.out.println("Result updated/appended in file.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (Files.exists(Paths.get(path))) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(path)));
                System.out.println("File content: \n" + content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + path);
        }
    }



    @FXML
    public void createProjectBackButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

    }




    @FXML
    public void importConfigButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) importConfigurationButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("File: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void submissionDirectoryChooseButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) chooseExpectedOutpuButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) { 
            submissionDirectoryTextField.setText(selectedFile.getAbsolutePath());
            project.setExpectedOutputPath(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void expectecOutputDirectoryChooseButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) chooseExpectedOutpuButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            expectedOutputFileDirectoryTextField.setText(selectedFile.getAbsolutePath());
            project.setExpectedOutputPath(expectedOutputFileDirectoryTextField.getText());
        }
    }

    @FXML
    public void createProjectOnAction(ActionEvent event) throws IOException {
        String name = createProjectGetName.getText();
        String config = configMenuButton.getText();
        String submissionDirectoryPath=submissionDirectoryTextField.getText();
        String expectedOutput = expectedOutputFileDirectoryTextField.getText();



        project.setProjectName(name);
        project.getConfiguration().setConfigurationName(config);
        project.setSubmissionDirectoryPath(submissionDirectoryPath);
        project.setExpectedOutputPath(expectedOutput);



        System.out.println(project.getProjectName());
        System.out.println(project.getConfiguration().getConfigurationName());
        if (project.getSubmissionDirectoryPath() != "" && project.getExpectedOutputPath()!="") {
            System.out.println(project.getSubmissionDirectoryPath());
            System.out.println(project.getExpectedOutputPath());
        }
        else{

            System.out.println("NULL");
        }

        File directory = new File("Projects");
        if (!directory.exists()) {
            directory.mkdirs();
        }


        File projectFile = new File(directory, project.getProjectName()+"_"+project.getConfiguration().getConfigurationName() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile))) {
            writer.write("Student ID: " + project.getProjectName());
            writer.newLine();
            writer.write("Configuration Name: " + project.getConfiguration().getConfigurationName());
            writer.newLine();
            writer.write("Main File: " + project.getSubmissionDirectoryPath());
            writer.newLine();
            writer.write("Expected Output File:" + project.getExpectedOutputPath());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(project.getProjectName());
        String projectName = project.getProjectName();


        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();


        TextField projectNameTextField = (TextField) loader.getNamespace().get("projectNameTextField");
        projectNameTextField.setText("Student ID: "+projectName);



        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();


        System.out.println("dosya: "+projectFile.getParent());
    }

    @FXML
    public void addNewConfigOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newConfiguration.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    public void chooseNewConfig(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) newConfigChooseButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            newConfigPath.setText(selectedFile.getAbsolutePath());
        }
    }




    @FXML
    public void welcomeHelpButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("help.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

    }

    @FXML
    public void welcomeNewProjectButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newProject.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

    }

    @FXML
    public void welcomeOpenProjectButton(ActionEvent event) throws IOException {
        Project openProject=new Project();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) welcomeOpenProjectButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("File: " + selectedFile.getAbsolutePath());
        }

        String projectName = "";
        Configuration configuration = new Configuration();
        String mainFilePath = "";
        String expectedOutput = "";


        try (BufferedReader okuyucu = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()))) {
            String satir;
            while ((satir = okuyucu.readLine()) != null) {
                if (satir.startsWith("Student ID:")) {
                    projectName = satir.substring(satir.indexOf(":") + 1).trim();
                    openProject.setProjectName(projectName);
                } else if (satir.startsWith("Configuration Name:")) {
                    configuration.setConfigurationName(satir.substring(satir.indexOf(":") + 1).trim());
                    openProject.setConfiguration(configuration);
                } else if (satir.startsWith("Main File:")) {
                    mainFilePath = satir.substring(satir.indexOf(":") + 1).trim();
                    openProject.setSubmissionDirectoryPath(mainFilePath);
                } else if (satir.startsWith("Expected Output File")) {
                    expectedOutput = satir.substring(satir.indexOf(":") + 1).trim();
                    openProject.setExpectedOutputPath(expectedOutput);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        project=openProject;

        System.out.println("name : " + projectName);
        System.out.println("Configuration : " + project.getConfiguration().getConfigurationName());
        System.out.println("mainFilePath : " + project.getSubmissionDirectoryPath());
        System.out.println("expectedOutput : " + project.getExpectedOutputPath());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();


        TextField projectNameTextField = (TextField) loader.getNamespace().get("projectNameTextField");
        projectNameTextField.setText(projectName);

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();

    }

    @FXML
    public void welcomeConfigButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) welcomeConfigButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("File: " + selectedFile.getAbsolutePath());
        }
    }




    @FXML
    public void mainPageOpenProject(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        } else {
            System.out.println("No file selected.");
        }
        project.openProject(selectedFile.getAbsolutePath());
    }



    @FXML
    public void mainBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

    }



    @FXML
    public void createConfigurationOnAction(ActionEvent event) {
        String configName = newConfigName.getText();
        String configParameters = newConfigParameters.getText();
        //String configMainFile = newConfigMainFile.getText();
        String configExeName = newConfigExeName.getText();
        String configPath = newConfigPath.getText();
        File directory = new File("Configurations");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File configFile = new File(directory, configName + "_config.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write("Config Name: " + configName);
            writer.newLine();
            writer.write("Parameters: " + configParameters);
            writer.newLine();
            writer.write("Executable Name: " + configExeName);
            writer.newLine();
            writer.write("Path: " + configPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}