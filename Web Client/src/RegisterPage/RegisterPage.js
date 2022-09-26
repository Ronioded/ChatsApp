import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Server from '../Server';
import Utils from '../Utils';

function RegisterPage({ setContacts, setConnectedUser }) {
    const navigate = useNavigate();

    useEffect(() => {
        document.title = "Register";
    }, [])

    const fetchRegister = async (userName, nickName, password) => {
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ userName: userName, nickName: nickName, password: password }),
            credentials: 'include'
        };
        const res = await fetch(`${Server.Url}/Users/register`, requestOptions);
        return (res.status == 200);
    }

    const register = async () => {
        let isRegisterSuccessful = true;
        const userName = document.getElementById('username').value;
        const nickName = document.getElementById('nickname').value;
        const password = document.getElementById('password').value;
        const passwordagain = document.getElementById('password-again').value;

        if (password != passwordagain) {
            alert('The passwords does not match, Please register again.');
            isRegisterSuccessful = false;
        }

        if (!Utils.checkValidPassword(password)) {
            alert("Password must contain numbers and letters. Try again!");
            isRegisterSuccessful = false;
        }

        if (isRegisterSuccessful && (!(await fetchRegister(userName, nickName, password)))) {
            alert('The username is already taken!!!');
            isRegisterSuccessful = false;
        }

        return isRegisterSuccessful;
    }

    const registerHandler = async (e) => {
        e.preventDefault();
        if (await register()) {
            const connectedUser = {
                UserName: document.getElementById('username').value,
                NickName: document.getElementById('nickname').value
            }
            Server.fetchAllContacts(connectedUser, setContacts, setConnectedUser)
            navigate('/chats');
        }
    }

    const inputsDetalis = [{ name: "Username:", type: "text", id: "username", placeholder: "Please enter unique username" },
    { name: "Nickname:", type: "text", id: "nickname", placeholder: "Please enter nickname" },
    { name: "Password:", type: "password", id: "password", placeholder: "Please enter password" },
    { name: "Confirm password:", type: "password", id: "password-again", placeholder: "Please enter the password again" }];

    const inputs = inputsDetalis.map((input, key) => {
        return (
            <div key={key}>
                <label className="form-label" htmlFor={input.id}>{input.name}</label>
                <input type={input.type} id={input.id} placeholder={input.placeholder} required></input>
            </div>
        );
    });

    return (
        <div id="register" className="mb-3 pages">
            <h2 className="pageTitle">Register Page</h2>
            <form onSubmit={registerHandler}>
                <div>{inputs}</div>
                <div className="flexCenter">
                    <button className="btn btn-outline-secondary" type="submit">Register</button>
                </div>
                <div className="flexCenter">
                    <span>Already registered? </span>
                    <Link to='/' className='linkDesign'>Click here</Link>
                    <span> to login.</span>
                </div>
            </form>
        </div>
    );
}

export default RegisterPage;
