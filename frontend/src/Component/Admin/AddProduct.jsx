import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../CSS/AddProduct.css";

function AddProduct() {
    const [productName, setProductName] = useState("");
    const [price, setPrice] = useState("");
    const [stock, setStock] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const handleAddProduct = () => {
        fetch("http://localhost:8081/admin/save-product", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ productName, price, stock }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to add the product");
                }
                navigate("/products");
            })
            .catch((err) => setError(err.message));
    };

    return (
        <div className="add-product-page">
            <h1 className="add-product-title">Add New Product</h1>
            {error && <p className="add-product-error">Error: {error}</p>}
            <form className="add-product-form">
                <input
                    className="add-product-input"
                    type="text"
                    placeholder="Product Name"
                    value={productName}
                    onChange={(e) => setProductName(e.target.value)}
                />
                <input
                    className="add-product-input"
                    type="number"
                    placeholder="Price"
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                />
                <input
                    className="add-product-input"
                    type="number"
                    placeholder="Stock"
                    value={stock}
                    onChange={(e) => setStock(e.target.value)}
                />
                <button
                    className="add-product-button"
                    type="button"
                    onClick={handleAddProduct}
                >
                    Submit
                </button>
            </form>
        </div>
    );
}

export default AddProduct;
