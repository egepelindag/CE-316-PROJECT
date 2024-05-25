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
import javafx.scene.layout.Background;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    private MenuItem deleteConfigButton;
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
    private MenuButton chooseConfigLanguage;
    @FXML
    private MenuItem chooseJavaConfig;
    @FXML
    private MenuItem chooseC_Config;
    @FXML
    private MenuItem chooseCppConfig;
    @FXML
    private MenuItem choosePythonConfig;
    @FXML
    private Button welcomeHelpButton;

    @FXML
    private Button createProjectBackButton;

    @FXML
    private Button chooseConfigButton;

    @FXML
    private TextField newProjectConfigName;
    @FXML
    private  Button configCreateOkButton;
    @FXML
    private Button newConfigBackButton;



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
       // configMenuButton.setText("JAVA");
        chooseConfigLanguage.setText("JAVA");
       /* configuration = Configuration.createConfiguration("JAVA", newConfigPath.getText(), "-g", "",newConfigName.getText());

        configuration.saveConfiguration();

        project.setConfiguration(configuration);
        System.out.println(project.getConfiguration().getConfigurationName());
        System.out.println(project.getConfiguration().getLanguage());
        System.out.println(project.getConfiguration().getCompilerPath());*/
    }

    @FXML
    public void cConfigMenuOnAction(ActionEvent event) {
        chooseConfigLanguage.setText("C");
      /*  configuration = Configuration.createConfiguration("C", "", "-Wall -o", "","c");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);*/
    }


    @FXML
    public void cPPConfigOnAction(ActionEvent event) {
        chooseConfigLanguage.setText("C++");
        /*configMenuButton.setText("C#");
        configuration = Configuration.createConfiguration("C#", "csc", "-out:", "mono","c#");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);*/
    }

    @FXML
    public void pythonConfigOnAction(ActionEvent event) {
        chooseConfigLanguage.setText("PYTHON");
        /*configMenuButton.setText("Python");
        configuration = Configuration.createConfiguration("Python", "python", "", "python","python");
        configuration.saveConfiguration();

        project.setConfiguration(configuration);*/
    }



    @FXML
    public void runButton(ActionEvent event) throws IOException, InterruptedException {
        Compiler compiler = new Compiler();



        
        System.out.println("config name = " + project.getConfiguration().getConfigurationName());
        System.out.println("compiler path= "+project.getConfiguration().getCompilerPath());
        String codeOutput="";
        String language=project.getConfiguration().getLanguage();
        System.out.println(project.getConfiguration().getConfigurationName());
        System.out.println(language);





        if (Objects.equals(project.getConfiguration().getLanguage(), "JAVA")){
            language="JAVA";
            System.out.println("main file path: " + project.getSubmissionDirectoryPath());

            System.out.println("input " + projectInput.getText());

            String userInput = projectInput.getText();

            codeOutput = compiler.runJavaProgram(project.getSubmissionDirectoryPath(), userInput, project.getConfiguration().getCompilerPath());

            System.out.println("output: " + codeOutput);

            submissionOutputText.setText(codeOutput);
            submissionOutputText.getStyleClass().add("textFontSize");



        }else if (Objects.equals(project.getConfiguration().getLanguage(), "C")){
            language="C";
            System.out.println("C code running");
            String CInput = projectInput.getText();
            codeOutput = compiler.runCProgram(project.getSubmissionDirectoryPath(), CInput,project.getConfiguration().getCompilerPath());
            System.out.println("output: "+codeOutput);
            submissionOutputText.setText(codeOutput);
            submissionOutputText.getStyleClass().add("textFontSize");
        }else if (Objects.equals(project.getConfiguration().getLanguage(), "C++")){
            language="C++";
            System.out.println("C++ code running");
            String CPPInput = projectInput.getText();
            codeOutput = compiler.runCppProgram(project.getSubmissionDirectoryPath(), CPPInput,project.getConfiguration().getCompilerPath());
            System.out.println("output: "+codeOutput);
            submissionOutputText.setText(codeOutput);
            submissionOutputText.getStyleClass().add("textFontSize");
        }else if (Objects.equals(project.getConfiguration().getLanguage(), "PYTHON")){
            language="PYTHON";
            System.out.println("Python code running");
            String pythonInput = projectInput.getText();
            codeOutput = compiler.runPythonProgram(project.getSubmissionDirectoryPath(), pythonInput,project.getConfiguration().getCompilerPath());
            System.out.println("output: "+codeOutput);
            submissionOutputText.setText(codeOutput);
            submissionOutputText.getStyleClass().add("textFontSize");
        }


        String expectedOutput=project.expectedOutput(project.getExpectedOutputPath());
        expectedOutputText.setText(expectedOutput);
        expectedOutputText.getStyleClass().add("textFontSize");



        String result;
        if (Objects.equals(codeOutput, expectedOutput)) {
            result = "Success!";
            resultText.getStyleClass().add("success");
            resultText.getStyleClass().remove("error");
        } else {
            result = "Failed!";
            resultText.getStyleClass().add("error");
            resultText.getStyleClass().remove("success");
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
    public void chooseConfigButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Configuration");


        File initialDirectory = new File("Configurations");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

        Stage stage = (Stage) chooseExpectedOutpuButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {

            newProjectConfigName.setText(selectedFile.getAbsolutePath());
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
    public void pageCloseButton(ActionEvent event) {

        Stage stage = (Stage) configCreateOkButton.getScene().getWindow();
        stage.close();
    }




    @FXML
    public void importConfigButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");

        //bu ne amk niye choose expected??
        Stage stage = (Stage) importConfigurationButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);



        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String content=line.substring(line.indexOf(":") + 1).trim();
                    if (line.startsWith("Configuration Name:")) {
                        newConfigName.setText(content);
                    } else if (line.startsWith("Language:")) {
                        chooseConfigLanguage.setText(content);
                    }else if ((line.startsWith("Compiler Path:"))) {
                        newConfigPath.setText(content);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

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
    public void expectedOutputDirectoryChooseButton(ActionEvent event) {
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
        String config = newProjectConfigName.getText();
        String submissionDirectoryPath=submissionDirectoryTextField.getText();
        String expectedOutput = expectedOutputFileDirectoryTextField.getText();

        Configuration configuration1=new Configuration();

        try (BufferedReader okuyucu = new BufferedReader(new FileReader(newProjectConfigName.getText()))) {
            String satir;
            while ((satir = okuyucu.readLine()) != null) {
                if (satir.startsWith("Configuration Name:")) {
                    configuration1.setConfigurationName(satir.substring(satir.indexOf(":") + 1).trim());
                } else if (satir.startsWith("Language:")) {
                    configuration1.setLanguage(satir.substring(satir.indexOf(":") + 1).trim());
                }else if ((satir.startsWith("Compiler Path:"))) {
                    configuration1.setCompilerPath(satir.substring(satir.indexOf(":") + 1).trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        project.setConfiguration(configuration1);

        project.setProjectName(name);
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


        File projectFile = new File(directory, project.getProjectName()+"_"+project.getConfiguration().getLanguage() + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(projectFile))) {
            writer.write("Student ID: " + project.getProjectName());
            writer.newLine();
            writer.write("Configuration : " + newProjectConfigName.getText());
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
        projectNameTextField.setText(projectName);

        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);


        System.out.println("file: "+projectFile.getParent());
    }


    @FXML
    public void addNewConfigOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newConfiguration.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("IAE");
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
    }
    @FXML
    public void mainToNewProject(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newProjectToMain.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("IAE");
        newStage.setResizable(false);
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    public void newProjectToMain(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void chooseNewConfig(ActionEvent event){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose File");
        Stage stage = (Stage) newConfigChooseButton.getScene().getWindow();
        File selectedFile = directoryChooser.showDialog(stage);
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

        File initialDirectory = new File("Projects");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

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

                } else if (satir.startsWith("Configuration :")) {
                    String cfPath=satir.substring(satir.indexOf(":") + 1).trim();
                    //configuration.setConfigurationName(satir.substring(satir.indexOf(":") + 1).trim());
                    try (BufferedReader reader = new BufferedReader(new FileReader(cfPath))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("Configuration Name:")) {
                                configuration.setConfigurationName(line.substring(line.indexOf(":") + 1).trim());
                            } else if (line.startsWith("Language:")) {
                                configuration.setLanguage(line.substring(line.indexOf(":") + 1).trim());
                            } else if (line.startsWith("Compiler Path:")) {
                                configuration.setCompilerPath(line.substring(line.indexOf(":") + 1).trim());
                            }
                        }
                    }
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

        System.out.println("Name : " + projectName);
        System.out.println("Configuration : " + project.getConfiguration().getConfigurationName());
        System.out.println("MainFilePath : " + project.getSubmissionDirectoryPath());
        System.out.println("ExpectedOutput : " + project.getExpectedOutputPath());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();


        TextField projectNameTextField = (TextField) loader.getNamespace().get("projectNameTextField");
        projectNameTextField.setText(projectName);

        Scene scene = new Scene(root);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(scene);
        currentStage.centerOnScreen();
        currentStage.setResizable(false);

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
    public void removeConfigButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Stage stage = (Stage) configMenuButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        File initialDirectory = new File("Configurations");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }
        if (selectedFile != null) {
            Configuration configuration1=new Configuration();

            configuration1.removeConfiguration(selectedFile.getName());
            System.out.println("File: " + selectedFile.getAbsolutePath());
        }
    }



    @FXML
    public void mainPageOpenProject(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Choose File");File initialDirectory = new File("Projects");
        if (initialDirectory.exists()) {
            fileChooser.setInitialDirectory(initialDirectory);
        }

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
    public void backToNewProject() {
        Stage stage = (Stage) newConfigBackButton.getScene().getWindow();
        stage.close();
    }



    @FXML
    public void createConfigurationOnAction(ActionEvent event) {
        String configName = newConfigName.getText();
        String configLang = chooseConfigLanguage.getText();
        String configPath = newConfigPath.getText();
        File directory = new File("Configurations");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File configFile = new File(directory, configName + "_config.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            writer.write("Configuration Name: " + configName);
            writer.newLine();
            writer.write("Language: " + configLang);
            writer.newLine();
            writer.write("Compiler Path: " + configPath);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("createConfigPopUp.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);


            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.setTitle("Create Config");


            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            newStage.initOwner(currentStage);
            newStage.initModality(Modality.WINDOW_MODAL);


            newStage.centerOnScreen();
            newStage.setResizable(false);


            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}