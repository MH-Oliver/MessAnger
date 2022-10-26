package View;

import DataStructure.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.jar.Attributes;

public class AddContactViewController {
    @FXML
    Rectangle SearchButton;

    @FXML
    Rectangle BackButton;

    @FXML
    TextArea PhoneNumberArea;

    @FXML
    TextArea NameArea;


    @FXML
    Text StatusText;

    public void initialize() {
        BackButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openContactView();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        SearchButton.setOnMouseClicked(ev -> {
            StatusText.setText("Wait a second...");
            DatabaseConnector lConnector = MessengerGui.mEchoClient.mConnector;
            lConnector.executeStatement("Insert into User values ('" + NameArea.getText() + "' , '" + PhoneNumberArea.getText() + "')");
            StatusText.setText("Finished");
            try {
                MessengerGui.openContactView();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }
}
