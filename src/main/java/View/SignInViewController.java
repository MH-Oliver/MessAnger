package View;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

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
    Text StatusText;

    public static boolean mLoginSucessful;
    @FXML
    public void initialize() {
        mLoginSucessful = false;
        BackButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openContactView();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        SendButton.setOnMouseClicked(ev -> {
            StatusText.setText("Wait a second...");
            String lID = PhoneNumberArea.getText();
            MessengerGui.mEchoClient.send("ANMELDUNG:"+PhoneNumberArea.getText()+":"+PasswordArea.getText());
            long lCurrentTime = System.currentTimeMillis();
            while (System.currentTimeMillis() < lCurrentTime+5000) {
                if (mLoginSucessful) {
                    mLoginSucessful = false;
                    System.out.println("Login:" +  lID);
                    MessengerGui.mCurrentLoggedUserID = lID;
                    try {
                        MessengerGui.openContactView();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
            }
            StatusText.setText("User not found, please try again");
        });
    }
}
