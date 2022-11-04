package Client;


import DataStructure.DatabaseConnector;
import View.MessengerGui;
import View.RegisterViewController;
import View.SignInViewController;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MessengerClient extends Client {
    public DatabaseConnector mConnector = new DatabaseConnector("", 0, "src/main/resources/Client/ClientDB.db", "", "");
    public String mCurrentUserID;
    public MessengerClient(String pServerIP, int pServerPort) {
        super(pServerIP, pServerPort);

    }
    @Override
    public void processMessage(String pMessage) {
        String[] lSplittedMessage = pMessage.split(":");

        //uebertrageAufGui(lSplittedMessage[1]);
        if (lSplittedMessage[0].equals("Nachricht")) {
            if (lSplittedMessage[5].equals(mCurrentUserID))  send("Received:"+lSplittedMessage[4]);
            //Überprüfen ob Sender bereits in Datenbank gespeichert
            mConnector.executeStatement("Select Name from User where ID = '" + lSplittedMessage[3] + "'");
            System.out.println("ID: " + lSplittedMessage[3]);
            if (mConnector.getCurrentQueryResult() == null) {
                mConnector.executeStatement("Insert into User values ('"+lSplittedMessage[3]+"' , '" + lSplittedMessage[3] +"')");
            }
            mConnector.executeStatement("Select MAX(ID) from Message");
            int lNewID = 1;
            if (mConnector.getCurrentQueryResult().getData()[0][0] != null) lNewID = Integer.parseInt(mConnector.getCurrentQueryResult().getData()[0][0]) + 1;
            mConnector.executeStatement("Insert into Message values ('" + lSplittedMessage[1] + "' , '" + lSplittedMessage[2] +  "' , '" + lNewID + "' , ' " + lSplittedMessage[3] +  "' , ' " + lSplittedMessage[5] + "')");

            System.out.println("Nachricht vom Server: " + lSplittedMessage[1] + ": " + lSplittedMessage[3]);
        }
        else if (lSplittedMessage[0].equals("ErfolgreichHerrgestellt")) {
            //System.out.println("Verbindung Herrgestellt");
            mCurrentUserID = lSplittedMessage[1];
            SignInViewController.mLoginSucessful = true;
        }

        else if (lSplittedMessage[0].equals("SucessfullCreatet")) {
            RegisterViewController.mUserID = lSplittedMessage[2];
        }
        System.out.println(pMessage);
    }

    public void uebertrageAufGui(String pContent) {



        HBox lHBox = new HBox();
        lHBox.setSpacing(50);

        Pane lPane = new Pane();
        Rectangle lRectangle = new Rectangle();

        Text lText = new Text();
        lText.setText(pContent);

        lRectangle.setArcHeight(10);
        lRectangle.setArcWidth(10);
        lRectangle.setHeight(50);

        lRectangle.setFill(Color.LIGHTBLUE);
        if (lText.getText().toCharArray().length * 8 < 212.5)
            lRectangle.setWidth(lText.getText().toCharArray().length * 4 + 40);
        else lRectangle.setWidth(207.5);

        lHBox.getChildren().add(lPane);
        lPane.getChildren().add(lRectangle);
        lPane.getChildren().add(lText);
        lText.setX(lRectangle.getX() + lRectangle.getWidth() / 2 - lText.getText().toCharArray().length * 4);
        lText.setY(lRectangle.getY() + lRectangle.getHeight() / 2);

        //Teil 2
        Text lNameText = new Text();
        //mConnector.executeStatement("Select Name from User where ID = '" + MessengerGui.mCurrentContactID + "'");
        //lNameText.setText(lConnector.getCurrentQueryResult().getData()[0][0]);
        lNameText.setText("                                            ");
        lHBox.getChildren().add(lNameText);
        MessengerGui.mChatViewController.MessageField.getChildren().add(lHBox);
    }

}
