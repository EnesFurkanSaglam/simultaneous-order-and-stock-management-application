import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../../CSS/CustomerLogin.css';

function CustomerLogin() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/customer/login', {
                username: username,
                password: password
            });

            const serverMessage = response.data;

            if (response.status === 200) {
                if (serverMessage === "The site is under maintenance, please try again later.") {
                    setMessage(serverMessage);
                } else if (serverMessage === "Customer logged in") {
                    setMessage('Login successful!');
                    navigate("/show-products", {
                        state: {
                            username: username
                        }
                    });
                } else {
                    setMessage(serverMessage);
                }
            }
        } catch (error) {
            setMessage('An error occurred: ' + (error.response ? error.response.data : error.message));
        }
    };

    return (
        <div className="customer-login-page">
            <h1 className="customer-login-title">Customer Login</h1>
            <form className="customer-login-form" onSubmit={handleLogin}>
                <div className="customer-login-input-group">
                    <label className="customer-login-label">
                        Username:
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter your username"
                            className="customer-login-input"
                        />
                    </label>
                </div>
                <div className="customer-login-input-group">
                    <label className="customer-login-label">
                        Password:
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                            className="customer-login-input"
                        />
                    </label>
                </div>
                <button type="submit" className="customer-login-button">Login</button>
            </form>
            {message && <p className="customer-login-message">{message}</p>}
        </div>
    );
}

export default CustomerLogin;
