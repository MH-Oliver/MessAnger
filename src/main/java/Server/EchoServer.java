package Server;

import DataStructure.*;

public class EchoServer extends Server {
    //List<Useraccount> mUserlist;
    public DatabaseConnector connector;
    String mLetzerNutzer;
    public EchoServer(int pPort) {
        super(pPort);
        connector = new DatabaseConnector("", 0, "src/main/resources/Server/ServerDB.db", "", "");
        System.out.println("Fehler:"+connector.getErrorMessage());
        //mUserlist = new List<Useraccount>();
        //mUserlist.append(new Useraccount("PokemanGu","asdf"));

    }

    @Override
    public void processNewConnection(String pClientIP, int pClientPort) {

    }

    @Override
    public void processMessage(String pClientIP, int pClientPort, String pMessage) {
        System.out.println("Nachricht von Client: "+pMessage);
        //System.out.println(connector.getErrorMessage());


        //if (connector.getCurrentQueryResult().getData()[0][0] != null) mLetzerNutzer = connector.getCurrentQueryResult().getData()[0][0] + ":" + pClientIP + ":" + pClientPort;


        connector.executeStatement("SELECT * FROM User");
        //System.out.println(connector.getErrorMessage());
        String[][] lUserDatabase = connector.getCurrentQueryResult().getData();
        String[] lSplittedMessage = pMessage.split(":");

        if (lSplittedMessage[0].equals("ANMELDUNG")) {
            boolean lUserfound = false;

            for (int i = 0; i < lUserDatabase.length; i++) {
                if     (lSplittedMessage[1].equals(lUserDatabase[i][3])
                        && lSplittedMessage[2].equals(lUserDatabase[i][1])) {
                    lUserfound = true;
                    connector.executeStatement("Update User set IP ='"+pClientIP+"' , Port = '" + pClientPort +"' where ID = '"+ lSplittedMessage[1]+"' and Password = '" + lSplittedMessage[2] + "'" );
                    mLetzerNutzer = lSplittedMessage[1]+":"+pClientIP+":"+pClientPort ;
                    connector.executeStatement("Select Name from User where ID = " + lSplittedMessage[1]);
                    send(pClientIP,pClientPort,"ErfolgreichHerrgestellt:" + lSplittedMessage[1] + ": " +connector.getCurrentQueryResult().getData()[0][0]);
                    
                }
            }
            if (!lUserfound) send(pClientIP,pClientPort,"VerbindungFehlgeschlagen");

        }

        else {
            connector.executeStatement("Select ID from User where IP = '" + pClientIP + "' and Port = '" + pClientPort + "'");


            if (connector.getCurrentQueryResult().getData()!= null) { //Nutzer muss angemeldet sein

                mLetzerNutzer = connector.getCurrentQueryResult().getData()[0][0] + ":" + pClientIP + ":" + pClientPort;
                String [] lLetzterUserSplit = mLetzerNutzer.split(":");
                if (lSplittedMessage[0].equals("SendeAnNachricht")) {
                    if (mLetzerNutzer != null) {
                        boolean lUserfound = false;
                        for (int i = 0; i < lUserDatabase.length; i++) {
                            if (lSplittedMessage[1].equals(lUserDatabase[i][3])) {
                                int lNewID = getNewIdOf("Message");
                                connector.executeStatement("Insert into Message values ('" + lSplittedMessage[2] + "' , " + System.currentTimeMillis() + " , "
                                        + lNewID + " , '" + lSplittedMessage[1] + "','"+lLetzterUserSplit[0]+"')");
                                System.out.println("Fehler:"+connector.getErrorMessage());
                                System.out.println("Nachricht in Datenbank abgespeichert");
                                long lCurrentTime = System.currentTimeMillis();
                                //send(lUserDatabase[i][2], Integer.parseInt(lUserDatabase[i][4]), "Nachricht:" + lSplittedMessage[2] + ":" + lCurrentTime + ":"+ lLetzterUserSplit[0] + ":" + lNewID + ":" + lSplittedMessage[1]);
                                send(pClientIP,pClientPort,"Nachricht:" + lSplittedMessage[2] + ":" + lCurrentTime + ":"+ lLetzterUserSplit[0] + ":" + lNewID + ":" + lSplittedMessage[1]);
                                send(pClientIP, pClientPort, "NachrichtVersendetAn: " + lUserDatabase[i][0]);
                                lUserfound = true;

                            }
                        }
                        if (!lUserfound) send(pClientIP, pClientPort, "ID:" + lSplittedMessage[1] + ":NichtGefunden");
                    }
                }
                else if (lSplittedMessage[0].equals("Received")){
                    connector.executeStatement("Delete from Message where ID = "+lSplittedMessage[1]);
                }
                else if (lSplittedMessage[0].equals("GetMessages")) {
                    connector.executeStatement("Select * from Message where ReceiverID = "+lLetzterUserSplit[0]);
                    String [][] lResultArray = connector.getCurrentQueryResult().getData();
                    for (int i = 0; i< lResultArray.length;i++) {
                        System.out.println(lResultArray[i][0]);
                        send(pClientIP,pClientPort,"Nachricht:"+lResultArray[i][0]+":"+lResultArray[i][1]+":"+lResultArray[i][4]+":"+lResultArray[i][2]  + ":" + lResultArray[i][3]);
                    }
                    send(pClientIP,pClientPort,"AlleNachrichtenVersendet");
                }
                else if (lSplittedMessage[0].equals("NewUser")) {
                    int NewID = getNewIdOf("User");
                    connector.executeStatement("Insert into User(Name,Password,IP,Port) values ('"+lSplittedMessage[1]+"' , '"
                            + lSplittedMessage[2]+ "' , '"
                            + pClientIP +"' , '"
                            + pClientPort + "')");
                    send(pClientIP,pClientPort,"SucessfullCreatet:"+ lSplittedMessage[1]+":"+NewID);
                }

                else if (lSplittedMessage[0].equals("ABMELDUNG")) {
                    System.out.println("Abmeldevorgang Datenbank");
                    connector.executeStatement("Update User set IP = null, Port = null where ID = " + lLetzterUserSplit[0]);
                    send(pClientIP,pClientPort,"ErfolgreichAbgemeldet");
                }

                else {
                    send(pClientIP,pClientPort,"Error please try again");
                }
            }
            else {
                send(pClientIP,pClientPort,"LoginExpected");
            }
        }




    }

    public int getNewIdOf(String pTable) {
        connector.executeStatement("Select MAX(ID) from " + pTable + "");
        int lNewID = 1;
        if (connector.getCurrentQueryResult() != null) lNewID = Integer.parseInt(connector.getCurrentQueryResult().getData()[0][0]) + 1;
        return  lNewID;
    }

    @Override
    public void processClosingConnection(String pClientIP, int pClientPort) {
        connector.executeStatement("Select ID from User where IP = '" + pClientIP + "' and Port = '" + pClientPort + "'");
        mLetzerNutzer = connector.getCurrentQueryResult().getData()[0][0] + ":" + pClientIP + ":" + pClientPort;
        String [] lLetzterUserSplit = mLetzerNutzer.split(":");

        System.out.println("Abmeldevorgang Datenbank");
        connector.executeStatement("Update User set IP = null, Port = null where ID = " + lLetzterUserSplit[0]);
        send(pClientIP,pClientPort,"ErfolgreichAbgemeldet");


    }
}
