package salon;

import salon.lab5.*;
import salon.lab5.classes.FemaleRequest;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.CheckBox;
import javafx.geometry.Orientation;
import javafx.geometry.Insets;
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import salon.PrimaryController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;




    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 900, 600);
        // задаем название
        stage.setTitle("Моделирование заявок в парикмахерскую");

        // устанавливаем иконку приложения
        Image image = new Image(getClass().getResourceAsStream("/salon/barber.jpg"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();

    }


    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
