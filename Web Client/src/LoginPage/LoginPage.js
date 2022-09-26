import React, { useEffect } from "react";
import { Link, useNavigate } from 'react-router-dom';
import Server from '../Server';

function LoginPage({ setContacts, setConnectedUser }) {
  const navigate = useNavigate();

  useEffect(() => {
    document.title = "Login";
  }, [])

  const fetchLogin = async (userName, password) => {
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ userName: userName, password: password }),
      credentials: 'include'
    };
    const res = await fetch(`${Server.Url}/Users/login`, requestOptions);
    if (res.status == 200) {
      return await res.json();
    } else {
      return null;
    }
  }

  const loginHandler = async (e) => {
    e.preventDefault();
    const userLoginDetails = {
      UserName: document.getElementById('username').value,
      Password: document.getElementById('password').value
    }
    const nickName = await fetchLogin(userLoginDetails.UserName, userLoginDetails.Password);
    if (nickName != null) {
      const connectedUser = { UserName: userLoginDetails.UserName, NickName: nickName.NickName }
      Server.fetchAllContacts(connectedUser, setContacts, setConnectedUser)
      navigate('/chats');
    } else {
      alert('Bad username or password!!');
    }
  }

  const inputsDetalis = [{ name: "Username:", type: "text", id: "username", placeholder: "Please enter your username" },
  { name: "Password:", type: "password", id: "password", placeholder: "Please enter your password" }];

  const inputs = inputsDetalis.map((input, key) => {
    return (
      <div key={key}>
        <label className="form-label" htmlFor={input.id}>{input.name}</label>
        <input type={input.type} id={input.id} placeholder={input.placeholder} required></input>
      </div>
    );
  });

  return (
    <div id="login" className='mb-3 pages'>
      <h2 className="pageTitle">Login Page</h2>
      <form onSubmit={loginHandler}>
        <div>{inputs}</div>
        <div className="flexCenter">
          <button className="btn btn-outline-secondary" type="submit">Login</button>
        </div>
        <div className="flexCenter">
          <span>Not registered?</span>
          <Link to='/registerpage' className='linkDesign'>Click here</Link>
          <span>to register.</span>
        </div>
      </form>
    </div>
  );
}

export default LoginPage;
