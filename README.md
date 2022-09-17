# ChatsApp
ChatsApp is a messages passing project. The project consists of:

## Web client 
The Web client is written in React.js and uses CSS for the design. The client also uses SignalR to receive messages and add contacts in real time.  
Pages in the app:  
_1._ **Register Page** for new users with basic requirements and validations, for example, checks that the password contains letters & numbers and checks that there is no other user registered with the same username. <img width="960" alt="RegisterPage" src="https://user-images.githubusercontent.com/74676502/188276524-c9f986d3-1286-4b8a-9613-6df5a037fa81.png">  
_2._ **Login Page** for existing users. <img width="960" alt="LoginPage" src="https://user-images.githubusercontent.com/74676502/188276894-7f079443-49e2-4229-a420-dac22cbc88d2.png">  
_3._ **Chats Window Page**- On this page, we can see on the left the Chats list of the user.   
<img width="960" alt="image" src="https://user-images.githubusercontent.com/74676502/190852396-25270570-588e-49c1-a58b-7d6c00d8fa8a.png">
_4._ **Chat Page**- Once the user clicks on contact, he can see on the right the chat with the same user.  
<img width="960" alt="image" src="https://user-images.githubusercontent.com/74676502/190852406-2447a9bf-1b16-418e-a59b-0b468da188c6.png"> 
_5._ **Add Contact Modal**- In this modal, the user can add a chat with a new contact.  
<img width="959" alt="image" src="https://user-images.githubusercontent.com/74676502/190852427-80c7d7e5-f16f-4e1b-a463-779a35ade04a.png">

In this video, you can see the registration of a new user, add a new contact, and click on the contact to see the new empty chat.

https://user-images.githubusercontent.com/74676502/190851779-f5594955-4489-4673-8758-b8302f63f0ae.mov

### How to run?  
To run the client, you need to download:
   -  @microsoft/signalr by the command ```npm i @microsoft/signalr```
   -  react-router-dom by the command ```npm i react-router-dom```
   -  react-bootstrap by the command ```npm i react-bootstrap```  
  
You also need a node_modules folder that you can download by creating a new to react app with the command ```npx create-react-app my-app```.  
To run type ```npm start``` after cd to the folder that contains the files.


## Android client  
The Android client is written in Java(XML for the design) with MVVM architecture. The client uses Firebase to get notifications in real-time. The client also uses ROOM library to have a local database on the android device and save all the data on it. Once login or tap a contact to open the messages history, the data shown is the data in the local database, and meanwhile, there are asynchronous API calls to the web service to update the data. Once the updated data arrives it renders it.


## Server  
The Server is written in C# with ASP.NET MVC architecture and Entity Framework Core.  
The server uses SQLite database to save all the data - users, contacts, and messages.
The server uses API written in C# with REST API architecture. The API defines a way to get access to the objects on the database.  
In order to check the API methods, the server uses Swagger UI. The connected user in concat to the URL in the format: /?username="username".  
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
