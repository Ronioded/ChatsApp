import React from 'react';
import { Col, Row, Container } from 'react-bootstrap';
import Server from '../../Server';
import './AddContactModal.css';

function AddContactModal({ connectedUser, contacts, setContacts, ExitNewContactWindow }) {
    const isContactExists = (contactUsername) => {
        let flag = false;
        contacts.forEach(contact => {
            if (contact.UserName == contactUsername) {
                flag = true;
            }
        });
        return flag;
    }

    const PostNewContact = async (contactUserName, contactNickName) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userName: contactUserName, nickName: contactNickName }),
            credentials: 'include',
        };

        const res = await fetch(`${Server.Url}/Contacts/?username=${connectedUser.UserName}`, requestOptions);
        return res.status;
    }

    const HandleAddContact = async (e) => {
        e.preventDefault();
        const newContactUsername = document.getElementById('newContactUsername').value;
        const newContactNickname = document.getElementById('newContactNickname').value;
        if (isContactExists(newContactUsername)) {
            alert('Contact is already exists');
            return;
        }
        if ((newContactUsername == connectedUser.UserName) ||
            (await PostNewContact(newContactUsername, newContactNickname) != 201)) {
            alert('User does not exists');
            return;
        }

        ExitNewContactWindow();
        const newContact = {
            UserName: newContactUsername,
            NickName: newContactNickname,
            LastMessageContent: "",
            LastMessageTimeStamp: ""
        }
        setContacts([newContact, ...contacts]);
    }

    return (
        <Container id="addContactModal" className="pages">
            <Row id="addContactTitle">
                <Col xs={10}>Add new contact</Col>
                <Col xs={2}>
                    <button className="bi bi-x-circle btn btn-outline-secondary" onClick={ExitNewContactWindow}> </button>
                </Col>
            </Row>
            <form className="form-floating mb3 addContactPadding" onSubmit={HandleAddContact}>
                <Row className="form-floating mb-3 addContactFields">
                    <div>
                        <label className="form-label" htmlFor="newContactUsername">Contact's username</label>
                        <input id="newContactUsername" placeholder="Please enter contact's username" required></input>
                    </div>
                    <div>
                        <label className="form-label" htmlFor="newContactNickname">Contact's nickname</label>
                        <input id="newContactNickname" placeholder="Please enter contact's nickname" required></input>
                    </div>
                </Row>
                <Row>
                    <span className='addContactPadding'><button className="btn btn-outline-secondary" id="addContactButton" type="submit">Add</button></span>
                </Row>
            </form>
        </Container>
    );
}

export default AddContactModal;