package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import sample.Main;

public class BookingPageController {
    private static String airportString;
    @FXML
    BorderPane borderPaneSeats;
    @FXML
    VBox vBoxFromTo;
    @FXML
    ImageView imgAirplaneNose;
    @FXML
    ImageView imgAirplaneTail;
    @FXML
    GridPane gridSeats;

    public void initialize() throws IOException {
        Image airplaneNose = new Image("resources/airplaneNose.png");
        Image airplaneTail = new Image("resources/airplaneTail.png");
        imgAirplaneNose.setImage(airplaneNose);
        imgAirplaneTail.setImage(airplaneTail);
        populateSeats();
    }


    private void populateSeats(){
        int nr = 0;
        for(int i=0; i<gridSeats.getColumnCount(); i++){
            for(int j=0; j<gridSeats.getRowCount(); j++){
                if(j==2){ continue; }
                nr++;
                Button seat = new Button(""+nr);
                seat.setOnAction(this::choseSeat);
                seat.setMaxWidth(Double.MAX_VALUE);
                seat.getStylesheets().add("resources/bookingSeats.css");
                seat.getStyleClass().add("seat");
                gridSeats.add(seat,i,j);
            }
        }
    }

    public void choseSeat(ActionEvent actionEvent) {
        for (Node seat : gridSeats.getChildren()) {
                if (((Button) seat).equals(actionEvent.getSource())) {
                    seat.setDisable(true);
                }
                else {
                    seat.setDisable(false);
                }
            }
            System.out.println("You have chosen seat nr: " + ((Button) actionEvent.getSource()).getText());
    }

    public void nextStep(ActionEvent actionEvent) {
        vBoxFromTo.setVisible(false);
        borderPaneSeats.setVisible(true);
        airportString = Main.getAirportList();
    }
}
