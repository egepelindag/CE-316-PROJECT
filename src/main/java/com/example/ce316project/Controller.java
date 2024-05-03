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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    private TextField expectectedOutpuFileDirectoryTextField;
    @FXML
    private Button newConfigButton;

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
        //configurationun libraryler gelecek
    }
    @FXML
    public void cConfigMenuOnAction(ActionEvent event) {
        configMenuButton.setText("C");
        //configurationun libraryler gelecek

    }
    @FXML
    public void cSharpConfigOnAction(ActionEvent event) {
        configMenuButton.setText("C#");
        //configurationun libraryler gelecek

    }
    @FXML
    public void pythonConfigOnAction(ActionEvent event) {
        configMenuButton.setText("Python");
        //configurationun libraryler gelecek

    }

    //ödev yükleme, ödev seçme
    @FXML
    private void addNewConfigurationButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dosya Seç");
        Stage stage = (Stage) newConfigButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            System.out.println("Seçilen dosya: " + selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void submissionDirectoryChooseButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dosya Seç");
        Stage stage = (Stage) chooseSubmissionButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            submissionDirectoryTextField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void expectecOutputDirectoryChooseButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Dosya Seç");
        Stage stage = (Stage) chooseExpectedOutpuButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            expectectedOutpuFileDirectoryTextField.setText(selectedFile.getAbsolutePath());
        }
    }
    //newProject.fxml kısmı bitiş

}
