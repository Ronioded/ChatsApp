import React, { useEffect, useRef } from 'react'
import { Col, Row, Container } from 'react-bootstrap';
import Utils from '../../Utils';
import './ChatsList.css'

function ChatsList({ contacts, HandleOpenChat }) {
    const chatsStartRef = useRef(null);
    const scrollToStart = () => {
        chatsStartRef.current?.scrollIntoView({ behavior: "smooth" })
    }

    useEffect(() => {
        scrollToStart();
    }, []);

    useEffect(() => {
        scrollToStart();
    }, [contacts]);

    const Chats = contacts.map((contact, key) => {
        return (
            <Row key={key} className="contact px-z" onClick={() => HandleOpenChat(contact)}>
                <Container>
                    <Row>
                        <Col xs={8} className='contactUsername'>{contact.NickName}</Col>
                        <Col xs={4} className='lastMessageTime'>{Utils.timeDisplay(contact.LastMessageTimeStamp)}</Col>
                    </Row>
                    <Row className='lastMessage'>{Utils.displayLastMessage(contact.LastMessageContent)}</Row>
                </Container>
            </Row>
        );
    });

    return (
        <div ref={chatsStartRef} id="chatsList">
            <Container >{Chats}</Container>
        </div>
    );
}

export default ChatsList;
