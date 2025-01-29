import React, { useEffect, useState } from "react";
import "../../CSS/Customers.css";

function Customers() {
    const [customers, setCustomers] = useState([]);

    const fetchCustomers = async () => {
        const result = await fetch("http://localhost:8081/admin/customers");
        return result.json();
    };

    useEffect(() => {
        const getCustomers = async () => {
            const data = await fetchCustomers();
            setCustomers(data);
        };
        getCustomers();
    }, []);

    return (
        <div className="customers-container">
            <h1 className="customers-title">Customers</h1>
            <ul className="customers-list">
                {customers.map((customer, index) => (
                    <li key={index} className="customers-list-item">
                        <p className="customers-info">ID: {customer.id}</p>
                        <p className="customers-info">Name: {customer.customerName}</p>
                        <p className="customers-info">Type: {customer.customerType}</p>
                        <p className="customers-info">Budget: {customer.budget}</p>
                        <p className="customers-info">Total Spent: {customer.totalSpent}</p>
                        <p className="customers-info">Email: {customer.user.email}</p>
                        <p className="customers-info">Created At: {customer.user.createdAt}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default Customers;
