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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import javafx.scene.control.MenuItem; // Import the correct MenuItem class


public class Controller {
    @FXML
    private MenuItem newProjectMenuItem; // Check if this should be a MenuItem or another control type
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

    private Button createProjectBackButton;



    static Project project= new Project();
    static Configuration configuration= new Configuration();

    @FXML
    public void mainExitButton(ActionEvent event){
        Platform.exit();
    }

    //yeni proje açma kısmı
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



    //bunların hepsi newProject.fxml dosyasının
    //Configuration secme
    @FXML
    public void javaConfigOnAction(ActionEvent event) {
        configMenuButton.setText("JAVA");
        // Java için yapılandırma oluşturuluyor
        //bunlar burada olmayacak
        configuration = Configuration.createConfiguration("Java", "", "-g", "","java");

        configuration.saveConfiguration();

        project.setConfiguration(configuration);

        System.out.println(project.getConfiguration().getCompilerPath());

    }

    @FXML
    public void cConfigMenuOnAction(ActionEvent event) {
        configMenuButton.setText("C");
        // C için yapılandırma oluşturuluyor
        configuration = Configuration.createConfiguration("C", "", "-Wall -o", "","c");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);
    }


    @FXML
    public void cSharpConfigOnAction(ActionEvent event) {
        configMenuButton.setText("C#");
        // C# için yapılandırma oluşturuluyor
        configuration = Configuration.createConfiguration("C#", "csc", "-out:", "mono","c#");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);
    }

    @FXML
    public void pythonConfigOnAction(ActionEvent event) {
        configMenuButton.setText("Python");
        // Python için yapılandırma oluşturuluyor
        configuration = Configuration.createConfiguration("Python", "python", "", "python","python");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);
    }

    @FXML
    public void runButton(ActionEvent event) {
        Compiler compiler = new Compiler();
        System.out.println("fdsfasf "+project.getConfiguration().getCompilerPath());
        String codeOutput="dsgdD";
        System.out.println("output"+ codeOutput);
        System.out.println(project.getConfiguration().getConfigurationName());
        if (project.getConfiguration().getConfigurationName()=="JAVA"){
            System.out.println("input "+projectInput.getText());
            codeOutput = compiler.runJavaProgram(project.getSubmissionDirectoryPath(), projectInput.getText(),project.getConfiguration().getCompilerPath());

            System.out.println("output: "+ codeOutput);
            submissionOutputText.setText(codeOutput);
        }else if (project.getConfiguration().getConfigurationName()=="C"){
            codeOutput = compiler.runCProgram(project.getSubmissionDirectoryPath(), "",project.getConfiguration().getCompilerPath());
            submissionOutputText.setText(codeOutput);
        }



        String expectedOutput=project.expectedOutput(project.getExpectedOutputPath());
        expectedOutputText.setText(expectedOutput);

        if (Objects.equals(codeOutput, expectedOutput)){
            resultText.setText("Success!");

        }else {
            resultText.setText("Failed!");
        }



        System.out.println("name: "+project.getProjectName());
    }

    @FXML
    public void createProjectBackButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // Mevcut pencerenin sahnesini değiştir
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

    }



    //ödev yükleme, ödev seçme
    @FXML
    public void importConfigButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) importConfigurationButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Seçilen dosya: " + selectedFile.getAbsolutePath());
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
            //popup
            System.out.println("NULL");
        }
        File directory = new File("Projects");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File projectFile = new File(directory, project.getProjectName() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile))) {
            writer.write("Project Name: " + project.getProjectName());
            writer.newLine();
            writer.write("Configuration Name: " + project.getConfiguration().getConfigurationName());
            writer.newLine();
            writer.write("Main File: " + project.getSubmissionDirectoryPath());
            writer.newLine();
            writer.write("Executable Name: " + project.getExpectedOutputPath());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(project.getProjectName());
        String projectName = project.getProjectName();

// FXML dosyasını yüklemeden önce projectNameTextField'e değer atayın
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();

// FXML dosyası yüklendikten sonra projectNameTextField'e değer atayın
        TextField projectNameTextField = (TextField) loader.getNamespace().get("projectNameTextField");
        projectNameTextField.setText(projectName);

        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();


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

    //newProject.fxml kısmı bitiş



    @FXML
    public void welcomeNewProjectButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newProject.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // Mevcut pencerenin sahnesini değiştir
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

    }

    @FXML
    public void welcomeOpenProjectButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) welcomeOpenProjectButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Seçilen dosya: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    public void welcomeConfigButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) welcomeConfigButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Seçilen dosya: " + selectedFile.getAbsolutePath());
        }
    }



    //welcome page sonu
    //yeni proje açma kısmı

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
    }



    @FXML
    public void mainBackButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("welcomePage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        // Mevcut pencerenin sahnesini değiştir
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

    }

    @FXML
    public void openProjectMainPage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) importConfigurationButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Seçilen dosya: " + selectedFile.getAbsolutePath());
        }

    }



    //createConfigButton
    @FXML
    public void createConfigurationOnAction(ActionEvent event) {
        String configName = newConfigName.getText();
        String configParameters = newConfigParameters.getText();
        String configMainFile = newConfigMainFile.getText();
        String configExeName = newConfigExeName.getText();
        String configPath = newConfigPath.getText();
        File directory = new File("Configurations");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File configFile = new File(directory, configName + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write("Config Name: " + configName);
            writer.newLine();
            writer.write("Parameters: " + configParameters);
            writer.newLine();
            writer.write("Main File: " + configMainFile);
            writer.newLine();
            writer.write("Executable Name: " + configExeName);
            writer.newLine();
            writer.write("Path: " + configPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}