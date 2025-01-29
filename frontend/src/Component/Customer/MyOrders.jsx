import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import "../../CSS/MyOrders.css";

function MyOrders() {
    const location = useLocation();
    const userId = location.state?.userId;
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (userId) {
            const fetchOrders = () => {
                fetch(`http://localhost:8081/customer/orders-by-user/${userId}`)
                    .then((response) => {
                        if (!response.ok) {
                            throw new Error("Orders could not be loaded!");
                        }
                        return response.json();
                    })
                    .then((data) => {
                        const sortedOrders = data.sort((a, b) => {
                            const orderStatusPriority = {
                                PENDING: 1,
                                COMPLETED: 2,
                                CANCELLED: 3,
                            };
                            return orderStatusPriority[a.orderStatus] - orderStatusPriority[b.orderStatus];
                        });
                        setOrders(sortedOrders);
                        setLoading(false);
                    })
                    .catch((error) => {
                        console.error("Hata:", error);
                        setLoading(false);
                    });
            };

            fetchOrders();

            const intervalId = setInterval(() => {
                fetchOrders();
            }, 1000);

            return () => clearInterval(intervalId);
        }
    }, [userId]);

    const getStatusStyle = (status) => {
        switch (status) {
            case "COMPLETED":
                return { color: "green" };
            case "CANCELLED":
                return { color: "red" };
            case "PENDING":
                return { color: "orange" };
            default:
                return {};
        }
    };

    if (loading) {
        return <p className="my-orders-loading">Loading...</p>;
    }

    return (
        <div className="my-orders-page">
            <h1 className="my-orders-title">My Orders</h1>
            {orders.length > 0 ? (
                <ul className="my-orders-list">
                    {orders.map((order) => (
                        <li key={order.orderId} className="my-orders-list-item">
                            <p className="my-orders-product">
                                <strong>Product Name:</strong> {order.product.productName}
                            </p>
                            <p className="my-orders-quantity">
                                <strong>Quantity:</strong> {order.quantity}
                            </p>
                            <p className="my-orders-price">
                                <strong>Total Price:</strong> {order.totalPrice} $
                            </p>
                            <p className="my-orders-status" style={getStatusStyle(order.orderStatus)}>
                                <strong>Order Status:</strong> {order.orderStatus}
                            </p>
                            <p className="my-orders-priority">
                                <strong>Waiting Time:</strong> {order.waitingTime} s
                            </p>
                            <p className="my-orders-priority">
                                <strong>Priority Score:</strong> {order.priorityScore}
                            </p>
                            <p className="my-orders-date">
                                <strong>Order Date:</strong> {order.orderDate}
                            </p>
                        </li>
                    ))}
                </ul>
            ) : (
                <p className="my-orders-empty">You have no orders!</p>
            )}
        </div>
    );
}

export default MyOrders;
