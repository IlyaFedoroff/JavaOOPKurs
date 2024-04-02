package salon;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class SecondaryController {

    @FXML
    private Text statText;

    @FXML
    private void switchToPrimary() throws IOException{
        App.setRoot("primary");

    }

    
}
