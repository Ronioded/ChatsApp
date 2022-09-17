# ChatsApp
ChatsApp is a messages passing project. The project consists of:

## Web client 
The Web client is written in React.js and uses CSS for the design. The client also uses SignalR to receive messages and add contacts in real time.  
Pages in the app:  
_1._ **Register Page** for new users with basic requirements and validations, for example, checks that the password contains letters & numbers and checks that there is no other user registered with the same username. <img width="960" alt="RegisterPage" src="https://user-images.githubusercontent.com/74676502/188276524-c9f986d3-1286-4b8a-9613-6df5a037fa81.png">  
_2._ **Login Page** for existing users. <img width="960" alt="LoginPage" src="https://user-images.githubusercontent.com/74676502/188276894-7f079443-49e2-4229-a420-dac22cbc88d2.png">  
_3._ **Chats Window Page**- On this page, we can see on the left the Chats list of the user.  
_4._ **Chats Page**- Once the user clicks on contact, he can see on the right the chat with the same user.  
_5._ **Add Contact Modal**- In this modal, the user can add a chat with a new contact.  

In this video, you can see the registration of a new user, add a new contact, and click on the contact to see the new empty chat.   
https://user-images.githubusercontent.com/74676502/190851779-f5594955-4489-4673-8758-b8302f63f0ae.mov


## Android client  
The Android client is written in Java(XML for the design) with MVVM architecture. The client uses Firebase to get notifications in real-time. The client also uses ROOM library to have a local database on the android device and save all the data on it. Once login or tap a contact to open the messages history, the data shown is the data in the local database, and meanwhile, there are asynchronous API calls to the web service to update the data. Once the updated data arrives it renders it.
## Server  
The Server is written in C# with ASP.NET MVC architecture and Entity Framework Core. The server uses SQLite database to save all the data - users, contacts, and messages.
## Web service API  
The Web service API is written in C# with REST API service. The API defines a way to get access to the objects on the database.
