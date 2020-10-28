package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.SQLException;

public class HomePageController {

    @FXML
    ImageView flightIcon;

    @FXML
    public void initialize() throws SQLException, IOException {
        Image image = new Image("resources/flight.png");
        flightIcon.setImage(image);
    }
}
