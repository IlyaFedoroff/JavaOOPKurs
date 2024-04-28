package salon;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import salon.lab5.*;

public class PrimaryController {

    @FXML
    private Button showTimeButton;

    @FXML
    private Button startSimulationButton;

    @FXML
    private Button stopSimulationButton;

    @FXML
    private Text timeText;

    @FXML
    private Pane visualisationPane;

    @FXML
    private Text statText;

    @FXML
    private Pane statPane;


    private Habitat habitat;



    // private double FIELD_WIDTH = visualisationPane.getWidth(); // Ширина области визуализации
    // private double FIELD_HEIGHT = visualisationPane.getHeight(); // Высота области визуализации


    @FXML
    private void initialize(){
        habitat = new Habitat(visualisationPane, timeText, statText);
        //timeText = new Text();
        //statText = new Text();
        statText.setText("ожидание...");
        Pane statPane = new Pane();
        statPane.setVisible(false);

        //statText.setVisible(false);

    }

    @FXML
    void showTime(ActionEvent event) {
        timeText.setVisible(!timeText.isVisible()); // Изменяем видимость текста на противоположное его текущему состоянию

    }

    @FXML
    void startSimulation(ActionEvent event) {
        statText.setText("ожидание...");
        habitat.startSimulation();
    }

    @FXML
    private void switchToSecondary() throws IOException{
        App.setRoot("secondary");
    }

    @FXML
    void stopSimulation(ActionEvent event) {
        habitat.stopSimulation();
        statPane.setVisible(true);
    }






}
