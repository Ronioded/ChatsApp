# ChatsApp
ChatsApp is a messages passing project. The project consists of:    
## Server  
The server is written in C# with ASP.NET MVC architecture and Entity Framework Core.  
The server uses SQLite database to save all the data - users, contacts, and messages.
The server also uses API written in C# with REST API architecture. The API defines a way to get access to the objects on the database.  
In order to check the API methods, the server uses Swagger UI. The connected user username is concat to the URL in the format: /?username="username".  
<img width="959" alt="image" src="https://user-images.githubusercontent.com/74676502/190852573-b77452f1-61b7-4704-a492-dc340b341670.png">  
<img width="954" alt="image" src="https://user-images.githubusercontent.com/74676502/190852595-33b7b9f7-449e-46a1-9625-793974e66821.png">

### How to run?  
In launchsettings.json file, there is a definition that the server will run on port 5119. If the server is running on another port, you need to change it in Server.js file in the client and in strings.xml file under 'API_URL' in the android app.
***Downloads we have done in visual studio:***  
   -  FirebaseAdmin
   -  Swashbuckle.AspNetCore
   -  Microsoft.EntityFrameworkCore
   -  Microsoft.EntityFrameworkCore.Tools
   -  Microsoft.EntityFrameworkCore.Sqlite
   -  Microsoft.EntityFrameworkCore.Design
   -  Microsoft.AspNet.Mvc
   -  Newtonsoft.Json
   -  Microsoft.VisualStudio.Web.CodeGeneration.Design  
    
After the downloads, run the server regularly on visual studio.
***Notice that we used SQLite, the DB is in ChatsAppDB.db file***

## Web client 
The Web client is written in React.js and uses CSS for the design. The client also uses SignalR to receive messages and add contacts in real time.  
Pages in the app:  
_1._ **Register Page** for new users with basic requirements and validations, for example, checks that the password contains letters & numbers and checks that there is no other user registered with the same username.  <img width="960" alt="image" src="https://user-images.githubusercontent.com/74676502/192283603-32653347-7ec1-4f1c-9647-c55fff61d273.png">  
_2._ **Login Page** for existing users.  <img width="960" alt="image" src="https://user-images.githubusercontent.com/74676502/192283453-b6de5ef4-be3a-4b7e-9932-4479723a0d7a.png">  
_3._ **Chats Window Page**- On this page, we can see on the left the Chats list of the user.   


_4._ **Chat Page**- Once the user clicks on contact, he can see on the right the chat with the same user.  


_5._ **Add Contact Modal**- In this modal, the user can add a chat with a new contact.  



In this video, you can see the registration of a new user, add a new contact, and click on the contact to see the new empty chat.



### How to run?  
To run the client, you need to download:
   -  @microsoft/signalr by the command ```npm i @microsoft/signalr```
   -  react-router-dom by the command ```npm i react-router-dom```
   -  react-bootstrap by the command ```npm i react-bootstrap```  
  
You also need a node_modules folder that you can download by creating a new to react app with the command ```npx create-react-app my-app```.  
To run type ```npm start``` after cd to the folder that contains the files.  
_Notice:_ in order to login, register and send messages in the clients (web or android), you need to run the server first.

## Android client  
The Android client is written in Java(XML for the design) with MVVM architecture. The client uses Firebase to get notifications  in real-time. The client also uses ROOM library to have a local database on the android device and save all the data on it. Once login or tap a contact to open the messages history, the data shown is the data in the local database, and meanwhile, there are asynchronous API calls to the server to update the data. Once the updated data arrives it renders it on the view.

The pages in the app are the same as in the web:  
_1._ **Register Page**  
<img width="176" alt="register android" src="https://user-images.githubusercontent.com/74676502/192304726-ffc125cf-1fcd-4546-a4d6-07b0c21ecca1.png">
  
_2._ **Login Page**  
<img width="173" alt="login android" src="https://user-images.githubusercontent.com/74676502/192304671-59184a9d-09ac-4dfa-8020-befa12f3c9dd.png">
  
_3._ **Chats Window Page**  

  
_4._ **Chat Page**  

  
_5._ **Add Contact Page**  
<img width="167" alt="add contact android" src="https://user-images.githubusercontent.com/74676502/192304808-dd5659aa-e8f6-4061-942d-459b42314053.png">
  

In this video, you can see the registration of a new user, add a new contact, and click on the contact to see the new empty chat.



### How to run?  
You can open the files on Android Studio and run it with Android emulator.  
In order to enable the firebase you need to connect the firebase to our android project files, download a private key from firebase website and put the pk json file in the Server under the path FireBase/privateKey.json.  
_Notice:_ in order to login, register and send messages in the clients (web or android), you need to run the server first.
