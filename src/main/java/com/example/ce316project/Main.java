package com.example.ce316project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {

    static String file;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("welcomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 525, 476);
        stage.setTitle("IAE");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
        /*primaryStage.setTitle("Dosya Seç");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Proje Dosyasını Seç");

        // Kullanıcıya sadece dosya seçme izni vermek için:
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        file=selectedFile.getAbsolutePath();
        // Seçilen dosyanın adını veya yolu almak için:
        if (selectedFile != null) {
            System.out.println("Seçilen dosya: " + selectedFile.getAbsolutePath());
            // Burada dosyayı kullanarak yapmak istediğiniz işlemi gerçekleştirebilirsiniz.
        }

        run(file);*/
    }

    public static void main(String[] args) {
        Configuration configuration=new Configuration();
            launch();

    }

    public static void run(String file1){
        //  String cFilePath = "C:\\Users\\ahmet\\Desktop\\c\\deneme\\main.c";
        String java="C:\\Users\\ahmet\\Desktop\\proje\\proje.java";
        String python="C:\\Users\\ahmet\\PycharmProjects\\pythonProject\\main.py";

        String cFilePath;
        Scanner scan=new Scanner(System.in);
        System.out.println("enter path:");
        cFilePath= scan.next();
        // Bu fonksiyon, komut satırında C programını derleyip çalıştırmak için kullanılır
        runCProgram(file1);
        runJavaProgram(java);
        runPythonProgram(python);
    }

    // C programını derleyip çalıştıran fonksiyon
    private static void runCProgram(String cFilePath) {
        try {
            // Derleyici komutu
            String compilerCommand = "gcc " + cFilePath + " -o program.exe";

            // Derleme işlemini gerçekleştir
            Process compileProcess = Runtime.getRuntime().exec(compilerCommand);
            compileProcess.waitFor();

            // Eğer derleme başarısız olduysa
            if (compileProcess.exitValue() != 0) {
                System.out.println("C programı derlenirken hata oluştu.");
                return;
            }

            // C programını çalıştır
            Process runProcess = Runtime.getRuntime().exec("program.exe");

            // Çalışma işlemini oku
            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Çalışma işlemini bekle
            runProcess.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    // Java programını derleyip çalıştıran fonksiyon
    private static void runJavaProgram(String javaFile) {
        try {
            // Java dosyasının derleneceği klasör
            String parentDir = new File(javaFile).getParent();

            // Java dosyasını derleme komutu
            String compileCommand = "javac " + javaFile;

            // Derleme işlemini gerçekleştir
            Process compileProcess = Runtime.getRuntime().exec(compileCommand);
            compileProcess.waitFor();

            // Eğer derleme başarısız olduysa
            if (compileProcess.exitValue() != 0) {
                System.out.println("Java programı derlenirken hata oluştu.");
                return;
            }

            // Java programını çalıştırma komutu
            String runCommand = "java -cp \"" + parentDir + "\" " + new File(javaFile).getName().replace(".java", "");

            // Java dosyasını çalıştır
            Process runProcess = Runtime.getRuntime().exec(runCommand);

            // Çalışma işleminin çıktısını oku
            BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Çalışma işleminin sonunu bekle
            runProcess.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void runPythonProgram(String pyFile) {
        try {
            // Python komutunu oluştur
            ProcessBuilder pb = new ProcessBuilder("python", pyFile);

            // Çalışma dizini
            pb.directory(new java.io.File(pyFile).getParentFile());

            // Çalışma işlemini başlat
            Process p = pb.start();

            // Çalışma işleminin çıktısını oku
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Çalışma işleminin sonunu bekle
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Zip dosyalarının işlenmesi
    private static void processZipFile(File zipFile, String studentId) {
        try {
            ZipFile file = new ZipFile(zipFile);
            file.stream().forEach(entry -> {
                try {
                    // Her zip girdisini işle
                    processZipEntry(file, entry, studentId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Zip dosyası içindeki her girdinin işlenmesi
    private static void processZipEntry(ZipFile zipFile, ZipEntry entry, String studentId) throws IOException {
        String fileName = entry.getName();
        if (fileName.endsWith(".c")) {
            // main.c dosyasını işle
            // Burada main.c dosyasının içeriğini okuyup işleyebilirsiniz
            System.out.println("Öğrenci " + studentId + " için " + fileName + " dosyası işleniyor.");
        }
    }

    // Öğrenci klasörlerinin oluşturulması
    private static void createStudentFolder(String studentId) {
        File directory = new File("students/" + studentId);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Öğrenci klasörü oluşturuldu: " + directory.getPath());
        }
    }
}
