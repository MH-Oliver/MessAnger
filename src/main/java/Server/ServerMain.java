package Server;

public class ServerMain {
    MessengerServer mServer;
    int mServerPort = 1;

    public ServerMain () {
        mServer = new MessengerServer(mServerPort);


    }

    public static void main(String[] args) {
        ServerMain lServer = new ServerMain();
        System.out.print("Server of the MessAnger startet");

        while (true){

        }
    }
}
