package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.AutoCompleteComboBoxListener;
import sample.Main;
import java.util.ArrayList;

public class BookingPageController {
    ObservableList<String> airportList;
    Boolean seatChosen = false;

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
    @FXML
    ComboBox comboBoxAirportsFrom;
    @FXML
    ComboBox comboBoxAirportsTo;
    @FXML
    Text txtRoute;
    @FXML
    Text txtSeat;

    public void initialize() {
        new Thread(this::fetchImages).start();
        loadComboBoxes();
        populateSeats();
    }

    private void fetchImages(){
        Image airplaneNose = new Image("resources/airplaneNose.png");
        Image airplaneTail = new Image("resources/airplaneTail.png");
        imgAirplaneNose.setImage(airplaneNose);
        imgAirplaneTail.setImage(airplaneTail);
    }

    private void loadComboBoxes(){
        ArrayList<String> airList = Main.getAirportList();
        for(int i=0; i<airList.size(); i++){
            airList.set(i,airList.get(i).replace("[",""));
            airList.set(i,airList.get(i).replace("]",""));
            airList.set(i,airList.get(i).replaceAll("[(){}\"]",""));
            airList.set(i,airList.get(i).replaceAll(",(\\S)",", $1"));
            if(airList.get(i).endsWith(",")){
                airList.set(i,airList.get(i).substring(0,airList.get(i).length()-1));
            }
        }
        airportList = FXCollections.observableArrayList(airList);
        comboBoxAirportsFrom.setItems(airportList);
        comboBoxAirportsTo.setItems(airportList);
        comboBoxAirportsFrom.getProperties().put("comboBoxRowsToMeasureWidth", 30);
        comboBoxAirportsTo.getProperties().put("comboBoxRowsToMeasureWidth", 30);
        new AutoCompleteComboBoxListener<>(comboBoxAirportsTo);
        new AutoCompleteComboBoxListener<>(comboBoxAirportsFrom);
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
        for (Node seat : gridSeats.getChildren()) { seat.setDisable(((Button) seat).equals(actionEvent.getSource())); }
        txtSeat.setText("You have chosen seat nr: " + ((Button) actionEvent.getSource()).getText());
        seatChosen = true;
    }

    public void seatContinue(ActionEvent actionEvent) {
        if(seatChosen){
            
        }else{
            txtSeat.setText("Please chose a seat first");
        }
    }

    public void nextStep(ActionEvent actionEvent) {
        txtRoute.setText("Please choose a seat for your route:\n"+comboBoxAirportsFrom.getValue()+" - "+comboBoxAirportsTo.getValue());
        vBoxFromTo.setVisible(false);
        borderPaneSeats.setVisible(true);
    }
}