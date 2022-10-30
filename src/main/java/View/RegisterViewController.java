package View;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class RegisterViewController {
    @FXML
    Rectangle SendButton;

    @FXML
    Rectangle BackButton;

    @FXML
    TextArea NameArea;

    @FXML
    TextArea PasswordArea;

    @FXML
    Text StatusText;

    @FXML
    Rectangle LogInButton;

    long mLastRegisterTime = 0;

    public static String mUserID;
    @FXML
    public void initialize() {

        BackButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openContactView();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        LogInButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openLogInView();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        SendButton.setOnMouseClicked(ev -> {
            if (mUserID == null && System.currentTimeMillis() > mLastRegisterTime+10000) {
                MessengerGui.mEchoClient.send("NewUser:" + NameArea.getText() + ":" + PasswordArea.getText());
                mLastRegisterTime = System.currentTimeMillis();
                StatusText.setText("Request Send.....Please Try again");

            }
            else if (mUserID != null) {
                StatusText.setText("Successfully created: " + NameArea.getText() + " with PhoneNumber: " + mUserID);

            }
            else StatusText.setText("Creation denied");
        });

    }
}
