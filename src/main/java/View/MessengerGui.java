package View;

import Client.EchoClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;


public class MessengerGui extends Application {
    static EchoClient mEchoClient = new EchoClient("localhost",1);;
    public static String mCurrentContactID;

    public static ChatViewController getmChatViewController() {
        return mChatViewController;
    }

    public static SignInViewController getmSignInViewController() {
        return mSignInViewController;
    }

    public static SignInViewController mSignInViewController;

    static String mCurrentLoggedUserID;

    public static ChatViewController mChatViewController;

    static Scene mAddContactView;

    public static AddContactViewController mAddContactViewController;

    public static RegisterViewController mRegisterViewController;

    static Scene mRegisterView;
    static Scene mContactView;
    static Scene mChatView;

    static  Scene mSignInView;
    static Stage mStage;
    @Override
    public void start(Stage stage) throws Exception {
        mCurrentLoggedUserID = "0";


        mStage = stage;
        mStage.setTitle("Messenger");
        openLogInView();

    }

    public static void openChatView(String pUserID) throws Exception {
        mCurrentContactID = pUserID;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MessengerGui.class.getResource("ChatView.fxml"));
        mChatViewController = loader.getController();
        mChatView = new Scene(loader.load());
        mStage.setScene(mChatView);
        mStage.show();
    }

    public static void openContactView() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MessengerGui.class.getResource("ContactView.fxml"));
        mContactView = new Scene(loader.load());
        mStage.setScene(mContactView);
        mStage.show();
    }

    public static void openLogInView() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MessengerGui.class.getResource("SignInView.fxml"));
        mSignInViewController = loader.getController();
        mSignInView = new Scene(loader.load());
        mStage.setScene(mSignInView);
        mStage.show();
    }

    public static void openAddContactView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MessengerGui.class.getResource("AddContactView.fxml"));
        mAddContactViewController = loader.getController();
        mAddContactView = new Scene(loader.load());
        mStage.setScene(mAddContactView);
        mStage.show();
    }

    public static void openRegisterView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MessengerGui.class.getResource("RegisterView.fxml"));
        mRegisterViewController = loader.getController();
        mRegisterView = new Scene(loader.load());
        mStage.setScene(mRegisterView);
        mStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        mEchoClient.close();
        System.out.println("Fertig");
    }

}



/*Rectangle lRectangle = new Rectangle();
                Random rand = new Random();
                lRectangle.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
                lRectangle.setArcHeight(10);
                lRectangle.setArcWidth(10);
                lRectangle.setHeight(63);
                lRectangle.setWidth(136.666);

                lHBox.getChildren().add(lRectangle);*/





















