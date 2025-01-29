import React, { useState } from "react";
import "../../CSS/UpdateProduct.css";

function UpdateProduct({ product, onClose }) {
    const [updatedProduct, setUpdatedProduct] = useState({ ...product });
    const [error, setError] = useState(null);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setUpdatedProduct({ ...updatedProduct, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        fetch("http://localhost:8081/admin/update-product", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedProduct),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to update product");
                }
                alert("Product updated successfully!");
                onClose();
            })
            .catch((err) => {
                setError(err.message);
            });
    };

    return (
        <div className="update-product-modal">
            <h2 className="update-product-title">Update Product</h2>
            {error && <p className="update-product-error">Error: {error}</p>}
            <form className="update-product-form" onSubmit={handleSubmit}>
                <label className="update-product-label">
                    Product Name:
                    <input
                        type="text"
                        name="productName"
                        value={updatedProduct.productName}
                        onChange={handleInputChange}
                        className="update-product-input"
                    />
                </label>
                <label className="update-product-label">
                    Price:
                    <input
                        type="number"
                        name="price"
                        value={updatedProduct.price}
                        onChange={handleInputChange}
                        className="update-product-input"
                    />
                </label>
                <label className="update-product-label">
                    Stock:
                    <input
                        type="number"
                        name="stock"
                        value={updatedProduct.stock}
                        onChange={handleInputChange}
                        className="update-product-input"
                    />
                </label>
                <div className="update-product-buttons">
                    <button type="submit" className="update-product-save-button">
                        Save
                    </button>
                    <button
                        type="button"
                        onClick={onClose}
                        className="update-product-cancel-button"
                    >
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    );
}

export default UpdateProduct;
