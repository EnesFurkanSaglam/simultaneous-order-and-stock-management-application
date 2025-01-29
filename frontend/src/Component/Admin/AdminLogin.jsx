import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import '../../CSS/AdminLogin.css';

function AdminLogin() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post('http://localhost:8081/admin/login', {
                username: username,
                password: password
            });

            if (response.data === true) {
                setMessage('Login successful!');
                navigate("/products");
            } else {
                setMessage('Invalid username or password.');
            }
        } catch (error) {
            setMessage('An error occurred: ' + (error.response ? error.response.data : error.message));
        }
    };

    return (
        <div className="admin-login-page">
            <h1 className="admin-login-title">Admin Login</h1>
            <form className="admin-login-form" onSubmit={handleLogin}>
                <div className="admin-login-input-group">
                    <label>
                        Username:
                        <input
                            className="admin-login-input"
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter your username"
                        />
                    </label>
                </div>
                <div className="admin-login-input-group">
                    <label>
                        Password:
                        <input
                            className="admin-login-input"
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                        />
                    </label>
                </div>
                <button className="admin-login-button" type="submit">Login</button>
            </form>
            {message && <p className="admin-login-message">{message}</p>}
        </div>
    );
}

export default AdminLogin;
