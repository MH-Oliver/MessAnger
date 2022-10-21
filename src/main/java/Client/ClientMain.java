package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import View.*;

public class ClientMain {
    EchoClient mClient;
    //public static DataStructure.DatabaseConnector connector = new DataStructure.DatabaseConnector("", 0, "User.db", "", "");

    public ClientMain() {
        mClient = new EchoClient("localhost",1);
    }

    public static void main(String[] args) throws IOException {
        ClientMain lSoftware  = new ClientMain();

        //lSoftware.mClient.send("ANMELDUNG:ElonMusk:123");
        //lSoftware.mClient.send("SendeAnNachricht:PokemanGu:MoinServusMoin");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while (true) {
            input = in.readLine();
            if (!input.equals(null)) lSoftware.mClient.send(input);
        }
        //lSoftware.connector.executeStatement("SELECT * FROM User");
        //System.out.println(lSoftware.connector.getCurrentQueryResult().getData()[0][0]);


    }

}
