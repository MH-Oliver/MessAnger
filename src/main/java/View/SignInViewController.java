package View;

import DataStructure.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;

public class SignInViewController {

    @FXML
    Rectangle SendButton;

    @FXML
    Rectangle BackButton;

    @FXML
    TextArea PhoneNumberArea;

    @FXML
    TextArea PasswordArea;

    @FXML
    TextArea NameArea;

    @FXML
    Text StatusText;

    @FXML
    Rectangle RegisterButton;

    public static boolean mLoginSucessful;
    @FXML
    public void initialize() {
        MessengerGui.mCurrentLoggedUserID = "0";
        mLoginSucessful = false;
        BackButton.setOnMouseClicked(ev -> {
            if (!MessengerGui.mCurrentLoggedUserID.equals("0")) {
                try {
                    MessengerGui.openContactView();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        SendButton.setOnMouseClicked(ev -> {
            StatusText.setText("Wait a second...");
            String lID = PhoneNumberArea.getText();
            MessengerGui.mEchoClient.send("ANMELDUNG:"+PhoneNumberArea.getText()+":"+PasswordArea.getText());

                if (mLoginSucessful) {
                    mLoginSucessful = false;
                    System.out.println("Login:" +  lID);
                    MessengerGui.mCurrentLoggedUserID = lID;

                    DatabaseConnector lConnector = MessengerGui.mEchoClient.mConnector;
                    lConnector.executeStatement("Select Name from User where ID = " + lID);
                    if (lConnector.getCurrentQueryResult().getData().length == 0) lConnector.executeStatement("Insert into User values ('" + NameArea.getText() + "'," + lID + ")");
                    else lConnector.executeStatement("Update User set Name ='" + NameArea.getText() + "' where ID = " + lID);
                    try {
                        MessengerGui.openContactView();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                }
                else {
                    StatusText.setText("User not found, please try again");
                }


        });

        RegisterButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openRegisterView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }




}
