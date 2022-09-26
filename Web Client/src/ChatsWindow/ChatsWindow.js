import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';
import { Col, Row, Container } from 'react-bootstrap';
import { HubConnectionBuilder, LogLevel } from '@microsoft/signalr';
import './ChatsWindow.css'
import Chat from './Chat/Chat.js'
import Utils from '../Utils';
import Server from '../Server';
import ChatsList from './ChatsList/ChatsList';
import AddContactModal from './AddContactModal/AddContactModal';

function ChatsWindow({ contacts, setContacts, connectedUser, connection, setConnection }) {
    const [messagesOnChat, setMessagesOnChat] = useState([]);
    const [currentContact, setCurrentContact] = useState({ UserName: "", NickName: ""});
    const [newMessage, setNewMessage] = useState("");
    const contactsRef = useRef(contacts);
    contactsRef.current = contacts;
    const messagesRef = useRef(messagesOnChat);
    messagesRef.current = messagesOnChat;

    const startNewConnection = async () => {
        if (connection) {
            connection.stop();
        }
        const newConnection = new HubConnectionBuilder()
            .withUrl(`http://` + Server.Server + `/hub`)
            .configureLogging(LogLevel.Information)
            .build();
        await newConnection.start();
        await newConnection.invoke("RegisterToHub", connectedUser.UserName);
        setConnection(newConnection);
    }

    const registerToReceiveMessageEvent = () => {
        if (connection) {
            if (Utils.lastContact != '') {
                connection.off("ReceiveMessage");
            }
            connection.on("ReceiveMessage", (FromUserName, m) => {
                if (FromUserName === currentContact.UserName) {
                    setMessagesOnChat([...messagesOnChat, m]);
                }
                UpdateLastMessage(m, FromUserName);
            });
        }
    }

    const registerToAddContactEvent = () => {
        if (connection) {
            connection.off("AddContact");
            connection.on("AddContact", async (contact) => {
                setContacts([contact, ...contacts]);
            });
        }
    }

    useEffect(() => {
        if (connectedUser.UserName != "") {
            document.title = `${connectedUser.UserName}'s chat`;
            startNewConnection();
            registerToReceiveMessageEvent();
        } else {
            document.title = `Error`;
        }
    }, [connectedUser]);

    useEffect(() => {
        registerToAddContactEvent();    
    }, [contacts, currentContact && messagesOnChat]);

    const OpenAddContactWindow = (e) => {
        e.preventDefault();
        document.getElementById('chatsWindow').style.opacity = 0.5;
        document.getElementById('addContactModal').style.display = "block";
        document.getElementById('addContactModal').style.opacity = 1;
    }

    const ExitNewContactWindow = () => {
        document.getElementById('addContactModal').style.display = "none";
        document.getElementById('chatsWindow').style.opacity = 1;
        document.getElementById('newContactUsername').value = '';
        document.getElementById('newContactNickname').value = '';
    }

    const HandleOpenChat = async (contactClicked) => {
        Utils.lastContact = currentContact.UserName;
        Utils.thisContact = contactClicked.UserName;
        setMessagesOnChat([]);
        setCurrentContact(contactClicked);
        const messages = await Server.fetchMessagesWithContact(contactClicked.UserName, connectedUser.UserName);
        setMessagesOnChat(messages);
        document.getElementById('chat').style.display = "block";
    }

    const UpdateLastMessage = (massage, contactUsername) => {
        let contact = null;
        contactsRef.current.forEach(c => {
            if (c.UserName == contactUsername) {
                contact = c;
            }
        });
        if (contact == null) {
            return;
        }

        var contacts1 = contactsRef.current.filter(c => c.UserName != contactUsername);
        contact.LastMessageContent = massage.Content;
        contact.LastMessageTimeStamp = massage.TimeStamp;
        contacts1 = [contact, ...contacts1];
        setContacts(contacts1);
    }

    const HandleLogout = () => {
        if (connection) {
            connection.off("AddContact");
            connection.off("ReceiveMessage");
            connection.stop();
        }
    }

    if (connectedUser.UserName == '') {
        return (
            <div id="error">
                <span>Error! There is no user connected</span>
                <Link to='/'> Click here </Link>
                <span>to login.</span>
            </div>
        );
    }

    return (
        <div>
            <Container id="chatsWindow" className='pages'>
                <Row className='h-100'>
                    <Col xs={4} id="sideBar" className='h-100'>
                        <Container>
                            <Row id='connectedUser'>
                                <Container>
                                    <Row>
                                        <Col xs={10} id="connectedNickname">{connectedUser.NickName}</Col>
                                        <Col xs={2}><button onClick={OpenAddContactWindow} className="bi bi-person-plus-fill btn btn-outline-secondary"></button></Col>
                                    </Row>
                                    <Row>
                                        <Link to="/"><button onClick={HandleLogout} className="btn btn-outline-secondary" type="button">LogOut</button></Link>
                                    </Row>
                                </Container>
                            </Row>
                        </Container>
                        <ChatsList contacts={contacts} HandleOpenChat={HandleOpenChat} />
                    </Col>
                    <Col xs={8} id="chat" className='h-100'>
                        <Chat connectedUserName={connectedUser.UserName} UpdateLastMessage={UpdateLastMessage} messagesOnChat={messagesOnChat} setMessagesOnChat={setMessagesOnChat}
                            currentContact={currentContact} newMessage={newMessage} setNewMessage={setNewMessage} />
                    </Col>
                </Row>
            </Container>
            <AddContactModal connectedUser={connectedUser} contacts={contacts} setContacts={setContacts} ExitNewContactWindow={ExitNewContactWindow} />
        </div>
    );
}

export default ChatsWindow;
