package View;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.Random;

public class ContactViewController {

    @FXML
    public VBox mVBox;

    @FXML
    Rectangle LogInButton;

    @FXML
    Rectangle AddContactButton;

    @FXML
    Text UserText;

    //public Rectangle test01;

    @FXML
    public void initialize() {
        MessengerGui.mEchoClient.mConnector.executeStatement("Select Name from User where ID = " + MessengerGui.mCurrentLoggedUserID);
        if (MessengerGui.mEchoClient.mConnector.getCurrentQueryResult() != null) UserText.setText(MessengerGui.mEchoClient.mConnector.getCurrentQueryResult().getData()[0][0]);

        MessengerGui.mEchoClient.mConnector.executeStatement("Select * from User");


        String[][] lCurrentUserResult = MessengerGui.mEchoClient.mConnector.getCurrentQueryResult().getData();
        //for (int i = 0; i< Math.round(lCurrentUserResult.length); i++) System.out.println(lCurrentUserResult[i][0]);
        mVBox.setSpacing(10);

        int lRoundedLength = lCurrentUserResult.length/3;
        if ((lCurrentUserResult.length/3)%1 <  1 ) lRoundedLength = 1 + Math.round(lCurrentUserResult.length/3);
        //System.out.println("lRoundedLength: "+lRoundedLength);
        for (int i = 0; i < lRoundedLength; i++) {
            //System.out.println(i);
            HBox lHBox = new HBox();
            lHBox.setSpacing(20);
            int lHBoxLength = 3;

            if ((lCurrentUserResult.length-i*3) < 3) lHBoxLength = lCurrentUserResult.length-i*3;
            //System.out.println("lHBoxLength: "+lHBoxLength);
            for (int n = 0; n < lHBoxLength; n++) {
                //System.out.println(n);
                Pane lPane = new Pane();
                Rectangle lRectangle = new Rectangle();
                Random rand = new Random();
                lRectangle.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                lRectangle.setArcHeight(10);
                lRectangle.setArcWidth(10);
                lRectangle.setHeight(63);
                lRectangle.setWidth(136.666);


                lRectangle.setOnScroll(ev -> {
                    Random lRand = new Random();
                    lRectangle.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                });
                int lCurrentUser = i*3 + n;
                lRectangle.setOnMouseClicked(ev -> {
                    try {
                        MessengerGui.openChatView(lCurrentUserResult[lCurrentUser][1]);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


                lHBox.getChildren().add(lPane);
                lPane.getChildren().add(lRectangle);
                Text lText = new Text(lRectangle.getX()+lRectangle.getWidth()/2,lRectangle.getY()+lRectangle.getHeight()/2,lCurrentUserResult[i*3 + n][0]);
                lText.setX(lText.getX()-lCurrentUserResult[i*3 + n][0].toCharArray().length*4);
                lPane.getChildren().add(lText);


                /*Canvas lCanvas = new Canvas();
                lHBox.getChildren().add(lCanvas);
                GraphicsContext lGC = lCanvas.getGraphicsContext2D();
                Random rand = new Random();
                lGC.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                lGC.fillRoundRect(0, 0, 136.666, 63, 10, 10);
                lGC.strokeText(lCurrentUserResult[i + n][0], 68.333, 31.5);*/
            }
            mVBox.getChildren().add(lHBox);


        }

        LogInButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openLogInView();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        AddContactButton.setOnMouseClicked(ev -> {
            try {
                MessengerGui.openAddContactView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

    /*@FXML
    public Rectangle test01;

    @FXML
    public void initialize()
    {
        test01.setOnMouseClicked(ev -> {
            Random rand = new Random();
            test01.setFill(Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));
        });
    }*/


