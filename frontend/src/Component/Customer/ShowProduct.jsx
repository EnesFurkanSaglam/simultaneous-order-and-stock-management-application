import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";
import "../../CSS/ShowProduct.css";

function ShowProduct() {
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);
    const [message, setMessage] = useState("");
    const [userId, setUserId] = useState(null);
    const [customer, setCustomer] = useState(null);
    const [reload, setReload] = useState(false);
    const location = useLocation();
    const username = location.state?.username;

    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserId = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8081/customer/get-userId-by/${username}`
                );
                setUserId(response.data);
            } catch (error) {
                console.error("Error fetching userId:", error);
                setError("Failed to fetch user ID.");
            }
        };

        fetchUserId();
    }, [username]);

    useEffect(() => {
        if (userId) {
            const fetchCustomer = async () => {
                try {
                    const result = await axios.get(
                        `http://localhost:8081/customer/get-user-by/${userId}`
                    );
                    setCustomer(result.data);
                } catch (error) {
                    console.error("Error fetching customer:", error);
                    setError("Failed to fetch customer data.");
                }
            };

            fetchCustomer();
        }
    }, [userId]);

    useEffect(() => {
        const fetchProducts = () => {
            fetch("http://localhost:8081/customer/products")
                .then((response) => {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    return response.json();
                })
                .then((data) =>
                    setProducts(data.map((product) => ({ ...product, sepet: 0 })))
                )
                .catch((err) => setError(err.message));
        };

        fetchProducts();
    }, [reload]);

    const add = (productId) => {
        setProducts((prevProducts) =>
            prevProducts.map((product) =>
                product.productId === productId && product.sepet < 5
                    ? { ...product, sepet: product.sepet + 1 }
                    : product
            )
        );
    };

    const remove = (productId) => {
        setProducts((prevProducts) =>
            prevProducts.map((product) =>
                product.productId === productId && product.sepet > 0
                    ? { ...product, sepet: product.sepet - 1 }
                    : product
            )
        );
    };

    const buy = async () => {
        const orders = products
            .filter((product) => product.sepet > 0)
            .map((product) => ({
                productId: product.productId,
                quantity: product.sepet,
                customerId: userId,
            }));

        if (orders.length === 0) {
            setMessage("No products selected for purchase.");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8081/customer/save-order", orders, {
                headers: { "Content-Type": "application/json" },
            });

            if (response.data) {
                setMessage("All orders saved successfully!");
                setProducts((prevProducts) =>
                    prevProducts.map((product) => ({ ...product, sepet: 0 }))
                );
                setReload((prev) => !prev);
            } else {
                setMessage("Failed to save some orders.");
            }
        } catch (err) {
            console.error("Error saving orders:", err);
            setMessage("An error occurred while saving orders.");
        }
    };

    const goToMyOrders = () => {
        navigate("/my-orders", { state: { userId } });
    };

    return (
        <div className="show-product-page">
            <h1 className="show-product-title">Welcome, {username}!</h1>
            <button className="show-product-button" onClick={goToMyOrders}>My Orders</button>
            <div className="show-product-customer-info">
                <h4>Type: {customer ? customer.customerType : "Loading..."} </h4>
                <h3>Budget: {customer ? customer.budget : "Loading..."} $</h3>
            </div>
            <h2 className="show-product-subtitle">Products</h2>
            {error && <p className="show-product-error">{error}</p>}
            {message && <p className="show-product-message">{message}</p>}
            <ul className="show-product-list">
                {products.map((product) => (
                    <li key={product.productId} className="show-product-list-item">
                        <span>{product.productName}</span> <span>Price: {product.price} $</span> <span>Stock: {product.stock}</span>
                        <div className="show-product-actions">
                            <hr />
                            <button onClick={() => add(product.productId)}>Add</button>
                            <button onClick={() => remove(product.productId)}>Remove</button>
                        </div>
                        <p>Selected: {product.sepet}</p>
                    </li>
                ))}
            </ul>
            <button className="show-product-buy-button" onClick={buy}>Buy</button>
        </div>
    );
}

export default ShowProduct;
