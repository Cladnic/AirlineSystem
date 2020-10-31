package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main extends Application {
    private static BorderPane mainLayout;
    private static Stage primaryStage;
    private static String apiOutput;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //get airport list on a new thread
        new Thread(Main::airportApiFetch).start();

        this.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/HomePage.fxml"));
        mainLayout=loader.load();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/HomePage.fxml"));
        primaryStage.setTitle("Johans Airline booking");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        showMainView();
    }

    public static void loadCenter(String resourceDirec) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(resourceDirec));
        BorderPane centerView = loader.load();
        mainLayout.setCenter(centerView);
    }

    public static void showMainView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/HomePage.fxml"));
        mainLayout=loader.load();
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static String getAirportList(){
        return apiOutput;
    }

    //get list of airports from API
    private static void airportApiFetch(){
        URL url;
        HttpURLConnection conn = null;
        BufferedReader br;
        try {
            url = new URL("https://api.easypnr.com/v4/airports");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("X-Api-Key", "NfAAXOwfDztlHDamHRGrnprKPprHQV");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            apiOutput = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            assert conn != null;
            conn.disconnect();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
