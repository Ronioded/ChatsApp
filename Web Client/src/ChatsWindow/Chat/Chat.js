import React, { useEffect, useRef } from 'react'
import { Container, Row, Col } from 'react-bootstrap';
import './Chat.css';
import Utils from '../../Utils';
import Server from '../../Server';

function Chat({ connectedUserName, UpdateLastMessage, messagesOnChat, setMessagesOnChat, currentContact, newMessage, setNewMessage }) {
    const messagesEndRef = useRef(null);
    const messagesRef = useRef(messagesOnChat);
    messagesRef.current = messagesOnChat;
    
    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }, [messagesOnChat]);    

    const fetchAddMessage = async () => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ content: newMessage }),
            credentials: 'include'
        };
        await fetch(`${Server.Url}/Messages/${currentContact.UserName}/?username=${connectedUserName}`, requestOptions);
        const messages = await Server.fetchMessagesWithContact(currentContact.UserName, connectedUserName);
        setMessagesOnChat(messages);
        UpdateLastMessage(messages[messages.length - 1], currentContact.UserName);
    }
    
    const HandleChangeMessage = (e) => {
        setNewMessage(e.target.value);
    }

    const HandleSendMessage = (e) => {
        if (e.key === "Enter") {
            HandleAddMessage(e);
        }
    }

    const HandleAddMessage = function (e) {
        e.preventDefault();       
        if (newMessage != "")  {
            fetchAddMessage();
            setNewMessage("");
        }
    }

    const chatHistory = messagesOnChat.map((message,key) => {
        return (
            <div key={key}> {(message.Content != "") ? (
                <div >
                    <div className={message.Sent ? 'message MessageSent' : 'message MessageNotSent'}>
                        <Container>
                            <Row>
                                <Col xs="auto">{message.Content}</Col>
                                <Col className='messageTime'>{Utils.dayjs(message.TimeStamp).format(Utils.timeFormat)}</Col>
                            </Row>
                        </Container>
                    </div>
                </div>
            ) : ""}
            </div>
        );
    });
    
    return (
        <>
            <h3 id="contactName">Chat with {currentContact.NickName}</h3>
            <div className="chatBody">
                {chatHistory}
                <div ref={messagesEndRef} />
            </div>
            <div className="toolBar">
                <form>
                    <input id="addMessageInput" placeholder='Write your message' type="text" onChange={HandleChangeMessage} value={newMessage} onKeyPress={HandleSendMessage} />
                    <button id="addMessageButton" className="bi bi-envelope" onClick={HandleAddMessage} type="submit"></button>
                </form>
            </div>
        </>
    );
}

export default Chat;
