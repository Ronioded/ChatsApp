# ChatsApp
ChatsApp is a messages passing project. The project consists of:

1. Web client written in React.js(CSS for the design). The client uses SignalR to receive messages and add contacts in real-time.
2. Android client written in Java(XML for the design) with MVVM architecture. The client uses Firebase to get notifications in real-time. The client also uses ROOM library to have a local database on the android device and save all the data on it. Once login or tap a contact to open the messages history, the data shown is the data in the local database, and meanwhile, there are asynchronous API calls to the web service to update the data. Once the updated data arrives it renders it.
3. Server written in C# with ASP.NET MVC architecture and Entity Framework Core. The server uses SQLite database to save all the data - users, contacts, and messages.
4. Web service API written in C# with REST API service. The API defines a way to get access to the objects on the database.
