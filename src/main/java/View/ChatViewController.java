package View;

import DataStructure.DatabaseConnector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class ChatViewController {
    @FXML
    Rectangle BackButton;

    @FXML
    Text ContactField;

    @FXML
    public VBox MessageField;

    @FXML
    TextArea WriteField;

    @FXML
    Button SendButton;

    String latestMessageTime = "0";

    @FXML
    public void initialize() {

        BackButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openContactView();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        MessageField.setSpacing(20);

        DatabaseConnector lConnector = MessengerGui.mEchoClient.mConnector;
        lConnector.executeStatement("Select Name from User where ID = '" + MessengerGui.mCurrentContactID + "'");

        ContactField.setText(lConnector.getCurrentQueryResult().getData()[0][0]);
        System.out.println("Logged: " + MessengerGui.mCurrentLoggedUserID);
        System.out.println("Chat: " + MessengerGui.mCurrentContactID);
        lConnector.executeStatement("Select * from Message where (SenderID = " + MessengerGui.mCurrentContactID + " OR SenderID = " + MessengerGui.mCurrentLoggedUserID +  ") AND (EmpfaengerID = " + MessengerGui.mCurrentContactID + " OR EmpfaengerID = " + MessengerGui.mCurrentLoggedUserID + ") ORDER BY Time ASC");
        //System.out.println(lConnector.getErrorMessage());
        addNewMessages(lConnector);

        //Weiter mit sonstigen Buttons

        SendButton.setOnMouseClicked(ev -> {
            MessengerGui.mEchoClient.send("SendeAnNachricht:" + MessengerGui.mCurrentContactID + ":"+WriteField.getText());
        });

        Timeline t1 = new Timeline();
        KeyFrame keyframe =
                new KeyFrame(Duration.seconds(2), ev -> {
                    MessengerGui.mEchoClient.send("GetMessages:" + MessengerGui.mCurrentLoggedUserID);

                    lConnector.executeStatement("Select Name from User where ID = '" + MessengerGui.mCurrentContactID + "'");

                    ContactField.setText(lConnector.getCurrentQueryResult().getData()[0][0]);
                    //System.out.println(latestMessageTime);

                    lConnector.executeStatement("Select * from Message where ((SenderID = " + MessengerGui.mCurrentContactID + " OR SenderID = " + MessengerGui.mCurrentLoggedUserID + ") AND (EmpfaengerID = " + MessengerGui.mCurrentContactID + " OR EmpfaengerID = " + MessengerGui.mCurrentLoggedUserID + ")) AND Time > " +latestMessageTime+ " ORDER BY Time ASC");
                    addNewMessages(lConnector);




                });
        t1.getKeyFrames().add(keyframe);
        t1.setCycleCount(Timeline.INDEFINITE);
        t1.play();






                }

    private void addNewMessages(DatabaseConnector lConnector) {


        String[][] lNewMessageResult = lConnector.getCurrentQueryResult().getData();
        for (int i = 0; i < lNewMessageResult.length; i++) {
            //System.out.println(lNewMessageResult[i][3]);
            if (Long.parseLong(latestMessageTime) < Long.parseLong(lNewMessageResult[i][1])) latestMessageTime = lNewMessageResult[i][1];
            if (lNewMessageResult[i][3].equals(MessengerGui.mCurrentContactID)) {
                // denk an doppeltes Füllen damit immer eine Nachricht eine ganze Zeile füllt (Nachricht - Text)
                HBox lHBox = new HBox();
                lHBox.setSpacing(50);

                Pane lPane = new Pane();
                Rectangle lRectangle = new Rectangle();

                Text lText = new Text();
                lText.setText(lNewMessageResult[i][0]);

                lRectangle.setArcHeight(10);
                lRectangle.setArcWidth(10);
                lRectangle.setHeight(50);

                lRectangle.setFill(Color.LIGHTGREEN);
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
                lConnector.executeStatement("Select Name from User where ID = '" + MessengerGui.mCurrentContactID + "'");
                //lNameText.setText(lConnector.getCurrentQueryResult().getData()[0][0]);
                lNameText.setText("                                            ");
                lHBox.getChildren().add(lNameText);

                MessageField.getChildren().add(lHBox);
            }
            else {

                HBox lHBox = new HBox();
                lHBox.setSpacing(50);

                Text lNameText = new Text();
                lConnector.executeStatement("Select Name from User where ID = '" + MessengerGui.mCurrentLoggedUserID + "'");
                //lNameText.setText(lConnector.getCurrentQueryResult().getData()[0][0]);
                lNameText.setText("                                            ");
                lHBox.getChildren().add(lNameText);


                Pane lPane = new Pane();
                Rectangle lRectangle = new Rectangle();

                Text lText = new Text();
                lText.setText(lNewMessageResult[i][0]);

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


                MessageField.getChildren().add(lHBox);
            }
        }
    }
    }

