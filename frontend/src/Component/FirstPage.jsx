import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import "../CSS/FirstPage.css";


function FirstPage() {


    return (
        <div className="first-page">
            <h1 className="first-page-title">Welcome</h1>
            <nav className="first-page-nav">
                <ul className="first-page-list">
                    <li className="first-page-list-item">
                        <Link to="/admin" className="first-page-link">Admin Login</Link>
                    </li>
                    <li className="first-page-list-item">
                        <Link to="/customer" className="first-page-link">Customer Login</Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
}

export default FirstPage;
