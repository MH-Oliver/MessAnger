package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientMain {
    MessengerClient mClient;
    //public static DataStructure.DatabaseConnector connector = new DataStructure.DatabaseConnector("", 0, "User.db", "", "");

    public ClientMain() {
        mClient = new MessengerClient("localhost",1);
    }

    public static void main(String[] args) throws IOException {
        ClientMain lSoftware  = new ClientMain();

        System.out.println("         Welcome to the MessAnger Console");
        System.out.println("To USE the MessAnger you have the following commands:");
        System.out.println("ANMELDUNG:<Your Phone Number>:<Your Password>  ");
        System.out.println("SendeAnNachricht:<Contact Phone Number>:<Message>");
        System.out.println("GetMessages");
        System.out.println("NewUser:<Any Name>:<Any Password>");
        System.out.println("ABMELDUNG");
        System.out.println("----------------------------------------------------");
        System.out.println("Now you can type in your commands: ");
        System.out.println();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals("Close")) {
            input = in.readLine();
            if (!input.equals(null)) {
                lSoftware.mClient.send(input);
            }
        }
        lSoftware.mClient.send("ABMELDUNG");
        lSoftware.mClient.close();
        //lSoftware.connector.executeStatement("SELECT * FROM User");
        //System.out.println(lSoftware.connector.getCurrentQueryResult().getData()[0][0]);


    }

}
