import React, { useEffect, useState } from 'react';
import "../../CSS/Confirm.css";

function Confirm() {
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    const fetchOrders = async () => {
        try {
            const response = await fetch('http://localhost:8081/admin/orders');
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const data = await response.json();
            const sortedOrders = data.sort((a, b) => b.priorityScore - a.priorityScore);
            setOrders(sortedOrders);
            setLoading(false);
        } catch (error) {
            console.error('Error fetching orders:', error);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchOrders();
        const intervalId = setInterval(fetchOrders, 1000);
        return () => clearInterval(intervalId);
    }, []);

    if (loading) {
        return <div className="confirm-loading">Loading...</div>;
    }

    const pendingOrders = orders.filter((order) => order.orderStatus === 'PENDING');
    const completedOrders = orders.filter((order) => order.orderStatus === 'COMPLETED');
    const cancelledOrders = orders.filter((order) => order.orderStatus === 'CANCELLED');

    const confirm = async () => {
        try {
            await fetch('http://localhost:8081/admin/confirm-all-pending-orders', {
                method: 'PUT',
            });
            setOrders((prevOrders) =>
                prevOrders.map((order) =>
                    order.orderStatus === 'PENDING'
                        ? { ...order, orderStatus: 'COMPLETED' }
                        : order
                )
            );
        } catch (error) {
            console.error('Error confirming orders:', error);
        }
    };

    const reject = async (orderId) => {
        try {
            await fetch(`http://localhost:8081/admin/reject-order-by/${orderId}`, {
                method: 'PUT',
            });
            setOrders((prevOrders) =>
                prevOrders.map((order) =>
                    order.orderId === orderId
                        ? { ...order, orderStatus: 'CANCELLED' }
                        : order
                )
            );
        } catch (error) {
            console.error('Error rejecting order:', error);
        }
    };

    return (
        <div className="confirm-page">
            <h1 className="confirm-title">Orders</h1>
            {pendingOrders.length > 0 && (
                <div className="confirm-section">
                    <h2>Pending Orders</h2>
                    <button className="confirm-button confirm-confirm-all" onClick={confirm}>
                        Confirm All
                    </button>
                    <ul className="confirm-list">
                        {pendingOrders.map((order) => (
                            <li key={order.orderId} className="confirm-list-item">
                                <div>
                                    <div> <strong>Customer:</strong> {order.customer.customerName}</div>
                                    <div> <strong>Customer Type:</strong> {order.customer.customerType}</div>
                                    <div> <strong>Product:</strong> {order.product.productName}</div>
                                    <div><strong>Quantity:</strong> {order.quantity}</div>
                                    <div><strong>Total Price:</strong> {order.totalPrice}</div>
                                    <div><strong>Priority Score:</strong> {order.priorityScore.toFixed(2)}</div>
                                    <div><strong>Waiting Time:</strong> {order.waitingTime.toFixed(2)} s</div>
                                    <div><strong>Status:</strong> {order.orderStatus}</div>

                                    <div><button
                                        className="confirm-button confirm-reject"
                                        onClick={() => reject(order.orderId)}
                                    >
                                        Reject
                                    </button>
                                    </div>




                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            )}
            {completedOrders.length > 0 && (
                <div className="confirm-section">
                    <h2>Completed Orders</h2>
                    <ul className="confirm-list">
                        {completedOrders.map((order) => (
                            <li key={order.orderId} className="confirm-list-item">
                                <div> <strong>Customer:</strong> {order.customer.customerName}</div>
                                <div> <strong>Customer Type:</strong> {order.customer.customerType}</div>
                                <div> <strong>Product:</strong> {order.product.productName} </div>
                                <div><strong>Quantity:</strong> {order.quantity}</div>
                                <div><strong>Total Price:</strong> {order.totalPrice}</div>
                                <div><strong>Priority Score:</strong> {order.priorityScore.toFixed(2)}</div>
                                <div><strong>Waiting Time:</strong> {order.waitingTime.toFixed(2)} s</div>
                                <div> <strong>Status:</strong> {order.orderStatus}</div>

                            </li>
                        ))}
                    </ul>
                </div>
            )}
            {cancelledOrders.length > 0 && (
                <div className="confirm-section">
                    <h2>Cancelled Orders</h2>
                    <ul className="confirm-list">
                        {cancelledOrders.map((order) => (
                            <li key={order.orderId} className="confirm-list-item">
                                <div> <strong>Customer:</strong> {order.customer.customerName}</div>
                                <div> <strong>Customer Type:</strong> {order.customer.customerType}</div>
                                <div><strong>Product:</strong> {order.product.productName}</div>
                                <div><strong>Quantity:</strong> {order.quantity}</div>
                                <div> <strong>Total Price:</strong> {order.totalPrice}</div>
                                <div><strong>Priority Score:</strong> {order.priorityScore.toFixed(2)}</div>
                                <div><strong>Waiting Time:</strong> {order.waitingTime.toFixed(2)} s</div>
                                <div> <strong>Status:</strong> {order.orderStatus}</div>

                            </li>
                        ))}
                    </ul>
                </div>
            )}
            {orders.length === 0 && <p className="confirm-no-orders">No orders found</p>}
        </div>
    );
}

export default Confirm;
