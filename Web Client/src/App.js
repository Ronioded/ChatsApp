import React, { useState } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from './LoginPage/LoginPage';
import RegisterPage from './RegisterPage/RegisterPage';
import ChatsWindow from './ChatsWindow/ChatsWindow';
import './App.css';

function App() {
  const [connectedUser, setConnectedUser] = useState({ UserName: "", NickName: "" });
  const [contacts, setContacts] = useState([]);
  // connection represents signalR connection.
  const [connection, setConnection] = useState(null);

  return (
      <BrowserRouter>
        <Routes>
          <Route path="/registerpage" element={<RegisterPage setConnectedUser={setConnectedUser} setContacts={setContacts} />}></Route>
          <Route path="/" element={<LoginPage setConnectedUser={setConnectedUser} setContacts={setContacts} />}></Route>
          <Route path="/chats" element={<ChatsWindow connection={connection} setConnection={setConnection} contacts={contacts} setContacts={setContacts} connectedUser={connectedUser} />}></Route>
        </Routes>
      </BrowserRouter>
  );
}

export default App;