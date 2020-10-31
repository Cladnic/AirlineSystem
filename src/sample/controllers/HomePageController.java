package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import java.io.IOException;
import sample.Main;

public class HomePageController {
    @FXML
    ImageView flightIcon;
    @FXML
    HBox hBoxGlobalNav;

    public void initialize(){
        Image image = new Image("resources/flight.png");
        flightIcon.setImage(image);
    }

    public void selectScene(ActionEvent actionEvent) throws IOException{
        switch (((Button)actionEvent.getSource()).getText()) {
            case "Settings"-> Main.loadCenter("fxml/SettingsPage.fxml");
            case "Overview"-> Main.loadCenter("fxml/OverviewPage.fxml");
            case "Booking" -> Main.loadCenter("fxml/BookingPage.fxml");
            case "Help" -> Main.loadCenter("fxml/SettingsPage.fxml");
            default -> Main.showMainView();
        }
        for (Node btnSceneSelect : hBoxGlobalNav.getChildren()) {
            btnSceneSelect.setDisable(false);
            if ((actionEvent.getSource().equals(btnSceneSelect))) {
                btnSceneSelect.setDisable(true);
            }
        }
    }
}