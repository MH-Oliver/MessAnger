# MessAnger
My own try to create my own Messenger

To do this I used the Classes i got from the German Ministry of Education for Informatics.
My Messenger is serializing all Information with a database. To communicate i used a kind of my own phone number system, where every number is individual and every account gets one of these numbers. 
(Database of the Server:ServerDB.db 
 Database of the Client:ClientDB.db)

## How To use the MessAnger with the Console
Open ServerMain.java and start the Main Method, to run the Server on your Computer (I'm currently working on a permanently running Server).
Open ClientMain.java and start the Main Method, to start a Client Connection and communicate to other Clients.
Now you are able to write the following Commands into the Console:
(Anything thats "*italic*" you can fill in)
- ANMELDUNG:*MessAngerPhoneNumber*:*Password*
- SendeAnNachicht:*MessAngerPhoneNumber*:*Message*
- GetMessages:*MessAngerPhoneNumber*
- NewUser:*MessAngerPhoneNumber*:*Password*

Hint: Sometimes it Happens, that you can't add a new User. 
      This seams To be a Problem with my Class To Connect To the Database, 
      which i'm Not allowed to fix.
      In this Case Just try it again or Take the Data of an Example User.
Example Accounts: 
Username: PokemanGu 
Password: asdf
MessAngerPhoneNumber:1
## MessAnger Database

If you want To Take a Look Into my Database, Take a 
look Into the Ressource foulder, 
because there ist also a another Version of the Database Out of the Ressource Foulder
which get's created by the School Ministry Class for the Database Connection. 
This Database is completly empty and I don't know why they become created.

**Database Client:**
User(Name,*ID*)
Message(Content,Time,*ID*,SenderID,RecieverID)

**Database Server:**
User(Name,ID,IP,Port)
Message(Content,Time,*ID*,SenderID,RecieverID)

## How To use the MessAnger GUI
I also startet building a GUI with JavaFX, which you can start by starting the Main-Method
in MessengerGui.java. In this you should be able to write with your Friends without knwoing about the Console Commands.
**(Still in work)**

