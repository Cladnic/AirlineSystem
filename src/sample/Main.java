package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main extends Application {
    private static BorderPane mainLayout;
    private static Stage primaryStage;
    private static final ArrayList<String> airportList = new ArrayList<>();
    private static String[] tempAirport = null;

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

    public static ArrayList<String> getAirportList(){
        return airportList;
    }

    //get list of airports from API
    private static void airportApiFetch() {
        boolean API = false;
        URL url;
        HttpURLConnection conn = null;
        BufferedReader br;
        try {
            if (API) {
                url = new URL("https://api.easypnr.com/v4/airports");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("X-Api-Key", "NfAAXOwfDztlHDamHRGrnprKPprHQV");
            }

            if (!API || conn.getResponseCode() != 200) {
                try (FileReader f = new FileReader("src/resources/Airports.csv")) {
                    StringBuffer sb = new StringBuffer();
                    while (f.ready()) {
                        char c = (char) f.read();
                        if (c == '\n') {
                            airportList.add(sb.toString());
                            sb = new StringBuffer();
                        } else {
                            sb.append(c);
                        }
                    }
                    if (sb.length() > 0) {
                        airportList.add(sb.toString());
                    }
                }
                throw new RuntimeException("Failed : HTTP error code : look at this row" + /*conn.getResponseCode()*/  "\nIf error code is 500 it means daily requests has been exceeded\n-> Local file has been used instead of API and program should still work");
            }

            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String read = br.readLine();
            tempAirport = read.split("[{]");
            for (String part : tempAirport){
                if(!part.equals(tempAirport[0])) {
                    airportList.add(part);
                }
            }
        } catch (IOException protocolException) {
            protocolException.printStackTrace();
        }finally {
            if(API) {
                assert conn != null;
                conn.disconnect();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}