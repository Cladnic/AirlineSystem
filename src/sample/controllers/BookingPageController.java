package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class BookingPageController {
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

    public void initialize(){
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
                if(j==2){continue;}
                nr++;
                Button seat = new Button(""+nr);
                seat.setMaxWidth(Double.MAX_VALUE);
                seat.getStylesheets().add("resources/bookingSeats.css");
                seat.getStyleClass().add("seat");
                gridSeats.add(seat,i,j);
            }
        }
    }

    public void nextStep(ActionEvent actionEvent) {
        vBoxFromTo.setVisible(false);
        borderPaneSeats.setVisible(true);
    }
}
